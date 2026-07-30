package com.hiaashuu.debloatzzz.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.model.PermissionInfo
import com.hiaashuu.debloatzzz.model.SafetyTag
import com.hiaashuu.debloatzzz.model.UninstallResult
import com.hiaashuu.debloatzzz.ui.components.CategoryChip
import com.hiaashuu.debloatzzz.ui.components.SafetyTagChip
import com.hiaashuu.debloatzzz.ui.theme.WarningOrange
import com.hiaashuu.debloatzzz.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private fun Drawable.toBitmapSafe(): Bitmap? {
    return try {
        if (this is BitmapDrawable && bitmap != null) return bitmap
        val w = if (intrinsicWidth > 0) intrinsicWidth else 64
        val h = if (intrinsicHeight > 0) intrinsicHeight else 64
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        bmp
    } catch (e: Exception) { null }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppDetailScreen(
    packageName: String,
    viewModel: AppViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val app = viewModel.getAppInfo(packageName)
    val shizukuConnected by viewModel.shizukuConnected.collectAsState()

    var showUninstallDialog by remember { mutableStateOf(false) }
    var showDisableDialog by remember { mutableStateOf(false) }
    var isOperationRunning by remember { mutableStateOf(false) }

    val iconBitmap = remember(packageName) {
        try {
            context.packageManager.getApplicationIcon(packageName).toBitmapSafe()?.asImageBitmap()
        } catch (e: Exception) { null }
    }

    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val isInstalled = viewModel.isPackageInstalled(packageName)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (app != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp,
                    border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isInstalled && shizukuConnected) {
                            val isEssential = app.bloatInfo?.safetyTag == SafetyTag.KEEP
                            if (isEssential) {
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                                ) {
                                    Row(modifier = Modifier.padding(10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Warning, null, modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.error)
                                        Text("This is a core system app. Removing it may cause a bootloop or break essential functions.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer,
                                            lineHeight = 16.sp)
                                    }
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { showUninstallDialog = true },
                                    modifier = Modifier.weight(1f).height(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isEssential)
                                            MaterialTheme.colorScheme.errorContainer
                                        else MaterialTheme.colorScheme.error,
                                        contentColor = if (isEssential)
                                            MaterialTheme.colorScheme.onErrorContainer
                                        else MaterialTheme.colorScheme.onError
                                    )
                                ) {
                                    Icon(Icons.Filled.Delete, null, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(if (isEssential) "Uninstall (Risky)" else "Uninstall",
                                        fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { showDisableDialog = true },
                                    modifier = Modifier.weight(1f).height(48.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(if (app.isEnabled) Icons.Filled.ToggleOff else Icons.Filled.ToggleOn,
                                        null, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(if (app.isEnabled) "Disable" else "Enable", 
                                        fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (!isInstalled && app.bloatInfo?.canBeRestored == true && shizukuConnected) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        isOperationRunning = true
                                        val result = viewModel.restorePackage(packageName)
                                        val msg = when (result) {
                                            is UninstallResult.Success -> "App restored successfully!"
                                            is UninstallResult.Failure -> "Failed: ${result.reason}"
                                            else -> "Error occurred"
                                        }
                                        snackbarHostState.showSnackbar(msg)
                                        isOperationRunning = false
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(Icons.Filled.Restore, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Restore App", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        } else if (!shizukuConnected) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = WarningOrange.copy(alpha = 0.1f),
                                border = BorderStroke(0.8.dp, WarningOrange.copy(alpha = 0.4f))
                            ) {
                                Text("Shizuku not connected. Connect Shizuku to enable uninstall/disable actions.",
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 16.dp, top = 4.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(
                        text = "App Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }

                if (app == null) {
                    Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Outlined.Android, null, modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
                            Spacer(Modifier.height(12.dp))
                            Text("App info not available", style = MaterialTheme.typography.bodyMedium)
                            Text(packageName, style = MaterialTheme.typography.labelSmall,
                                fontFamily = FontFamily.Monospace)
                        }
                    }
                    return@Column
                }

                val bloatInfo = app.bloatInfo
                val allPermissions = (bloatInfo?.permissions ?: emptyList())
                    .plus(app.declaredPermissions)
                    .distinctBy { it.name }

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (iconBitmap != null) {
                                        Image(bitmap = iconBitmap, contentDescription = null,
                                            modifier = Modifier.size(64.dp), contentScale = ContentScale.Fit)
                                    } else {
                                        Icon(Icons.Outlined.Android, null, modifier = Modifier.size(34.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                Spacer(Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(app.displayName, style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                        Text(app.packageName, style = MaterialTheme.typography.labelSmall,
                                            fontFamily = FontFamily.Monospace,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                            modifier = Modifier.weight(1f), maxLines = 2, fontSize = 9.5.sp)
                                        IconButton(
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString(app.packageName))
                                                scope.launch { snackbarHostState.showSnackbar("Package name copied!") }
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(Icons.Filled.ContentCopy, "Copy",
                                                modifier = Modifier.size(14.dp),
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                bloatInfo?.let {
                                    SafetyTagChip(tag = it.safetyTag)
                                    CategoryChip(category = it.category)
                                } ?: SafetyTagChip(tag = SafetyTag.UNKNOWN)

                                if (!app.isEnabled) {
                                    Surface(shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
                                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))) {
                                        Text("DISABLED", style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                                    }
                                }
                                if (!isInstalled) {
                                    Surface(shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
                                        Text("NOT INSTALLED", style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(Modifier.height(12.dp))

                    InfoSectionCard(title = "Technical Info", icon = Icons.Outlined.Android) {
                        InfoRow(label = "Version", value = "${app.versionName} (${app.versionCode})")
                        InfoRow(label = "Type", value = if (app.isSystemApp) "System App" else "User App")
                        InfoRow(label = "Status", value = if (app.isEnabled) "Enabled" else "Disabled")
                        
                        if (app.apkSize > 0) {
                            val sizeMb = String.format(java.util.Locale.US, "%.1f MB", app.apkSize / (1024f * 1024f))
                            InfoRow(label = "App Size", value = sizeMb)
                        }
                        
                        if (app.installer != null) {
                            val installerName = when (app.installer) {
                                "com.android.vending" -> "Play Store"
                                "com.aurora.store" -> "Aurora Store"
                                "org.fdroid.fdroid" -> "F-Droid"
                                "com.heytap.market" -> "App Market"
                                "com.xiaomi.mipicks" -> "GetApps"
                                "com.sec.android.app.samsungapps" -> "Galaxy Store"
                                "com.amazon.venezia" -> "Amazon Appstore"
                                else -> app.installer
                            }
                            InfoRow(label = "Installed From", value = installerName)
                        } else if (!app.isSystemApp) {
                            InfoRow(label = "Installed From", value = "Sideloaded")
                        } else {
                            InfoRow(label = "Installed From", value = "System")
                        }
                        
                        if (app.installTime > 0) {
                            InfoRow(label = "Installed", value = dateFormat.format(Date(app.installTime)))
                        }
                        if (app.updateTime > 0 && app.updateTime != app.installTime) {
                            InfoRow(label = "Updated", value = dateFormat.format(Date(app.updateTime)))
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    bloatInfo?.let { info ->
                        InfoSectionCard(title = "About This App", icon = Icons.Outlined.Info) {
                            Text(info.description, style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface, lineHeight = 20.sp)

                            if (info.developer.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Outlined.BusinessCenter, null,
                                        modifier = Modifier.size(14.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("Developer: ${info.developer}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        if (info.warningNote.isNotEmpty()) {
                            val isKeep = info.safetyTag == SafetyTag.KEEP
                            val isDanger = info.safetyTag == SafetyTag.RECOMMENDED_REMOVE

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                color = when {
                                    isKeep -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    isDanger -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                                    else -> WarningOrange.copy(alpha = 0.12f)
                                },
                                border = BorderStroke(0.8.dp, when {
                                    isKeep -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    isDanger -> MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
                                    else -> WarningOrange.copy(alpha = 0.4f)
                                })
                            ) {
                                Row(modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top) {
                                    Icon(
                                        if (isKeep) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                                        null, modifier = Modifier.size(16.dp),
                                        tint = if (isKeep) MaterialTheme.colorScheme.primary
                                        else if (isDanger) MaterialTheme.colorScheme.error else WarningOrange
                                    )
                                    Text(info.warningNote, style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp)
                                }
                            }

                            Spacer(Modifier.height(10.dp))
                        }

                        if (info.dataCollected.isNotEmpty()) {
                            InfoSectionCard(title = "Data This App Collects", icon = Icons.Outlined.Analytics) {
                                info.dataCollected.forEach { item ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("•", color = MaterialTheme.colorScheme.error,
                                            fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(item, style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                        }

                        if (info.alternatives.isNotEmpty()) {
                            InfoSectionCard(title = "Better Alternatives", icon = Icons.Outlined.SwapHoriz) {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    info.alternatives.forEach { alt ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                            border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                                        ) {
                                            Text(alt, style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                                        }
                                    }
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                        }
                    }

                    if (allPermissions.isNotEmpty()) {
                        val criticalPerms = allPermissions.filter { it.isCritical }
                        val normalPerms = allPermissions.filter { !it.isCritical }

                        InfoSectionCard(
                            title = "Permissions (${allPermissions.size} total, ${criticalPerms.size} critical)",
                            icon = Icons.Outlined.Security
                        ) {
                            if (criticalPerms.isNotEmpty()) {
                                Text("🔴 Critical Permissions",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error)
                                Spacer(Modifier.height(6.dp))
                                criticalPerms.forEach { perm ->
                                    PermissionItem(perm = perm)
                                    Spacer(Modifier.height(4.dp))
                                }
                            }
                            if (normalPerms.isNotEmpty()) {
                                if (criticalPerms.isNotEmpty()) {
                                    Spacer(Modifier.height(8.dp))
                                    Divider(color = MaterialTheme.colorScheme.outlineVariant)
                                    Spacer(Modifier.height(8.dp))
                                }
                                Text("Other Permissions",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(Modifier.height(6.dp))
                                normalPerms.forEach { perm ->
                                    PermissionItem(perm = perm)
                                    Spacer(Modifier.height(4.dp))
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                    
                    Spacer(Modifier.height(30.dp))
                }
            }

            if (isOperationRunning) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    if (showUninstallDialog) {
        val isEssential = app?.bloatInfo?.safetyTag == SafetyTag.KEEP || app?.bloatInfo == null
        AlertDialog(
            onDismissRequest = { showUninstallDialog = false },
            icon = { Icon(if (isEssential) Icons.Filled.Warning else Icons.Filled.Delete,
                null, tint = if (isEssential) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface) },
            title = {
                Text(if (isEssential) "⚠️ Risky Operation" else "Confirm Uninstall",
                    fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isEssential) {
                        Surface(shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.errorContainer) {
                            Text("This app is marked as ESSENTIAL. Removing it may cause a bootloop, break calls, internet, or require a factory reset.",
                                modifier = Modifier.padding(10.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer)
                        }
                    }
                    Text("Uninstall '${app?.displayName}' for the current user? The app can be restored using the restore function.",
                        style = MaterialTheme.typography.bodyMedium)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showUninstallDialog = false
                        scope.launch {
                            isOperationRunning = true
                            val result = viewModel.uninstallPackage(packageName)
                            val msg = when (result) {
                                is UninstallResult.Success -> "Uninstalled successfully!"
                                is UninstallResult.Failure -> "Failed: ${result.reason}"
                                UninstallResult.ShizukuNotConnected -> "Shizuku not connected"
                                UninstallResult.PermissionDenied -> "Permission denied"
                            }
                            snackbarHostState.showSnackbar(msg)
                            isOperationRunning = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Uninstall") }
            },
            dismissButton = {
                TextButton(onClick = { showUninstallDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showDisableDialog) {
        val isEnabling = app?.isEnabled == false
        AlertDialog(
            onDismissRequest = { showDisableDialog = false },
            title = { Text(if (isEnabling) "Enable App" else "Disable App") },
            text = { Text("${if (isEnabling) "Enable" else "Disable"} '${app?.displayName}'?") },
            confirmButton = {
                Button(onClick = {
                    showDisableDialog = false
                    scope.launch {
                        isOperationRunning = true
                        val result = if (isEnabling) viewModel.enablePackage(packageName)
                        else viewModel.disablePackage(packageName)
                        val msg = when (result) {
                            is UninstallResult.Success -> if (isEnabling) "App enabled!" else "App disabled!"
                            is UninstallResult.Failure -> "Failed: ${result.reason}"
                            else -> "Error"
                        }
                        snackbarHostState.showSnackbar(msg)
                        isOperationRunning = false
                    }
                }) { Text(if (isEnabling) "Enable" else "Disable") }
            },
            dismissButton = {
                TextButton(onClick = { showDisableDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PermissionItem(perm: PermissionInfo) {
    val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
    val context = androidx.compose.ui.platform.LocalContext.current
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(perm.name))
                    android.widget.Toast.makeText(context, "Permission copied!", android.widget.Toast.LENGTH_SHORT).show()
                }
            ),
        shape = RoundedCornerShape(10.dp),
        color = when {
            perm.isDangerous && perm.isCritical -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
            perm.isCritical -> WarningOrange.copy(alpha = 0.1f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        },
        border = BorderStroke(0.7.dp, when {
            perm.isDangerous && perm.isCritical -> MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
            perm.isCritical -> WarningOrange.copy(alpha = 0.4f)
            else -> MaterialTheme.colorScheme.outlineVariant
        })
    ) {
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top) {
            Icon(
                when {
                    perm.isDangerous -> Icons.Filled.GppBad
                    perm.isCritical -> Icons.Filled.Warning
                    else -> Icons.Outlined.Security
                },
                null, modifier = Modifier.size(16.dp).padding(top = 1.dp),
                tint = when {
                    perm.isDangerous -> MaterialTheme.colorScheme.error
                    perm.isCritical -> WarningOrange
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(perm.label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    if (perm.isDangerous) {
                        Surface(shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.15f)) {
                            Text("DANGEROUS", style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold,
                                fontSize = 8.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                        }
                    } else if (perm.isCritical) {
                        Surface(shape = RoundedCornerShape(4.dp), color = WarningOrange.copy(alpha = 0.15f)) {
                            Text("CRITICAL", style = MaterialTheme.typography.labelSmall,
                                color = WarningOrange, fontWeight = FontWeight.Bold,
                                fontSize = 8.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                        }
                    }
                }
                Text(perm.description, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
                Text(perm.name, style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun InfoSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(icon, null, modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary)
                Text(title, style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
    }
}