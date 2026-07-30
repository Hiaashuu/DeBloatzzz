package com.hiaashuu.debloatzzz.data

import com.hiaashuu.debloatzzz.model.PermissionInfo

object PermissionCatalog {

    private val catalog: Map<String, PermissionInfo> = mapOf(

        "android.permission.ACCESS_FINE_LOCATION" to PermissionInfo(
            name = "android.permission.ACCESS_FINE_LOCATION",
            label = "Precise Location (GPS)",
            description = "Allows the app to get your exact GPS coordinates at any time.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.ACCESS_COARSE_LOCATION" to PermissionInfo(
            name = "android.permission.ACCESS_COARSE_LOCATION",
            label = "Approximate Location",
            description = "Allows the app to access your approximate location via Wi-Fi or cell towers.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.ACCESS_BACKGROUND_LOCATION" to PermissionInfo(
            name = "android.permission.ACCESS_BACKGROUND_LOCATION",
            label = "Background Location (Always On)",
            description = "Allows location access even when the app is closed or not in use. High privacy risk.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.CAMERA" to PermissionInfo(
            name = "android.permission.CAMERA",
            label = "Camera",
            description = "Allows the app to take photos and videos using the camera.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.RECORD_AUDIO" to PermissionInfo(
            name = "android.permission.RECORD_AUDIO",
            label = "Microphone / Record Audio",
            description = "Allows the app to record audio using the microphone at any time.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.READ_CONTACTS" to PermissionInfo(
            name = "android.permission.READ_CONTACTS",
            label = "Read Contacts",
            description = "Allows the app to read all your saved contacts, including names, emails, and phone numbers.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.WRITE_CONTACTS" to PermissionInfo(
            name = "android.permission.WRITE_CONTACTS",
            label = "Write Contacts",
            description = "Allows the app to add, edit, or delete your contacts.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.GET_ACCOUNTS" to PermissionInfo(
            name = "android.permission.GET_ACCOUNTS",
            label = "Access Account List",
            description = "Allows the app to see a list of all accounts (Google, Samsung, etc.) on your device.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.READ_PHONE_STATE" to PermissionInfo(
            name = "android.permission.READ_PHONE_STATE",
            label = "Read Phone State",
            description = "Allows reading the device IMEI, SIM serial, phone number, and call state. Used for device fingerprinting.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.CALL_PHONE" to PermissionInfo(
            name = "android.permission.CALL_PHONE",
            label = "Make Phone Calls",
            description = "Allows the app to initiate phone calls without your confirmation.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.READ_CALL_LOG" to PermissionInfo(
            name = "android.permission.READ_CALL_LOG",
            label = "Read Call Log",
            description = "Allows the app to read your entire call history.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.PROCESS_OUTGOING_CALLS" to PermissionInfo(
            name = "android.permission.PROCESS_OUTGOING_CALLS",
            label = "Intercept Outgoing Calls",
            description = "Allows the app to see the number being dialed and redirect or stop outgoing calls.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.READ_SMS" to PermissionInfo(
            name = "android.permission.READ_SMS",
            label = "Read SMS Messages",
            description = "Allows the app to read all SMS messages on your device, including OTP codes.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.SEND_SMS" to PermissionInfo(
            name = "android.permission.SEND_SMS",
            label = "Send SMS Messages",
            description = "Allows the app to send SMS messages, which may incur charges.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.RECEIVE_SMS" to PermissionInfo(
            name = "android.permission.RECEIVE_SMS",
            label = "Receive SMS",
            description = "Allows the app to intercept incoming SMS messages before they reach the default app.",
            isCritical = true,
            isDangerous = true
        ),

        "android.permission.READ_EXTERNAL_STORAGE" to PermissionInfo(
            name = "android.permission.READ_EXTERNAL_STORAGE",
            label = "Read Storage",
            description = "Allows the app to read all files on your SD card and shared storage.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.WRITE_EXTERNAL_STORAGE" to PermissionInfo(
            name = "android.permission.WRITE_EXTERNAL_STORAGE",
            label = "Write to Storage",
            description = "Allows the app to write or modify files on your shared storage.",
            isCritical = false,
            isDangerous = true
        ),
        "android.permission.MANAGE_EXTERNAL_STORAGE" to PermissionInfo(
            name = "android.permission.MANAGE_EXTERNAL_STORAGE",
            label = "Manage All Files",
            description = "Grants full access to all files on the device, including sensitive directories. Very powerful.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.READ_MEDIA_IMAGES" to PermissionInfo(
            name = "android.permission.READ_MEDIA_IMAGES",
            label = "Read Photos",
            description = "Allows the app to read photos stored on your device.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.READ_MEDIA_VIDEO" to PermissionInfo(
            name = "android.permission.READ_MEDIA_VIDEO",
            label = "Read Videos",
            description = "Allows the app to read video files stored on your device.",
            isCritical = false,
            isDangerous = true
        ),
        "android.permission.READ_MEDIA_AUDIO" to PermissionInfo(
            name = "android.permission.READ_MEDIA_AUDIO",
            label = "Read Audio Files",
            description = "Allows the app to read audio/music files stored on your device.",
            isCritical = false,
            isDangerous = true
        ),

        "android.permission.READ_CALENDAR" to PermissionInfo(
            name = "android.permission.READ_CALENDAR",
            label = "Read Calendar",
            description = "Allows the app to read all your calendar events and appointments.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.WRITE_CALENDAR" to PermissionInfo(
            name = "android.permission.WRITE_CALENDAR",
            label = "Write Calendar",
            description = "Allows the app to add, edit, or delete calendar events.",
            isCritical = false,
            isDangerous = true
        ),

        "android.permission.BODY_SENSORS" to PermissionInfo(
            name = "android.permission.BODY_SENSORS",
            label = "Body Sensors",
            description = "Allows the app to access data from sensors like heart rate monitors.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.ACTIVITY_RECOGNITION" to PermissionInfo(
            name = "android.permission.ACTIVITY_RECOGNITION",
            label = "Physical Activity Recognition",
            description = "Allows the app to detect physical activity (walking, running, etc.).",
            isCritical = false,
            isDangerous = true
        ),

        "android.permission.INTERNET" to PermissionInfo(
            name = "android.permission.INTERNET",
            label = "Internet Access",
            description = "Allows the app to connect to the internet. Required for almost all online functionality.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.ACCESS_NETWORK_STATE" to PermissionInfo(
            name = "android.permission.ACCESS_NETWORK_STATE",
            label = "View Network State",
            description = "Allows the app to check if the device is connected to a network.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.ACCESS_WIFI_STATE" to PermissionInfo(
            name = "android.permission.ACCESS_WIFI_STATE",
            label = "View Wi-Fi State",
            description = "Allows the app to view information about Wi-Fi networks, including SSIDs.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.CHANGE_WIFI_STATE" to PermissionInfo(
            name = "android.permission.CHANGE_WIFI_STATE",
            label = "Change Wi-Fi State",
            description = "Allows the app to connect or disconnect from Wi-Fi networks.",
            isCritical = false,
            isDangerous = false
        ),

        "android.permission.BLUETOOTH" to PermissionInfo(
            name = "android.permission.BLUETOOTH",
            label = "Bluetooth",
            description = "Allows the app to connect to paired Bluetooth devices.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.BLUETOOTH_SCAN" to PermissionInfo(
            name = "android.permission.BLUETOOTH_SCAN",
            label = "Scan Nearby Bluetooth Devices",
            description = "Allows scanning for nearby Bluetooth devices. Can be used for location tracking.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.BLUETOOTH_CONNECT" to PermissionInfo(
            name = "android.permission.BLUETOOTH_CONNECT",
            label = "Connect to Bluetooth Devices",
            description = "Allows the app to connect to paired Bluetooth devices.",
            isCritical = false,
            isDangerous = true
        ),

        "android.permission.RECEIVE_BOOT_COMPLETED" to PermissionInfo(
            name = "android.permission.RECEIVE_BOOT_COMPLETED",
            label = "Auto-Start on Boot",
            description = "Allows the app to automatically start every time your device boots. Can affect battery life.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.FOREGROUND_SERVICE" to PermissionInfo(
            name = "android.permission.FOREGROUND_SERVICE",
            label = "Run as Foreground Service",
            description = "Allows the app to run a persistent background service that cannot be killed by the system.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.WAKE_LOCK" to PermissionInfo(
            name = "android.permission.WAKE_LOCK",
            label = "Prevent Phone from Sleeping",
            description = "Allows the app to keep the processor awake even when you're not using the phone.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.VIBRATE" to PermissionInfo(
            name = "android.permission.VIBRATE",
            label = "Vibration Control",
            description = "Allows the app to vibrate the device.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.USE_BIOMETRIC" to PermissionInfo(
            name = "android.permission.USE_BIOMETRIC",
            label = "Use Biometrics (Fingerprint / Face)",
            description = "Allows the app to use fingerprint or face unlock for authentication.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.USE_FINGERPRINT" to PermissionInfo(
            name = "android.permission.USE_FINGERPRINT",
            label = "Use Fingerprint",
            description = "Legacy permission to use fingerprint sensor for authentication.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.REQUEST_INSTALL_PACKAGES" to PermissionInfo(
            name = "android.permission.REQUEST_INSTALL_PACKAGES",
            label = "Install Unknown Apps",
            description = "Allows the app to install other APK files on your device without going through the Play Store. High risk.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.SYSTEM_ALERT_WINDOW" to PermissionInfo(
            name = "android.permission.SYSTEM_ALERT_WINDOW",
            label = "Draw Over Other Apps",
            description = "Allows the app to display overlays on top of all other apps, which can be abused for UI spoofing attacks.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.BIND_DEVICE_ADMIN" to PermissionInfo(
            name = "android.permission.BIND_DEVICE_ADMIN",
            label = "Device Administrator",
            description = "Grants device-level admin controls including remote wipe. Extremely powerful. Used by stalkerware.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.READ_LOGS" to PermissionInfo(
            name = "android.permission.READ_LOGS",
            label = "Read System Logs",
            description = "Allows the app to read sensitive system and crash logs from all other apps.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.PACKAGE_USAGE_STATS" to PermissionInfo(
            name = "android.permission.PACKAGE_USAGE_STATS",
            label = "App Usage Statistics",
            description = "Allows the app to collect data about how long and how often you use each app.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.QUERY_ALL_PACKAGES" to PermissionInfo(
            name = "android.permission.QUERY_ALL_PACKAGES",
            label = "View All Installed Apps",
            description = "Allows the app to see a full list of every app installed on your device.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.INSTALL_PACKAGES" to PermissionInfo(
            name = "android.permission.INSTALL_PACKAGES",
            label = "Install Packages (System Level)",
            description = "System-level permission to silently install apps without any user prompt.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.DELETE_PACKAGES" to PermissionInfo(
            name = "android.permission.DELETE_PACKAGES",
            label = "Delete Packages (System Level)",
            description = "System-level permission to silently uninstall apps without user confirmation.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.CHANGE_COMPONENT_ENABLED_STATE" to PermissionInfo(
            name = "android.permission.CHANGE_COMPONENT_ENABLED_STATE",
            label = "Enable/Disable App Components",
            description = "Allows toggling individual app components on or off at a system level.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.WRITE_SETTINGS" to PermissionInfo(
            name = "android.permission.WRITE_SETTINGS",
            label = "Modify System Settings",
            description = "Allows the app to change system-level settings like brightness and screen timeout.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.WRITE_SECURE_SETTINGS" to PermissionInfo(
            name = "android.permission.WRITE_SECURE_SETTINGS",
            label = "Modify Secure System Settings",
            description = "Allows changing sensitive system settings. Normally only granted to system apps.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.MANAGE_ACCOUNTS" to PermissionInfo(
            name = "android.permission.MANAGE_ACCOUNTS",
            label = "Manage Accounts",
            description = "Allows adding and removing accounts from the Accounts list.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.AUTHENTICATE_ACCOUNTS" to PermissionInfo(
            name = "android.permission.AUTHENTICATE_ACCOUNTS",
            label = "Authenticate Accounts",
            description = "Allows the app to act as an account authenticator (used by sync adapters).",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.BIND_ACCESSIBILITY_SERVICE" to PermissionInfo(
            name = "android.permission.BIND_ACCESSIBILITY_SERVICE",
            label = "Accessibility Service",
            description = "Grants the ability to observe and control all UI interactions, including text input. Extremely powerful.",
            isCritical = true,
            isDangerous = true
        ),
        "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" to PermissionInfo(
            name = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE",
            label = "Read All Notifications",
            description = "Allows the app to read the content of every notification on your device.",
            isCritical = true,
            isDangerous = false
        ),
        "android.permission.NFC" to PermissionInfo(
            name = "android.permission.NFC",
            label = "NFC",
            description = "Allows the app to use NFC hardware for reading tags or making payments.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.POST_NOTIFICATIONS" to PermissionInfo(
            name = "android.permission.POST_NOTIFICATIONS",
            label = "Send Notifications",
            description = "Allows the app to show notification banners. Required on Android 13+.",
            isCritical = false,
            isDangerous = true
        ),
        "android.permission.USE_EXACT_ALARM" to PermissionInfo(
            name = "android.permission.USE_EXACT_ALARM",
            label = "Set Exact Alarms",
            description = "Allows the app to schedule actions at precise times, even if the device is idle.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.SCHEDULE_EXACT_ALARM" to PermissionInfo(
            name = "android.permission.SCHEDULE_EXACT_ALARM",
            label = "Schedule Exact Alarms",
            description = "Allows scheduling exact timing events. Can keep the device awake.",
            isCritical = false,
            isDangerous = false
        ),
        "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" to PermissionInfo(
            name = "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS",
            label = "Ignore Battery Optimization",
            description = "Allows the app to run unrestricted in the background, bypassing battery saving limits.",
            isCritical = true,
            isDangerous = false
        ),
    )

    fun get(permissionName: String): PermissionInfo {
        return catalog[permissionName] ?: PermissionInfo(
            name = permissionName,
            label = permissionName.substringAfterLast('.').replace('_', ' ').lowercase()
                .replaceFirstChar { it.uppercase() },
            description = "System or OEM-specific permission. No detailed information available for this permission.",
            isCritical = false,
            isDangerous = false
        )
    }

    fun enrichPermissions(rawNames: List<String>): List<PermissionInfo> {
        return rawNames.map { get(it) }
    }
}