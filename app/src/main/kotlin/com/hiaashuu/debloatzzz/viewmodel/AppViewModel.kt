package com.hiaashuu.debloatzzz.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hiaashuu.debloatzzz.data.AppRepository
import com.hiaashuu.debloatzzz.data.BackupManager
import com.hiaashuu.debloatzzz.model.AppCategory
import com.hiaashuu.debloatzzz.model.AppFilter
import com.hiaashuu.debloatzzz.model.BackupEntry
import com.hiaashuu.debloatzzz.model.InstalledAppInfo
import com.hiaashuu.debloatzzz.model.SafetyTag
import com.hiaashuu.debloatzzz.model.SortOrder
import com.hiaashuu.debloatzzz.model.UninstallResult
import com.hiaashuu.debloatzzz.shizuku.ShizukuHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)
    val backupManager = BackupManager(application)

    private val _allApps = MutableStateFlow<List<InstalledAppInfo>>(emptyList())
    val allApps: StateFlow<List<InstalledAppInfo>> = _allApps.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow(AppFilter.ALL)
    val selectedFilter: StateFlow<AppFilter> = _selectedFilter.asStateFlow()

    private val _selectedPackages = MutableStateFlow<Set<String>>(emptySet())
    val selectedPackages: StateFlow<Set<String>> = _selectedPackages.asStateFlow()

    private val _operationResult = MutableStateFlow<String?>(null)
    val operationResult: StateFlow<String?> = _operationResult.asStateFlow()

    private val _backups = MutableStateFlow<List<BackupEntry>>(emptyList())
    val backups: StateFlow<List<BackupEntry>> = _backups.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.BLOATWARE_FIRST)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    val shizukuConnected: StateFlow<Boolean> = ShizukuHelper.isConnected
    val shizukuBinderAlive: StateFlow<Boolean> = ShizukuHelper.isBinderAlive
    val shizukuHasPermission: StateFlow<Boolean> = ShizukuHelper.hasPermission

    val filteredApps: StateFlow<List<InstalledAppInfo>> = combine(
        _allApps,
        _searchQuery,
        _selectedFilter,
        _sortOrder
    ) { apps, query, filter, sort ->
        val filtered = apps.filter { app ->
            val matchesQuery = query.isEmpty() ||
                app.displayName.contains(query, ignoreCase = true) ||
                app.packageName.contains(query, ignoreCase = true)

            val matchesFilter = when (filter) {
                AppFilter.ALL -> true
                AppFilter.USER_APPS -> !app.isSystemApp
                AppFilter.SYSTEM_APPS -> app.isSystemApp
                AppFilter.BLOATWARE -> app.bloatInfo?.safetyTag?.let {
                    it == SafetyTag.RECOMMENDED_REMOVE || it == SafetyTag.SAFE_TO_REMOVE
                } ?: false
                AppFilter.DISABLED -> !app.isEnabled
                AppFilter.SAMSUNG -> app.bloatInfo?.category == AppCategory.SAMSUNG_BLOAT
                AppFilter.GOOGLE -> app.bloatInfo?.category == AppCategory.GOOGLE_BLOAT ||
                    app.bloatInfo?.category == AppCategory.GOOGLE_SERVICE
                AppFilter.XIAOMI -> app.bloatInfo?.category == AppCategory.XIAOMI_MIUI
            }

            matchesQuery && matchesFilter
        }

        when (sort) {
            SortOrder.NAME_A_TO_Z -> {
                filtered.sortedBy { it.displayName.lowercase() }
            }
            SortOrder.NAME_Z_TO_A -> {
                filtered.sortedByDescending { it.displayName.lowercase() }
            }
            SortOrder.INSTALL_DATE_NEWEST -> {
                filtered.sortedByDescending { it.installTime }
            }
            SortOrder.INSTALL_DATE_OLDEST -> {
                filtered.sortedBy { it.installTime }
            }
            SortOrder.BLOATWARE_FIRST -> {
                filtered.sortedWith(
                    compareByDescending<InstalledAppInfo> { it.bloatInfo != null }
                        .thenByDescending {
                            it.bloatInfo?.safetyTag == SafetyTag.RECOMMENDED_REMOVE
                        }
                        .thenByDescending {
                            it.bloatInfo?.safetyTag == SafetyTag.SAFE_TO_REMOVE
                        }
                        .thenBy { it.displayName.lowercase() }
                )
            }
            SortOrder.SAFE_FIRST -> {
                filtered.sortedWith(
                    compareByDescending<InstalledAppInfo> {
                        it.bloatInfo?.safetyTag == SafetyTag.KEEP
                    }.thenBy { it.displayName.lowercase() }
                )
            }
            SortOrder.TYPE_SYSTEM_FIRST -> {
                filtered.sortedWith(
                    compareByDescending<InstalledAppInfo> { it.isSystemApp }
                        .thenBy { it.displayName.lowercase() }
                )
            }
            SortOrder.TYPE_USER_FIRST -> {
                filtered.sortedWith(
                    compareBy<InstalledAppInfo> { it.isSystemApp }
                        .thenBy { it.displayName.lowercase() }
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    init {
        loadApps()
        loadBackups()
    }

    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            _allApps.value = repository.getAllInstalledApps()
            _isLoading.value = false
        }
    }

    fun loadBackups() {
        _backups.value = backupManager.listBackups()
    }

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    fun updateFilter(filter: AppFilter) {
        _selectedFilter.value = filter
    }

    fun updateSortOrder(sort: SortOrder) {
        _sortOrder.value = sort
    }

    fun togglePackageSelection(packageName: String) {
        val current = _selectedPackages.value.toMutableSet()
        if (current.contains(packageName)) {
            current.remove(packageName)
        } else {
            current.add(packageName)
        }
        _selectedPackages.value = current
    }

    fun clearSelection() {
        _selectedPackages.value = emptySet()
    }

    fun selectAll() {
        val filtered = filteredApps.value
        _selectedPackages.value = filtered.map { it.packageName }.toSet()
    }

    fun clearOperationResult() {
        _operationResult.value = null
    }

    suspend fun uninstallPackage(packageName: String): UninstallResult {
        if (!ShizukuHelper.isConnected.value) {
            return UninstallResult.ShizukuNotConnected
        }
        val (success, message) = ShizukuHelper.uninstallPackage(packageName)
        return if (success) {
            loadApps()
            UninstallResult.Success
        } else {
            UninstallResult.Failure(message)
        }
    }

    suspend fun restorePackage(packageName: String): UninstallResult {
        if (!ShizukuHelper.isConnected.value) {
            return UninstallResult.ShizukuNotConnected
        }
        val (success, message) = ShizukuHelper.reinstallPackage(packageName)
        return if (success) {
            loadApps()
            UninstallResult.Success
        } else {
            UninstallResult.Failure(message)
        }
    }

    suspend fun disablePackage(packageName: String): UninstallResult {
        if (!ShizukuHelper.isConnected.value) {
            return UninstallResult.ShizukuNotConnected
        }
        val (success, message) = ShizukuHelper.disablePackage(packageName)
        return if (success) {
            loadApps()
            UninstallResult.Success
        } else {
            UninstallResult.Failure(message)
        }
    }

    suspend fun enablePackage(packageName: String): UninstallResult {
        if (!ShizukuHelper.isConnected.value) {
            return UninstallResult.ShizukuNotConnected
        }
        val (success, message) = ShizukuHelper.enablePackage(packageName)
        return if (success) {
            loadApps()
            UninstallResult.Success
        } else {
            UninstallResult.Failure(message)
        }
    }
    
    suspend fun restoreFromBackup(backup: BackupEntry): List<Pair<String, UninstallResult>> {
        return backup.packages.map { pkg ->
            val result = restorePackage(pkg)
            Pair(pkg, result)
        }
    }
    
    suspend fun batchUninstall(packages: List<String>): Pair<Int, Int> {
        var successCount = 0
        var failCount = 0
        packages.forEach { pkg ->
            val result = uninstallPackage(pkg)
            when (result) {
                is UninstallResult.Success -> successCount++
                else -> failCount++
            }
        }
        return Pair(successCount, failCount)
    }

    fun createBackup(packages: List<String>, label: String = ""): BackupEntry? {
        val entry = backupManager.createBackup(packages, label)
        if (entry != null) {
            loadBackups()
        }
        return entry
    }

    fun deleteBackup(filename: String) {
        backupManager.deleteBackup(filename)
        loadBackups()
    }

    fun getAppInfo(packageName: String): InstalledAppInfo? {
        return _allApps.value.find { it.packageName == packageName }
    }

    fun isPackageInstalled(packageName: String): Boolean {
        return repository.isPackageInstalled(packageName)
    }

    fun getBloatwareCount(): Int {
        return _allApps.value.count { app ->
            app.bloatInfo?.safetyTag?.let {
                it == SafetyTag.RECOMMENDED_REMOVE || it == SafetyTag.SAFE_TO_REMOVE
            } ?: false
        }
    }

    fun getSystemAppCount(): Int {
        return _allApps.value.count { it.isSystemApp }
    }

    fun getUserAppCount(): Int {
        return _allApps.value.count { !it.isSystemApp }
    }

    fun generateUnknownAppsReport(context: Context): android.net.Uri? {
        try {
            val unknownApps = _allApps.value.filter {
                it.bloatInfo?.safetyTag == SafetyTag.UNKNOWN || it.bloatInfo == null
            }
            if (unknownApps.isEmpty()) return null

            val file = File(context.cacheDir, "UnknownAppsReport.txt")
            val sb = java.lang.StringBuilder()
            sb.appendLine("DeBloatzzz Unknown Apps Report")
            sb.appendLine("==============================")
            sb.appendLine("Device: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}")
            sb.appendLine("Android Version: ${android.os.Build.VERSION.RELEASE} (SDK ${android.os.Build.VERSION.SDK_INT})")
            sb.appendLine("Total Unknown Apps: ${unknownApps.size}")
            sb.appendLine("==============================\n")

            unknownApps.forEachIndexed { index, app ->
                sb.appendLine("${index + 1}. ${app.displayName}")
                sb.appendLine("   Package: ${app.packageName}")
                sb.appendLine("   System App: ${app.isSystemApp}")
                sb.appendLine("   Version: ${app.versionName} (${app.versionCode})")
                if (app.declaredPermissions.isNotEmpty()) {
                    sb.appendLine("   Permissions:")
                    app.declaredPermissions.forEach { perm ->
                        sb.appendLine("      - ${perm.name}")
                    }
                } else {
                    sb.appendLine("   Permissions: None")
                }
                sb.appendLine()
            }

            file.writeText(sb.toString())
            return androidx.core.content.FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}