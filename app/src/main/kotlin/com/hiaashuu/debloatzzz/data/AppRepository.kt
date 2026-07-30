package com.hiaashuu.debloatzzz.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.hiaashuu.appinfo.AppInfoLibrary
import com.hiaashuu.debloatzzz.model.AppCategory
import com.hiaashuu.debloatzzz.model.BloatAppInfo
import com.hiaashuu.debloatzzz.model.DeviceType
import com.hiaashuu.debloatzzz.model.InstalledAppInfo
import com.hiaashuu.debloatzzz.model.SafetyTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val context: android.content.Context) {

    private val packageManager: PackageManager = context.packageManager

    private fun mapLibInfoToBloatInfo(libInfo: com.hiaashuu.appinfo.models.PackageAppInfo): BloatAppInfo {
        // Use .name to map Enums safely in case of library updates
        val safetyTag = when (libInfo.safetyLevel.name) {
            "CRITICAL" -> SafetyTag.KEEP
            "CAUTION" -> SafetyTag.CAUTION
            "SAFE" -> SafetyTag.SAFE_TO_REMOVE
            "BLOATWARE" -> SafetyTag.RECOMMENDED_REMOVE
            else -> SafetyTag.UNKNOWN
        }

        val category = when (libInfo.category.name) {
            "ANDROID_SYSTEM" -> AppCategory.STOCK_ANDROID
            "GOOGLE", "GOOGLE_CORE" -> AppCategory.GOOGLE_BLOAT
            "ONEPLUS_OPLUS", "HEYTAP_NEARME", "COLOROS_OPPO" -> AppCategory.ONEPLUS_OPPO_REALME
            "QUALCOMM" -> AppCategory.OEM_SERVICE
            "META_FACEBOOK" -> AppCategory.SOCIAL_MEDIA
            "MICROSOFT", "THIRD_PARTY" -> AppCategory.UTILITY
            else -> AppCategory.UTILITY
        }

        return BloatAppInfo(
            packageName = libInfo.packageName,
            displayName = libInfo.appName,
            description = libInfo.purpose + (libInfo.notes?.let { "\n\nNote: $it" } ?: ""),
            category = category,
            safetyTag = safetyTag,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = libInfo.replacements ?: emptyList(),
            warningNote = libInfo.impactIfDisabled ?: "",
            developer = libInfo.category.name.replace("_", " "),
            dataCollected = libInfo.privacyNote?.let { listOf(it) } ?: emptyList(),
            permissions = emptyList() // Resolved dynamically via PackageInfo
        )
    }

    suspend fun getAllInstalledApps(): List<InstalledAppInfo> {
        return withContext(Dispatchers.IO) {
            val packages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledPackages(
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
                )
            } else {
                @Suppress("DEPRECATION")
                packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
            }

            packages.mapNotNull { packageInfo ->
                try {
                    if (packageInfo.packageName == "moe.shizuku.privileged.api" ||
                        packageInfo.packageName == "com.hiaashuu.debloatzzz" ||
                        packageInfo.packageName == context.packageName) {
                        return@mapNotNull null
                    }
                    
                    val appInfo = packageInfo.applicationInfo ?: return@mapNotNull null
                    val label = packageManager.getApplicationLabel(appInfo).toString()
                    val isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                    val isEnabled = appInfo.enabled
                    
                    val installer = try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            packageManager.getInstallSourceInfo(packageInfo.packageName).installingPackageName
                        } else {
                            @Suppress("DEPRECATION")
                            packageManager.getInstallerPackageName(packageInfo.packageName)
                        }
                    } catch (e: Exception) { null }
                    
                    val apkSize = try {
                        java.io.File(appInfo.sourceDir).length()
                    } catch (e: Exception) { 0L }

                    // Primary fetch from Daddychill's AppInfoLibrary, fallback to local AppDataSource
                    val libAppInfo = try {
                        AppInfoLibrary.getInfo(packageInfo.packageName)
                    } catch (e: Exception) { null }

                    val bloatInfo = if (libAppInfo != null) {
                        mapLibInfoToBloatInfo(libAppInfo)
                    } else {
                        AppDataSource.getBloatInfo(packageInfo.packageName)
                    }

                    val rawPerms = packageInfo.requestedPermissions?.toList() ?: emptyList()
                    val enrichedPerms = PermissionCatalog.enrichPermissions(rawPerms)

                    InstalledAppInfo(
                        packageName = packageInfo.packageName,
                        displayName = label,
                        isSystemApp = isSystem,
                        isEnabled = isEnabled,
                        versionName = packageInfo.versionName ?: "N/A",
                        versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            packageInfo.longVersionCode
                        } else {
                            @Suppress("DEPRECATION")
                            packageInfo.versionCode.toLong()
                        },
                        installTime = packageInfo.firstInstallTime,
                        updateTime = packageInfo.lastUpdateTime,
                        bloatInfo = bloatInfo,
                        declaredPermissions = enrichedPerms,
                        installer = installer,
                        apkSize = apkSize
                    )
                } catch (e: Exception) {
                    null
                }
            }.sortedWith(
                compareByDescending<InstalledAppInfo> { it.bloatInfo != null }
                    .thenBy { it.displayName.lowercase() }
            )
        }
    }

    fun getAppDisplayName(packageName: String): String {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }

    fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun isPackageEnabled(packageName: String): Boolean {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            appInfo.enabled
        } catch (e: Exception) {
            false
        }
    }
}