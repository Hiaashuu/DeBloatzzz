package com.hiaashuu.debloatzzz.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.model.SafetyTag
import com.hiaashuu.debloatzzz.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onNavigateToAppList: () -> Unit,
    onNavigateToBackup: () -> Unit,
    onNavigateToGuide: () -> Unit
) {
    val apps by viewModel.allApps.collectAsState()
    val shizukuConnected by viewModel.shizukuConnected.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    val bloatwareCount = apps.count {
        it.bloatInfo?.safetyTag == SafetyTag.RECOMMENDED_REMOVE
    }
    val safeToRemoveCount = apps.count {
        it.bloatInfo?.safetyTag == SafetyTag.SAFE_TO_REMOVE ||
                it.bloatInfo?.safetyTag == SafetyTag.REPLACEABLE
    }
    val unknownCount = apps.count { 
        it.bloatInfo == null || it.bloatInfo?.safetyTag == SafetyTag.UNKNOWN 
    }

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("DeBloatzzz", style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold)
                Text("Android Debloat Tool", style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (shizukuConnected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.errorContainer,
                border = BorderStroke(0.8.dp,
                    if (shizukuConnected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.error.copy(alpha = 0.3f))
            ) {
                Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        if (shizukuConnected) Icons.Outlined.CheckCircle else Icons.Outlined.Warning,
                        null, modifier = Modifier.size(12.dp),
                        tint = if (shizukuConnected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    )
                    Text(
                        if (shizukuConnected) "Shizuku OK" else "No Shizuku",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (shizukuConnected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(80.dp),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatCard(modifier = Modifier.weight(1f),
                        value = apps.size.toString(), label = "Total Apps",
                        containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    StatCard(modifier = Modifier.weight(1f),
                        value = bloatwareCount.toString(), label = "Bloatware",
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f))
                    StatCard(modifier = Modifier.weight(1f),
                        value = safeToRemoveCount.toString(), label = "Removable",
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
                }
            }

            if (!shizukuConnected) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.35f))
                ) {
                    Row(modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top) {
                        Icon(Icons.Outlined.Warning, null, modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.error)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Shizuku Not Connected", style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error)
                            Text("Install and start Shizuku to enable one-tap uninstall/disable. You can still browse app info and generate .bat scripts without Shizuku.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer)
                        }
                    }
                }
            }

            Text("Quick Actions", style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant)

            QuickActionCard(
                icon = Icons.Outlined.Apps,
                title = "Browse Apps",
                subtitle = "${apps.size} apps installed • Tap an app for full details, permissions & data info",
                onClick = onNavigateToAppList
            )
            QuickActionCard(
                icon = Icons.Outlined.CloudUpload,
                title = "Backup & Restore",
                subtitle = "Save list of selected apps before debloating so you can restore later",
                onClick = onNavigateToBackup
            )
            QuickActionCard(
                icon = Icons.Outlined.BugReport,
                title = "Submit Unknown Apps",
                subtitle = "$unknownCount unknown apps found. Send a report to help us update the database.",
                onClick = {
                    if (unknownCount > 0) {
                        val uri = viewModel.generateUnknownAppsReport(context)
                        if (uri != null) {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("aashuux@gmail.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "DeBloatzzz: Unknown Apps Report - ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}")
                                putExtra(Intent.EXTRA_TEXT, "Hello,\n\nPlease find the attached report of unknown apps found on my device.\n\nThank you!")
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(Intent.createChooser(intent, "Send Report via Email"))
                        } else {
                            Toast.makeText(context, "Error generating report.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "No unknown apps to report! You're all set.", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            QuickActionCard(
                icon = Icons.Outlined.Info,
                title = "How to Use",
                subtitle = "Step-by-step guide for Shizuku setup and safe debloating",
                onClick = onNavigateToGuide
            )

            Text("Safety Tag Guide", style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant)

            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)) {
                Column(modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TagLegendRow("✅ ESSENTIAL", "Core system app — do not remove under any circumstances")
                    TagLegendRow("🔴 BLOATWARE", "Recommended to remove — ads, telemetry, or useless")
                    TagLegendRow("🟢 SAFE REMOVE", "Can be safely removed with no system impact")
                    TagLegendRow("⚠️ CAUTION", "Remove only if you understand the impact")
                    TagLegendRow("🔄 REPLACEABLE", "A better or more private alternative exists")
                    TagLegendRow("❓ UNKNOWN", "No data in database — research before removing")
                }
            }

            Spacer(Modifier.height(90.dp))
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, value: String, label: String, containerColor: androidx.compose.ui.graphics.Color) {
    Surface(modifier = modifier, shape = RoundedCornerShape(14.dp), color = containerColor,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
        }
    }
}

@Composable
fun QuickActionCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant),
        onClick = onClick) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                Icon(icon, null, modifier = Modifier.size(44.dp).padding(10.dp),
                    tint = MaterialTheme.colorScheme.primary)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Outlined.ChevronRight, null, modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        }
    }
}

@Composable
fun TagLegendRow(tag: String, desc: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(tag, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(120.dp))
        Text(desc, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
    }
}