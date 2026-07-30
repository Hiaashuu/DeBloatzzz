package com.hiaashuu.debloatzzz.model

sealed class UninstallResult {
    object Success : UninstallResult()
    data class Failure(val reason: String) : UninstallResult()
    object ShizukuNotConnected : UninstallResult()
    object PermissionDenied : UninstallResult()
}

enum class SafetyTag {
    KEEP,
    SAFE_TO_REMOVE,
    RECOMMENDED_REMOVE,
    CAUTION,
    REPLACEABLE,
    UNKNOWN
}

enum class AppCategory {
    SYSTEM_CRITICAL,
    SAMSUNG_BLOAT,
    GOOGLE_SERVICE,
    GOOGLE_BLOAT,
    XIAOMI_MIUI,
    ONEPLUS_OPPO_REALME,
    STOCK_ANDROID,
    CARRIER_BLOAT,
    SOCIAL_MEDIA,
    UTILITY,
    COMMUNICATION,
    OEM_SERVICE
}

enum class DeviceType {
    ALL,
    SAMSUNG,
    XIAOMI,
    ONEPLUS,
    OPPO,
    REALME,
    VIVO,
    PIXEL,
    CARRIER
}

enum class AppFilter {
    ALL,
    USER_APPS,
    SYSTEM_APPS,
    BLOATWARE,
    DISABLED,
    SAMSUNG,
    GOOGLE,
    XIAOMI
}

enum class SortOrder {
    NAME_A_TO_Z,
    NAME_Z_TO_A,
    INSTALL_DATE_NEWEST,
    INSTALL_DATE_OLDEST,
    BLOATWARE_FIRST,
    SAFE_FIRST,
    TYPE_SYSTEM_FIRST,
    TYPE_USER_FIRST
}

data class PermissionInfo(
    val name: String,
    val label: String,
    val description: String,
    val isCritical: Boolean,
    val isDangerous: Boolean
)

data class BloatAppInfo(
    val packageName: String,
    val displayName: String,
    val description: String,
    val category: AppCategory,
    val safetyTag: SafetyTag,
    val deviceType: DeviceType,
    val canBeRestored: Boolean = true,
    val alternatives: List<String> = emptyList(),
    val warningNote: String = "",
    val developer: String = "",
    val dataCollected: List<String> = emptyList(),
    val permissions: List<PermissionInfo> = emptyList()
)

data class InstalledAppInfo(
    val packageName: String,
    val displayName: String,
    val isSystemApp: Boolean,
    val isEnabled: Boolean,
    val versionName: String,
    val versionCode: Long,
    val installTime: Long,
    val updateTime: Long,
    val bloatInfo: BloatAppInfo?,
    val isSelected: Boolean = false,
    val declaredPermissions: List<PermissionInfo> = emptyList(),
    val installer: String? = null,
    val apkSize: Long = 0L
)

data class BackupEntry(
    val id: String,
    val timestamp: Long,
    val deviceModel: String,
    val androidVersion: Int,
    val packages: List<String>,
    val filename: String,
    val label: String = ""
)

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AppList : Screen("app_list")
    object AppDetail : Screen("app_detail/{packageName}")
    object BackupRestore : Screen("backup_restore")
    object BatGenerator : Screen("bat_generator")
    object HowToUse : Screen("how_to_use")

    companion object {
        fun appDetailRoute(packageName: String): String {
            return "app_detail/${android.net.Uri.encode(packageName)}"
        }
    }
}