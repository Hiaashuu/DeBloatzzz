package com.hiaashuu.debloatzzz.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LooksOne
import androidx.compose.material.icons.outlined.LooksTwo
import androidx.compose.material.icons.outlined.Looks3
import androidx.compose.material.icons.outlined.Looks4
import androidx.compose.material.icons.outlined.Looks5
import androidx.compose.material.icons.outlined.Looks6
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.ui.theme.InfoBlue
import com.hiaashuu.debloatzzz.ui.theme.SuccessGreen
import com.hiaashuu.debloatzzz.ui.theme.WarningOrange

@Composable
fun HowToUseScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text(
                    text = "Guide & Disclaimer",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "How to use DeBloatzzz safely",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 90.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                ExpandableGuideCard(
                    icon = Icons.Filled.Warning,
                    iconColor = MaterialTheme.colorScheme.error,
                    title = "⚠️ Disclaimer — Read Before Using",
                    initiallyExpanded = true
                ) {
                    val points = listOf(
                        "DeBloatzzz is a tool that helps you remove pre-installed system apps. Use at your own risk.",
                        "Removing the wrong system app can break features, cause crashes, or create a boot loop.",
                        "Apps removed via Shizuku (pm uninstall --user 0) are NOT permanently deleted — they are hidden for User 0 and can be restored.",
                        "Always create a backup before removing apps using the Backup tab.",
                        "The developer of DeBloatzzz is not responsible for any damage, data loss, or device instability.",
                        "DeBloatzzz does NOT collect or transmit any of your data.",
                        "If something breaks after removing an app, restore it using the restore script or the Backup & Restore tab.",
                        "Safety tags are based on community knowledge and may not cover all device variants."
                    )
                    points.forEach { point ->
                        BulletPoint(text = point, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            item {
                ExpandableGuideCard(
                    icon = Icons.Filled.Shield,
                    iconColor = InfoBlue,
                    title = "Understanding Safety Tags"
                ) {
                    TagLegendRow("KEEP", SuccessGreen.copy(green = 0.4f, red = 0.2f), "Critical system app. Never remove.")
                    Spacer(modifier = Modifier.height(6.dp))
                    TagLegendRow("SAFE TO REMOVE", SuccessGreen, "Safe to remove on most devices without issue.")
                    Spacer(modifier = Modifier.height(6.dp))
                    TagLegendRow("RECOMMENDED REMOVE", Color(0xFFE53935), "Bloatware, adware, or spyware. Strongly recommended to remove.")
                    Spacer(modifier = Modifier.height(6.dp))
                    TagLegendRow("CAUTION", WarningOrange, "Remove with caution. May affect certain device features.")
                    Spacer(modifier = Modifier.height(6.dp))
                    TagLegendRow("REPLACEABLE", Color(0xFFFFC857), "Can be replaced with a better third-party alternative.")
                    Spacer(modifier = Modifier.height(6.dp))
                    TagLegendRow("UNKNOWN", Color(0xFF78909C), "App is not in the DeBloatzzz database.")
                }
            }

            item {
                ExpandableGuideCard(
                    icon = Icons.Filled.Link,
                    iconColor = MaterialTheme.colorScheme.primary,
                    title = "Setting Up Shizuku (No Root Required)"
                ) {
                    Text(
                        text = "Method 1: Wireless Debugging (Android 11+, No PC needed)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val wirelessSteps = listOf(
                        "Go to Settings → About Phone → tap Build Number 7 times to enable Developer Options",
                        "Open Settings → System → Developer Options",
                        "Enable 'Wireless Debugging'",
                        "Install Shizuku from Google Play Store or GitHub",
                        "Open Shizuku and tap 'Pair using Wireless Debugging'",
                        "A notification will appear — open Wireless Debugging → 'Pair device with pairing code'",
                        "Enter the pairing code from the notification into the Wireless Debugging dialog",
                        "Go back to Shizuku and tap START — you should see 'Shizuku is running'",
                        "Open DeBloatzzz and tap 'Grant Permission' on the Home screen",
                        "Authorize DeBloatzzz in Shizuku's Authorized Apps list"
                    )
                    wirelessSteps.forEachIndexed { index, step ->
                        NumberedStep(number = index + 1, text = step)
                        if (index < wirelessSteps.size - 1) Spacer(modifier = Modifier.height(5.dp))
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Method 2: ADB via PC (Android 6+)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val adbSteps = listOf(
                        "Install ADB (Android Platform Tools) on your PC",
                        "Enable USB Debugging in Developer Options",
                        "Connect phone to PC with a USB cable and trust the connection",
                        "Run: adb shell sh /sdcard/Android/data/moe.shizuku.privileged.api/start.sh",
                        "Shizuku will start — grant permission to DeBloatzzz"
                    )
                    adbSteps.forEachIndexed { index, step ->
                        NumberedStep(number = index + 1, text = step)
                        if (index < adbSteps.size - 1) Spacer(modifier = Modifier.height(5.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "⚠️ Note: Shizuku requires manual restart after each device reboot when using Wireless Debugging or ADB method (not rooted). " +
                                "Rooted users can use the Sui Magisk module for persistent Shizuku.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(10.dp),
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            item {
                ExpandableGuideCard(
                    icon = Icons.Outlined.CheckCircle,
                    iconColor = SuccessGreen,
                    title = "How to Debloat Safely"
                ) {
                    val steps = listOf(
                        "Start Shizuku and grant permission to DeBloatzzz (see above).",
                        "Go to the Backup tab → tap + to create a backup of all installed apps.",
                        "Go to the Apps tab → use the filter chips to find bloatware.",
                        "Tap any app to see detailed info: description, safety tag, alternatives, and warnings.",
                        "Review the safety tag carefully — only remove SAFE TO REMOVE or RECOMMENDED REMOVE apps.",
                        "Tap 'Uninstall for User 0' to remove the app. This is reversible.",
                        "If something breaks, go to the Backup & Restore tab and restore the affected app."
                    )
                    steps.forEachIndexed { index, step ->
                        NumberedStep(number = index + 1, text = step)
                        if (index < steps.size - 1) Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }

            item {
                ExpandableGuideCard(
                    icon = Icons.Filled.Terminal,
                    iconColor = WarningOrange,
                    title = "Restoring Removed Apps"
                ) {
                    Text(
                        text = "Method 1: Via DeBloatzzz (Shizuku required)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    val shizukuRestore = listOf(
                        "Connect Shizuku and grant DeBloatzzz permission.",
                        "Open the Apps tab and search for the package name of the removed app.",
                        "If it shows as 'Not Installed', tap on it and press 'Restore App'.",
                        "Alternatively, go to Backup & Restore → select a backup → press Restore."
                    )
                    shizukuRestore.forEachIndexed { index, step ->
                        NumberedStep(number = index + 1, text = step)
                        if (index < shizukuRestore.size - 1) Spacer(modifier = Modifier.height(5.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Method 2: Via ADB on PC",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Run the restore .bat script generated by DeBloatzzz, or manually run:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "adb shell cmd package install-existing --user 0 com.package.name",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Method 3: Factory Reset (last resort)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "If nothing else works and your device is unstable, a factory reset will restore ALL pre-installed apps.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 16.sp
                    )
                }
            }

            item {
                ExpandableGuideCard(
                    icon = Icons.Filled.Info,
                    iconColor = InfoBlue,
                    title = "Frequently Asked Questions"
                ) {
                    FaqItem(
                        question = "Will removing apps with DeBloatzzz brick my device?",
                        answer = "No. DeBloatzzz uses 'pm uninstall --user 0' which only removes the app for the current user. The APK remains on the device and can be restored. You cannot brick a device this way."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FaqItem(
                        question = "Do I need root?",
                        answer = "No. DeBloatzzz uses Shizuku which works with ADB privileges (shell user). No root is required."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FaqItem(
                        question = "Why does Shizuku stop after reboot?",
                        answer = "On non-rooted devices, Shizuku uses temporary ADB/Wireless Debugging privileges that expire on reboot. You need to restart Shizuku after each reboot. For persistent Shizuku, use the Sui Magisk module (requires root)."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FaqItem(
                        question = "Can I restore apps that don't come back with 'install-existing'?",
                        answer = "User-installed apps (not system apps) cannot be restored via ADB install-existing. Only apps that originally came with the ROM (system partition) can be restored this way. For user apps, reinstall from Play Store."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FaqItem(
                        question = "My app isn't in the database. Is it safe to remove?",
                        answer = "If an app shows UNKNOWN tag, it's not in DeBloatzzz's database. Research the package name before removing it. A good resource is the UAD-NG GitHub repository."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    FaqItem(
                        question = "Can DeBloatzzz read my personal data?",
                        answer = "No. DeBloatzzz only uses Shizuku to run package manager commands. It does not read files, contacts, messages, or any personal data."
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandableGuideCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 14.dp, end = 14.dp, bottom = 14.dp)
                ) {
                    Divider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    content()
                }
            }
        }
    }
}

@Composable
private fun BulletPoint(text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 1.dp, end = 6.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            lineHeight = 17.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NumberedStep(number: Int, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "$number",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                fontSize = 10.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 17.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TagLegendRow(label: String, color: Color, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = color.copy(alpha = 0.12f),
            border = BorderStroke(0.8.dp, color.copy(alpha = 0.5f))
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 9.sp,
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
            lineHeight = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FaqItem(question: String, answer: String) {
    Column {
        Text(
            text = "Q: $question",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            lineHeight = 17.sp
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = answer,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
            lineHeight = 17.sp
        )
    }
}

private fun Modifier.clickable(onClick: () -> Unit): Modifier {
    return this.then(Modifier.clickable(onClick = onClick))
}