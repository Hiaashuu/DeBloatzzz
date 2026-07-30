package com.hiaashuu.debloatzzz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.data.AppDataSource
import com.hiaashuu.debloatzzz.model.BackupEntry
import com.hiaashuu.debloatzzz.model.UninstallResult
import com.hiaashuu.debloatzzz.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BackupRestoreScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val backups by viewModel.backups.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var newBackupLabel by remember { mutableStateOf("") }
    var backupToDelete by remember { mutableStateOf<BackupEntry?>(null) }
    var backupToRestore by remember { mutableStateOf<BackupEntry?>(null) }
    var isWorking by remember { mutableStateOf(false) }

    val selectedPackages by viewModel.selectedPackages.collectAsState()
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Filled.Add, "Create Backup")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
                Column {
                    Text("Backup & Restore", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                    Text("${backups.size} backup(s) saved",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            if (backups.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Outlined.Backup, null, modifier = Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                        Text("No Backups Yet", style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Create a backup before debloating to be able to restore apps later.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.padding(horizontal = 32.dp))
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { showCreateDialog = true }) {
                            Icon(Icons.Filled.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Create First Backup")
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(backups, key = { it.id }) { backup ->
                        BackupCard(
                            backup = backup,
                            dateFormat = dateFormat,
                            onRestore = { backupToRestore = backup },
                            onDelete = { backupToDelete = backup }
                        )
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            icon = { Icon(Icons.Outlined.Backup, null) },
            title = { Text("Create Backup") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Saves the list of ${selectedPackages.size} selected package(s). If nothing is selected, saves all currently installed apps.",
                        style = MaterialTheme.typography.bodyMedium)
                    OutlinedTextField(
                        value = newBackupLabel,
                        onValueChange = { newBackupLabel = it },
                        label = { Text("Backup label (optional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Before debloating OnePlus") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showCreateDialog = false
                    scope.launch {
                        isWorking = true
                        val pkgs = if (selectedPackages.isNotEmpty()) selectedPackages.toList() else viewModel.allApps.value.map { it.packageName }
                        val result = viewModel.createBackup(pkgs, newBackupLabel)
                        snackbarHostState.showSnackbar(
                            if (result != null) "Backup '${result.label}' created!" else "Backup failed."
                        )
                        newBackupLabel = ""
                        isWorking = false
                    }
                }) { Text("Create") }
            },
            dismissButton = { TextButton(onClick = { showCreateDialog = false }) { Text("Cancel") } }
        )
    }

    backupToDelete?.let { backup ->
        AlertDialog(
            onDismissRequest = { backupToDelete = null },
            icon = { Icon(Icons.Filled.Delete, null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Delete Backup") },
            text = { Text("Delete '${backup.label}'? This cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        backupToDelete = null
                        scope.launch {
                            viewModel.deleteBackup(backup.filename)
                            snackbarHostState.showSnackbar("Backup deleted.")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Delete") }
            },
            dismissButton = { TextButton(onClick = { backupToDelete = null }) { Text("Cancel") } }
        )
    }

    backupToRestore?.let { backup ->
        AlertDialog(
            onDismissRequest = { backupToRestore = null },
            icon = { Icon(Icons.Filled.Restore, null) },
            title = { Text("Restore ${backup.packages.size} Apps") },
            text = {
                Text("This will attempt to reinstall all ${backup.packages.size} packages from backup '${backup.label}'.")
            },
            confirmButton = {
                Button(onClick = {
                    backupToRestore = null
                    scope.launch {
                        isWorking = true
                        val results = viewModel.restoreFromBackup(backup)
                        val succeeded = results.count { it.second is UninstallResult.Success }
                        snackbarHostState.showSnackbar("Restored $succeeded / ${backup.packages.size} apps.")
                        isWorking = false
                    }
                }) { Text("Restore") }
            },
            dismissButton = { TextButton(onClick = { backupToRestore = null }) { Text("Cancel") } }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BackupCard(
    backup: BackupEntry,
    dateFormat: SimpleDateFormat,
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val knownPackageNames = backup.packages.map { pkg ->
        AppDataSource.getBloatInfo(pkg)?.displayName ?: pkg
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier.size(44.dp)
                        .let {
                            it.then(Modifier)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Surface(shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer) {
                        Icon(Icons.Outlined.HistoryEdu, null,
                            modifier = Modifier.size(44.dp).padding(10.dp),
                            tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(backup.label, style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(dateFormat.format(Date(backup.timestamp)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)) {
                MetaChip(label = "${backup.packages.size} apps",
                    icon = Icons.Outlined.Apps)
                MetaChip(label = backup.deviceModel,
                    icon = Icons.Outlined.PhoneAndroid)
                MetaChip(label = "Android ${backup.androidVersion}",
                    icon = Icons.Outlined.Android)
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(if (expanded) "Hide app list" else "Preview ${backup.packages.size} apps",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary)
            }

            if (expanded) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        knownPackageNames.take(20).forEach { name ->
                            Text("• $name", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (backup.packages.size > 20) {
                            Text("... and ${backup.packages.size - 20} more",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                        }
                    }
                }
                Spacer(Modifier.height(4.dp))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onRestore, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )) {
                    Icon(Icons.Filled.Restore, null, modifier = Modifier.size(15.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Restore", fontSize = 13.sp)
                }
                OutlinedButton(onClick = onDelete, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))) {
                    Icon(Icons.Filled.Delete, null, modifier = Modifier.size(15.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Delete", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun MetaChip(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icon, null, modifier = Modifier.size(11.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(label, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}