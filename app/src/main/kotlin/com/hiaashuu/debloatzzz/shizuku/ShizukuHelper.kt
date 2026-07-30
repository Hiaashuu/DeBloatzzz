package com.hiaashuu.debloatzzz.shizuku

import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import rikka.shizuku.Shizuku

object ShizukuHelper {

    const val SHIZUKU_PERMISSION_REQUEST_CODE = 1001

    private val _isBinderAlive = MutableStateFlow(false)
    val isBinderAlive: StateFlow<Boolean> = _isBinderAlive.asStateFlow()

    private val _hasPermission = MutableStateFlow(false)
    val hasPermission: StateFlow<Boolean> = _hasPermission.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError.asStateFlow()

    private val binderReceivedListener = Shizuku.OnBinderReceivedListener {
        _isBinderAlive.value = true
        checkAndUpdatePermission()
        updateConnectedState()
    }

    private val binderDeadListener = Shizuku.OnBinderDeadListener {
        _isBinderAlive.value = false
        _hasPermission.value = false
        updateConnectedState()
    }

    private fun checkAndUpdatePermission() {
        try {
            if (!Shizuku.isPreV11()) {
                val granted = Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
                _hasPermission.value = granted
                
                // Automatically request permission if Shizuku is connected but not yet authorized
                if (!granted) {
                    Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
                }
            }
        } catch (e: Exception) {
            _hasPermission.value = false
        }
    }

    private fun updateConnectedState() {
        _isConnected.value = _isBinderAlive.value && _hasPermission.value
    }

    fun init() {
        try {
            Shizuku.addBinderReceivedListenerSticky(binderReceivedListener)
            Shizuku.addBinderDeadListener(binderDeadListener)
            
            // Prevent double-checking if the sticky listener already caught the live binder
            if (!_isBinderAlive.value && Shizuku.pingBinder()) {
                _isBinderAlive.value = true
                checkAndUpdatePermission()
                updateConnectedState()
            }
        } catch (e: Exception) {
            _lastError.value = e.message
        }
    }

    fun destroy() {
        try {
            Shizuku.removeBinderReceivedListener(binderReceivedListener)
            Shizuku.removeBinderDeadListener(binderDeadListener)
        } catch (e: Exception) {

        }
    }

    fun requestPermission() {
        try {
            if (Shizuku.isPreV11()) {
                _lastError.value = "Shizuku v11 or higher is required."
                return
            }
            Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            _lastError.value = "Shizuku is not running. Please start Shizuku first."
        }
    }

    fun onPermissionResult(requestCode: Int, grantResult: Int) {
        if (requestCode == SHIZUKU_PERMISSION_REQUEST_CODE) {
            _hasPermission.value = grantResult == PackageManager.PERMISSION_GRANTED
            updateConnectedState()
        }
    }

    fun isShizukuAvailable(): Boolean {
        return try {
            Shizuku.pingBinder()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun executeCommand(vararg args: String): Pair<Boolean, String> {
        return withContext(Dispatchers.IO) {
            try {
                if (!_isConnected.value) {
                    return@withContext Pair(false, "Shizuku is not connected or permission not granted.")
                }
                
                // Shizuku API 13+ made newProcess private to enforce UserService usage.
                // Since we only need simple ADB shell commands, we securely bypass this restriction 
                // via reflection to avoid the compilation errors without building full boilerplate services.
                val clazz = Class.forName("rikka.shizuku.Shizuku")
                val method = clazz.getDeclaredMethod(
                    "newProcess",
                    Array<String>::class.java,
                    Array<String>::class.java,
                    String::class.java
                )
                method.isAccessible = true
                
                val process = method.invoke(null, args.toList().toTypedArray(), null, null) as java.lang.Process
                
                val output = process.inputStream.bufferedReader().use { it.readText() }.trim()
                val error = process.errorStream.bufferedReader().use { it.readText() }.trim()
                val exitCode = process.waitFor()
                process.destroy()

                val success = exitCode == 0 || output.contains("Success", ignoreCase = true)
                val result = if (success) output else error.ifEmpty { output }
                Pair(success, result)
            } catch (e: Exception) {
                // Handle Reflection's InvocationTargetException underlying cause if available
                val cause = e.cause ?: e
                if (cause is IllegalStateException) {
                    _isBinderAlive.value = false
                    updateConnectedState()
                    Pair(false, "Shizuku binder died. Please restart Shizuku.")
                } else {
                    Pair(false, cause.message ?: "Unknown error occurred.")
                }
            }
        }
    }

    suspend fun uninstallPackage(packageName: String, userId: Int = 0): Pair<Boolean, String> {
        return executeCommand("pm", "uninstall", "-k", "--user", userId.toString(), packageName)
    }

    suspend fun reinstallPackage(packageName: String, userId: Int = 0): Pair<Boolean, String> {
        return executeCommand("cmd", "package", "install-existing", "--user", userId.toString(), packageName)
    }

    suspend fun disablePackage(packageName: String): Pair<Boolean, String> {
        return executeCommand("pm", "disable-user", "--user", "0", packageName)
    }

    suspend fun enablePackage(packageName: String): Pair<Boolean, String> {
        return executeCommand("pm", "enable", packageName)
    }
}