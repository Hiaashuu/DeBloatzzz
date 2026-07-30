package com.hiaashuu.debloatzzz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hiaashuu.debloatzzz.ui.theme.DisclaimerPrefs
import kotlinx.coroutines.delay

@Composable
fun DisclaimerScreen(onAccepted: () -> Unit) {
    val context = LocalContext.current
    var countdown by remember { mutableIntStateOf(5) }
    var canProceed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000)
            countdown--
        }
        canProceed = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                text = "⚠️ Important Disclaimer",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    DisclaimerSection(
                        title = "🔧 What This App Does",
                        body = "DeBloatzzz is a debloating tool that lets you uninstall, disable, or generate ADB scripts for system and pre-installed apps on your Android device."
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    DisclaimerSection(
                        title = "⚠️ Removing the Wrong App Can Break Your Device",
                        body = "Uninstalling essential system components (marked ESSENTIAL/KEEP) can cause bootloops, loss of calls, loss of internet, or require a factory reset to fix. Always read the warning note on each app before removing it."
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    DisclaimerSection(
                        title = "💡 Safety Tags Explained",
                        body = "• ✅ ESSENTIAL — Do not remove under any circumstances\n• 🔴 BLOATWARE — Safe to remove, recommended\n• 🟢 SAFE REMOVE — Can be removed without issues\n• ⚠️ CAUTION — Remove only if you understand the impact\n• 🔄 REPLACEABLE — A better alternative exists\n• ❓ UNKNOWN — No data in database; research before removing"
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    DisclaimerSection(
                        title = "🔑 Shizuku / ADB Required",
                        body = "This app requires Shizuku or ADB access to uninstall system apps. It does NOT require root. Make sure Shizuku is running before using the Uninstall feature."
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant)

                    DisclaimerSection(
                        title = "📋 No Warranty",
                        body = "The developer is not responsible for any device damage, data loss, or bootloops caused by using this app. You use DeBloatzzz entirely at your own risk. Always create a backup before debloating."
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (canProceed) {
                        DisclaimerPrefs.setAccepted(context)
                        onAccepted()
                    }
                },
                enabled = canProceed,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canProceed) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                if (canProceed) {
                    Text(
                        text = "I Understand — Continue",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                } else {
                    Text(
                        text = "Please read the disclaimer... ($countdown)",
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "This warning will only appear once.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DisclaimerSection(title: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
        )
    }
}