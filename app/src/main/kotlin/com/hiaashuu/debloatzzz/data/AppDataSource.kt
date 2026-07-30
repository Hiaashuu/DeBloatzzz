package com.hiaashuu.debloatzzz.data

import com.hiaashuu.debloatzzz.model.AppCategory
import com.hiaashuu.debloatzzz.model.BloatAppInfo
import com.hiaashuu.debloatzzz.model.DeviceType
import com.hiaashuu.debloatzzz.model.PermissionInfo
import com.hiaashuu.debloatzzz.model.SafetyTag

object AppDataSource {

    private fun perm(name: String, label: String, desc: String, critical: Boolean = false, dangerous: Boolean = false) =
        PermissionInfo(name, label, desc, critical, dangerous)

    private val database: Map<String, BloatAppInfo> = buildList {

        add(BloatAppInfo(
            packageName = "com.google.android.gms",
            displayName = "Google Play Services",
            description = "The core framework that underlies virtually all Google and third-party apps on Android. Handles authentication tokens, background sync, push notifications via Firebase Cloud Messaging, location APIs, and Google account sign-in. Without it, most apps will crash, fail to sign in, or lose push notification support entirely.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing this will break authentication in nearly every app and cause widespread crashes.",
            developer = "Google LLC",
            dataCollected = listOf("Device identifiers (IMEI, ANDROID_ID)", "Location history", "App usage patterns", "Account credentials cache"),
            permissions = listOf(
                perm("android.permission.ACCESS_FINE_LOCATION", "Precise Location", "Used for location APIs provided to other apps", critical = true, dangerous = true),
                perm("android.permission.READ_CONTACTS", "Read Contacts", "Allows syncing contact data", critical = true, dangerous = true),
                perm("android.permission.GET_ACCOUNTS", "Account Access", "Manages Google account tokens", critical = true, dangerous = true),
                perm("android.permission.READ_PHONE_STATE", "Phone State", "Reads device identifiers for authentication", critical = true, dangerous = true),
                perm("android.permission.CAMERA", "Camera", "Requested for AR and Lens APIs", critical = true, dangerous = true),
                perm("android.permission.RECORD_AUDIO", "Microphone", "Used by voice search and Assistant APIs", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.gsf",
            displayName = "Google Services Framework",
            description = "The low-level foundation layer that Google Play Services is built upon. Manages the C2DM/GCM/FCM legacy push notification protocol, Google account tokens at the OS level, and device registration with Google servers. This was the predecessor to modern Play Services.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Required for the Google ecosystem to function. Do not remove.",
            developer = "Google LLC",
            dataCollected = listOf("Device registration ID", "Google account identifiers")
        ))

        add(BloatAppInfo(
            packageName = "com.android.vending",
            displayName = "Google Play Store",
            description = "The official Android app marketplace. Manages app downloads, updates, license verification for paid apps, and in-app purchase verification. Many apps check for Play Store presence and will refuse to run without it.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("F-Droid", "Aurora Store"),
            warningNote = "Remove only if completely switching to a third-party app store like F-Droid or Aurora Store.",
            developer = "Google LLC",
            dataCollected = listOf("Installed app list", "Purchase history", "Device fingerprint")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.permissioncontroller",
            displayName = "Permission Controller",
            description = "The system component that manages all app permission dialogs, the Privacy Dashboard, and permission auto-reset for unused apps. It is the gatekeeper for all dangerous permissions on Android 6+.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing this breaks permission management for all apps.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.ext.services",
            displayName = "Android Services Library",
            description = "An updatable extension library shipped by Google that provides components like the smart text selection engine, autofill classification, and on-device ML features used by the Android framework itself.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Required for Android system stability and Smart Text Selection.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.android.phone",
            displayName = "Phone / Dialer Service",
            description = "The core telephony service that handles all voice calls, GSM/CDMA protocol management, and the radio interface layer. Without this, the device cannot make or receive any phone calls.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing this disables all phone calls.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.settings",
            displayName = "Settings",
            description = "The system Settings application. Provides UI for configuring every aspect of the device from network and display to privacy and developer options. Also acts as a coordination hub for many system features.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Do not remove under any circumstances.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.systemui",
            displayName = "System UI",
            description = "Draws and manages the status bar, navigation bar, quick settings panel, lock screen, and notification shade. This is the visual shell of Android itself.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing causes bootloop. Do not touch.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.launcher3",
            displayName = "Android Launcher (Home Screen)",
            description = "The default AOSP home screen launcher. Provides the app grid, widget host, and recent apps switcher. Only safe to remove if another launcher (like OEM launcher) is set as default.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("OEM Launcher (OnePlus Launcher)", "Nova Launcher", "Lawnchair"),
            warningNote = "⚠️ Remove only if another launcher is installed and set as default.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.providers.media",
            displayName = "Media Storage Provider",
            description = "Manages the media database (Music, Photos, Videos) and the MediaStore API. Apps use this to query and access media files. Without it, file pickers and gallery apps will stop working.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Required for all media access.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.providers.contacts",
            displayName = "Contacts Storage",
            description = "The database that stores all contacts on the device. Without this, no contacts app can read or write contact data.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing deletes all contacts and breaks contact access.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.providers.telephony",
            displayName = "Telephony Provider (SMS Storage)",
            description = "Stores all SMS and MMS messages. The default SMS app relies on this provider to read and write messages.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing deletes all SMS and breaks messaging.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.providers.settings",
            displayName = "Settings Storage Provider",
            description = "A content provider that stores all system, secure, and global Android settings. Without it, Settings cannot be read or written.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing causes immediate bootloop.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.bluetooth",
            displayName = "Bluetooth System Service",
            description = "The core Bluetooth stack implementation for Android. Handles all Bluetooth Classic and BLE connections, pairing, and profile management.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing disables all Bluetooth functionality.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.nfc",
            displayName = "NFC Service",
            description = "Manages the NFC hardware and provides the Android Beam and Host Card Emulation APIs. Required for tap-to-pay (Google Pay) and NFC tag reading.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "⚠️ Removes NFC and contactless payment support. Keep if you use Google Pay.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.keychain",
            displayName = "Android Keychain",
            description = "Manages SSL/TLS certificates and cryptographic keys used for secure connections by VPNs, enterprise Wi-Fi (EAP), and certificate-based authentication.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Required for VPN, corporate Wi-Fi, and secure certificates.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.vpndialogs",
            displayName = "VPN Dialogs",
            description = "Shows the system-level VPN permission dialog when an app requests VPN access. Required for any VPN app to work.",
            category = AppCategory.SYSTEM_CRITICAL,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ALL,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing this prevents any VPN app from connecting.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.inputmethod.latin",
            displayName = "AOSP Keyboard (Latin IME)",
            description = "The default AOSP on-screen keyboard. Required as a fallback if no other keyboard is installed.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Gboard", "SwiftKey", "FlorisBoard"),
            warningNote = "⚠️ Keep at least one keyboard installed. Remove only if Gboard or another IME is set as default.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.brickmode",
            displayName = "OnePlus Brick Mode",
            description = "A OnePlus security feature that completely locks down the phone — disabling all apps, calls, and data — via a special button sequence. Designed for situations where you want to prevent unauthorized access (e.g. at a border crossing). Very niche use case.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Safe to remove if you never use this feature.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.opbackup",
            displayName = "OnePlus Backup",
            description = "OnePlus's built-in backup and restore solution for OxygenOS. Creates local backups of contacts, SMS, call logs, app data, and settings. Useful only if you prefer local over cloud backup.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Google One Backup", "SeedVault", "Neo Backup (root)"),
            warningNote = "Remove if using Google backup. Keep if you rely on local backups.",
            developer = "OnePlus Technology",
            dataCollected = listOf("Contacts", "SMS history", "App data")
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.account",
            displayName = "OnePlus / OPPO Account",
            description = "The OnePlus account service used for OnePlus Cloud backup, Forum login, and device finder. Runs persistent background sync services.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Google Account"),
            warningNote = "Remove if not using OnePlus Cloud or the official OnePlus forums app.",
            developer = "OnePlus Technology",
            dataCollected = listOf("Account info", "Device telemetry", "Backup data")
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.gallery",
            displayName = "OnePlus Gallery",
            description = "OnePlus's default photo and video gallery app. Supports OxygenOS-specific features like Smart Album organization. Some versions send photo metadata to OPPO cloud servers.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Simple Gallery Pro", "Ente Photos", "Google Photos"),
            warningNote = "Replace with Simple Gallery Pro for a fully local, ad-free gallery.",
            developer = "OnePlus Technology",
            dataCollected = listOf("Photo metadata", "Album structure")
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.filemanager",
            displayName = "OnePlus File Manager",
            description = "The built-in file manager for OxygenOS. Basic file browsing, zip support, and cloud service integration.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("MiXplorer", "Solid Explorer", "Files by Google"),
            warningNote = "Replace with MiXplorer for more powerful file management.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.camera",
            displayName = "OnePlus Camera",
            description = "The stock OxygenOS camera app with Hasselblad tuning on newer models. Required for accessing all camera hardware modes (ProShot, Night Mode, etc.) on OnePlus devices.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Keep unless using a third-party camera app that supports your hardware fully.",
            developer = "OnePlus Technology",
            permissions = listOf(
                perm("android.permission.CAMERA", "Camera", "Core function", critical = true, dangerous = true),
                perm("android.permission.RECORD_AUDIO", "Microphone", "For video recording", critical = true, dangerous = true),
                perm("android.permission.ACCESS_FINE_LOCATION", "Location", "For photo geotagging", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.dialer",
            displayName = "OnePlus Phone / Dialer",
            description = "OnePlus's stock dialer app, styled for OxygenOS. Handles call management, contacts integration, and spam detection. Replaces the AOSP Phone app.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Keep as primary dialer. Only remove if using a third-party dialer.",
            developer = "OnePlus Technology",
            permissions = listOf(
                perm("android.permission.READ_PHONE_STATE", "Phone State", "Call management", critical = true, dangerous = true),
                perm("android.permission.READ_CONTACTS", "Contacts", "Caller ID lookup", critical = true, dangerous = true),
                perm("android.permission.READ_CALL_LOG", "Call Log", "Displays call history", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.contacts",
            displayName = "OnePlus Contacts",
            description = "OnePlus's contacts management app, styled for OxygenOS. Syncs with Google contacts and provides a dialer-integrated contact list.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Simple Contacts Pro", "Google Contacts"),
            warningNote = "Replace with Simple Contacts Pro for local-only storage without cloud sync.",
            developer = "OnePlus Technology",
            permissions = listOf(
                perm("android.permission.READ_CONTACTS", "Read Contacts", "Core function", critical = true, dangerous = true),
                perm("android.permission.WRITE_CONTACTS", "Write Contacts", "Edit contacts", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.launcher",
            displayName = "OnePlus Launcher (OxygenOS Home)",
            description = "The default home screen launcher for OxygenOS. Provides the Shelf feature and OnePlus-specific gestures. Without it you'll need another launcher set as default.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Nova Launcher", "Lawnchair 2", "Microsoft Launcher"),
            warningNote = "⚠️ Only remove if another launcher is installed and set as default. Device becomes unusable without a launcher.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.recorder",
            displayName = "OnePlus Recorder",
            description = "Voice recorder app for OxygenOS. Supports live transcription on newer models. Uses the microphone in the background when recording is active.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Record You (FOSS)", "Easy Voice Recorder"),
            warningNote = "Safe to remove if you use another recorder app.",
            developer = "OnePlus Technology",
            permissions = listOf(
                perm("android.permission.RECORD_AUDIO", "Microphone", "Core function — recording", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.notes",
            displayName = "OnePlus Notes",
            description = "OnePlus's built-in note-taking app. Syncs notes to OnePlus Cloud if account is signed in.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Standard Notes", "Joplin", "Notally"),
            warningNote = "Replace with Standard Notes for end-to-end encrypted notes.",
            developer = "OnePlus Technology",
            dataCollected = listOf("Note content (if cloud sync is enabled)")
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.weather",
            displayName = "OnePlus Weather",
            description = "Stock weather app for OxygenOS. Collects location to display local weather and sends telemetry back to weather data providers.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Geometric Weather", "Weather.gov", "Breezy Weather (FOSS)"),
            warningNote = "Replace with an open-source weather app for better privacy.",
            developer = "OnePlus Technology",
            dataCollected = listOf("Precise location"),
            permissions = listOf(
                perm("android.permission.ACCESS_FINE_LOCATION", "Precise Location", "Fetches local weather", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.clock",
            displayName = "OnePlus Clock",
            description = "Stock clock app with alarms, world clock, timer, and stopwatch. Tied to OxygenOS clock face animations.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("AOSP Clock", "Google Clock"),
            warningNote = "Safe to remove if using Google Clock or another alarm app.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.calculator",
            displayName = "OnePlus Calculator",
            description = "OnePlus's built-in calculator. Basic and scientific modes. No network access.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            alternatives = listOf("Google Calculator", "OpenCalc"),
            warningNote = "Safe to replace with any calculator app.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.flashlight",
            displayName = "OnePlus Flashlight",
            description = "Dedicated flashlight app for OxygenOS. Redundant — the quick settings flashlight tile achieves the same function.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Quick Settings flashlight tile is sufficient. Safe to remove.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.soundrecorder",
            displayName = "OnePlus Sound Recorder (Legacy)",
            description = "An older version of the OnePlus recorder app present on earlier OxygenOS builds. Superseded by OnePlus Recorder.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Safe to remove — replaced by OnePlus Recorder.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oplus.ocloud",
            displayName = "OPPO Cloud / OCloud",
            description = "OPPO/OnePlus's cloud storage and backup service. Syncs photos, contacts, app data, and settings to OPPO servers in China unless the global server is selected. Runs persistent background sync.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            alternatives = listOf("Google One Backup", "Nextcloud"),
            warningNote = "Remove if using Google or another backup. Review server location settings before using.",
            developer = "OPPO Electronics",
            dataCollected = listOf("Photos and videos", "Contacts", "App data", "Device settings")
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.weather",
            displayName = "ColorOS Weather",
            description = "OPPO ColorOS weather app with animated widgets and city-based forecasts. Sends location data to third-party weather data providers.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            alternatives = listOf("Geometric Weather", "Breezy Weather", "Weather.gov"),
            warningNote = "Replace with an open-source weather app.",
            developer = "OPPO Electronics",
            dataCollected = listOf("Location"),
            permissions = listOf(
                perm("android.permission.ACCESS_FINE_LOCATION", "Precise Location", "Local weather lookup", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.healthservice",
            displayName = "ColorOS Health Service",
            description = "OPPO's step counting and basic health monitoring service. Runs as a persistent background service using the accelerometer sensor.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            alternatives = listOf("Google Fit", "Samsung Health"),
            warningNote = "Remove if not using OPPO health tracking features.",
            developer = "OPPO Electronics",
            dataCollected = listOf("Step count", "Physical activity", "Sleep patterns")
        ))

        add(BloatAppInfo(
            packageName = "com.heytap.market",
            displayName = "OPPO / HeyTap App Market",
            description = "OPPO's alternative app marketplace. Sends push notification ads for sponsored apps and installs promotions. Known to run background services that periodically recommend apps.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            alternatives = listOf("Google Play Store"),
            warningNote = "Safe to remove if using Google Play Store.",
            developer = "OPPO/HeyTap",
            dataCollected = listOf("Installed apps list", "Usage patterns", "Ad targeting data")
        ))

        add(BloatAppInfo(
            packageName = "com.heytap.browser",
            displayName = "HeyTap Browser",
            description = "OPPO/HeyTap's built-in browser. Known to collect browsing history and send it to HeyTap servers. Does not support extensions. Privacy score: Poor.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            alternatives = listOf("Firefox", "Brave", "Mull"),
            warningNote = "🔴 Collects browsing history. Replace with Firefox or Brave immediately.",
            developer = "OPPO/HeyTap",
            dataCollected = listOf("Browsing history", "Search queries", "Ad targeting profile")
        ))

        add(BloatAppInfo(
            packageName = "com.realme.store",
            displayName = "Realme Store",
            description = "Realme's official e-commerce and shopping app. Pre-installed and frequently sends promotional push notifications. Provides no system functionality.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.REALME,
            canBeRestored = true,
            warningNote = "🔴 Spam notifications. Pure bloatware. Remove immediately.",
            developer = "Realme (BBK Electronics)",
            dataCollected = listOf("Device info", "Shopping behavior", "Location for delivery")
        ))

        add(BloatAppInfo(
            packageName = "com.oppo.enterprise.mdmcoreapp",
            displayName = "MDM Core (Device Management)",
            description = "Mobile Device Management core service used for enterprise device management policies. On personal devices, this is unused bloat unless your company manages your phone.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "⚠️ Remove only on personal devices not enrolled in enterprise management.",
            developer = "OPPO Electronics",
            permissions = listOf(
                perm("android.permission.BIND_DEVICE_ADMIN", "Device Admin", "Enterprise policy enforcement", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.safecenter",
            displayName = "ColorOS Security Center / Safe Center",
            description = "OPPO's device security app that handles permission monitoring, virus scanning (using a third-party engine), and privacy settings. Also manages the app lock feature.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "⚠️ Some features (like App Lock) depend on this. Remove with caution.",
            developer = "OPPO Electronics",
            dataCollected = listOf("App activity logs", "Security scan results"),
            permissions = listOf(
                perm("android.permission.PACKAGE_USAGE_STATS", "App Usage Stats", "Security monitoring", critical = true, dangerous = false),
                perm("android.permission.READ_LOGS", "System Logs", "Crash/security analysis", critical = true, dangerous = false),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.gamespace",
            displayName = "ColorOS Game Space",
            description = "OPPO's gaming mode overlay. Provides do-not-disturb during gaming, performance boosts, and a floating toolbar. Runs background services to detect when games are launched.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't use Gaming Mode features.",
            developer = "OPPO Electronics"
        ))

        add(BloatAppInfo(
            packageName = "com.oplus.games",
            displayName = "OnePlus Game Space",
            description = "OnePlus's gaming hub that aggregates all installed games and provides performance mode toggling. Similar to ColorOS Game Space.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't want the gaming sidebar.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.screenrecorder",
            displayName = "OnePlus Screen Recorder",
            description = "Built-in screen recording app for OxygenOS. Records the screen with optional front camera overlay. Android 10+ has built-in screen recording via Quick Settings, making this redundant.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Built-in Android 10+ screen recorder via Quick Settings tile is sufficient.",
            developer = "OnePlus Technology",
            permissions = listOf(
                perm("android.permission.RECORD_AUDIO", "Microphone", "Audio recording in screen capture", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.screenshot",
            displayName = "ColorOS Screenshot Tool",
            description = "Provides the screenshot annotation editor that opens after taking a screenshot on OPPO/Realme devices.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "Screenshots still work if removed; only the annotation editor disappears.",
            developer = "OPPO Electronics"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.fingerprint.animation",
            displayName = "OnePlus Fingerprint Animation #1",
            description = "One of several animation packs for the in-display or rear fingerprint scanner visual effect on OxygenOS. Multiple variants of this are installed by default — only one is needed.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "Safe to remove extra animation packs. Keep only your preferred one.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oneplus.fingerprint",
            displayName = "OnePlus Fingerprint Service",
            description = "Core service managing fingerprint enrollment, authentication API, and in-display fingerprint sensor hardware. Must be kept for biometric unlock and fingerprint-based app authentication.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.KEEP,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = false,
            warningNote = "⛔ CRITICAL: Removing disables fingerprint unlock entirely.",
            developer = "OnePlus Technology"
        ))

        add(BloatAppInfo(
            packageName = "com.oplus.statistics.rom",
            displayName = "OPPO/OPlus ROM Statistics",
            description = "Telemetry service that collects device usage statistics, crash logs, and performance data and sends them to OPPO servers. Pure analytics — no user-facing functionality.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.ONEPLUS,
            canBeRestored = true,
            warningNote = "🔴 Pure telemetry/analytics. Safe to remove for better privacy.",
            developer = "OPPO/OnePlus",
            dataCollected = listOf("Device usage stats", "Crash logs", "Performance metrics", "App activity")
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.activation",
            displayName = "ColorOS Device Activation",
            description = "One-time device activation service that registers the device with OPPO servers and sends device identifiers. Useful only at initial setup.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "Safe to remove after the device is set up.",
            developer = "OPPO Electronics",
            dataCollected = listOf("Device IMEI", "Serial number", "MAC address")
        ))

        add(BloatAppInfo(
            packageName = "com.heytap.pictorial",
            displayName = "HeyTap Wallpaper Service",
            description = "OPPO/HeyTap's dynamic wallpaper download service. Downloads animated or seasonal wallpapers from HeyTap servers.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "Safe to remove if you set wallpapers manually.",
            developer = "OPPO/HeyTap"
        ))

        add(BloatAppInfo(
            packageName = "com.coloros.assistantscreen",
            displayName = "ColorOS Assistant Screen",
            description = "OPPO's AI sidebar assistant, similar to OnePlus Shelf. Provides quick access to notes, translations, and clipboard tools from a swipe-in side panel.",
            category = AppCategory.ONEPLUS_OPPO_REALME,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.OPPO,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't use the side-swipe assistant panel.",
            developer = "OPPO Electronics"
        ))

        add(BloatAppInfo(
            packageName = "com.samsung.android.bixby.agent",
            displayName = "Bixby Voice",
            description = "Samsung's Bixby AI voice assistant. Runs persistent background listening services that drain battery. Sends voice commands and usage data to Samsung servers in the cloud. Even with the Bixby key remapped, background services continue unless the app is disabled or removed.",
            category = AppCategory.SAMSUNG_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.SAMSUNG,
            canBeRestored = true,
            alternatives = listOf("Google Assistant", "Amazon Alexa"),
            warningNote = "🔴 Background services run 24/7. Remove for battery savings and privacy.",
            developer = "Samsung Electronics",
            dataCollected = listOf("Voice recordings", "Usage patterns", "Device context"),
            permissions = listOf(
                perm("android.permission.RECORD_AUDIO", "Microphone", "Always-on wake word detection", critical = true, dangerous = true),
                perm("android.permission.ACCESS_FINE_LOCATION", "Location", "Context-aware suggestions", critical = true, dangerous = true),
                perm("android.permission.READ_CONTACTS", "Contacts", "Bixby call/message actions", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.samsung.android.bixby.wakeup",
            displayName = "Bixby Wake-up",
            description = "Constantly listens for the 'Hi Bixby' wake phrase in the background using the microphone, even when the screen is off. This is a significant battery drain and microphone privacy concern.",
            category = AppCategory.SAMSUNG_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.SAMSUNG,
            canBeRestored = true,
            warningNote = "🔴 Always-on microphone listener. Remove for battery and privacy.",
            developer = "Samsung Electronics",
            dataCollected = listOf("Audio samples (wake word detection)"),
            permissions = listOf(
                perm("android.permission.RECORD_AUDIO", "Microphone (Always On)", "Wake word 'Hi Bixby' detection", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.samsung.android.app.spage",
            displayName = "Samsung Free (Bixby Today / Discover)",
            description = "Samsung's news and content aggregator panel accessible by swiping left on the home screen. Contains third-party ads and collects browsing behavior and reading preferences to build an advertising profile.",
            category = AppCategory.SAMSUNG_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.SAMSUNG,
            canBeRestored = true,
            warningNote = "🔴 Serves ads and collects behavioral data. Disable or remove.",
            developer = "Samsung Electronics",
            dataCollected = listOf("Reading habits", "News preferences", "Ad interaction data")
        ))

        add(BloatAppInfo(
            packageName = "com.samsung.android.mobiletracker",
            displayName = "Find My Mobile",
            description = "Samsung's device tracking service. Allows remotely locating, locking, or wiping the device via Samsung account. Runs a persistent background service. Useful alternative to Google Find My Device.",
            category = AppCategory.SAMSUNG_BLOAT,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.SAMSUNG,
            canBeRestored = true,
            alternatives = listOf("Google Find My Device"),
            warningNote = "⚠️ Remove only if using Google Find My Device instead.",
            developer = "Samsung Electronics",
            dataCollected = listOf("Real-time location", "Device status"),
            permissions = listOf(
                perm("android.permission.ACCESS_FINE_LOCATION", "Precise Location", "Remote device tracking", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.samsung.android.app.omcagent",
            displayName = "OMC Agent (Carrier Customization)",
            description = "Samsung's Operator Master Configuration agent. Responsible for detecting the carrier SIM and then silently downloading and installing carrier-specific bloatware, branding, and configuration after initial setup. Often the source of unexpected pre-installed carrier apps.",
            category = AppCategory.SAMSUNG_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.SAMSUNG,
            canBeRestored = true,
            warningNote = "🔴 Installs carrier bloatware silently. Safe to remove after initial device setup.",
            developer = "Samsung Electronics",
            permissions = listOf(
                perm("android.permission.INSTALL_PACKAGES", "Install Packages", "Silently installs carrier apps", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.syncadapters.contacts",
            displayName = "Google Contacts Sync",
            description = "A background sync adapter that keeps your device contacts synchronized with your Google account. If removed, contacts won't sync to Google but local contacts remain intact.",
            category = AppCategory.GOOGLE_SERVICE,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "⚠️ Remove only if you don't sync contacts with a Google account.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.syncadapters.calendar",
            displayName = "Google Calendar Sync",
            description = "A background sync adapter that synchronizes Google Calendar events to the device. Removing stops calendar sync but doesn't affect the calendar app itself.",
            category = AppCategory.GOOGLE_SERVICE,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "⚠️ Remove only if you don't use Google Calendar sync.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.tachyon",
            displayName = "Google Meet (formerly Duo)",
            description = "Google's video calling app, originally Google Duo, merged into Google Meet. Pre-installed on most Android devices. Requires Google account. Competes with other video calling apps.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Signal", "WhatsApp", "Telegram"),
            warningNote = "Safe to remove if you use another video calling app.",
            developer = "Google LLC",
            permissions = listOf(
                perm("android.permission.CAMERA", "Camera", "Video calls", critical = true, dangerous = true),
                perm("android.permission.RECORD_AUDIO", "Microphone", "Voice in video calls", critical = true, dangerous = true),
                perm("android.permission.READ_CONTACTS", "Contacts", "Find friends on Meet", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.subscriptions.red",
            displayName = "Google One",
            description = "Google's cloud storage subscription manager. Shows your Google Drive storage usage and subscription plan. Only useful if you pay for Google One.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if not subscribed to Google One.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.photos",
            displayName = "Google Photos",
            description = "Google's cloud photo and video backup service. Automatically uploads every photo and video you take to Google servers. Uses AI to analyze and categorize your photos. Free tier limited to 15 GB shared with Drive/Gmail.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Simple Gallery Pro", "Ente Photos (E2E encrypted)", "Immich (self-hosted)"),
            warningNote = "Uploads all photos to Google servers. Consider Ente Photos for end-to-end encrypted backup.",
            developer = "Google LLC",
            dataCollected = listOf("All photos and videos", "Face recognition data", "Location from EXIF data", "Photo content analysis"),
            permissions = listOf(
                perm("android.permission.READ_MEDIA_IMAGES", "Read Photos", "Access local photos", critical = true, dangerous = true),
                perm("android.permission.ACCESS_FINE_LOCATION", "Location", "Photo geotagging", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.youtube",
            displayName = "YouTube",
            description = "Google's video streaming platform. Pre-installed on most Android devices. Delivers targeted ads based on your watch history. Collects extensive behavioral data. Official app does not support background playback without Premium.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("NewPipe (FOSS, ad-free)", "ReVanced (patched)", "Invidious (web)"),
            warningNote = "Replace with NewPipe for an ad-free, privacy-respecting YouTube experience without an account.",
            developer = "Google LLC",
            dataCollected = listOf("Watch history", "Search history", "Ad interaction", "Device fingerprint")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.youtube.music",
            displayName = "YouTube Music",
            description = "Google's music streaming service, replacing the discontinued Google Play Music. Requires a subscription for ad-free offline playback.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Spotify", "VLC (local)", "Musicolet (local)", "Poweramp (local)"),
            warningNote = "Remove if using a different music service.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.maps",
            displayName = "Google Maps",
            description = "Google's navigation and mapping service. Provides turn-by-turn navigation, business search, and Street View. Collects extensive location history and sends it to Google even when the app is closed if Location History is enabled.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("OsmAnd (offline)", "Organic Maps (offline, FOSS)", "HERE WeGo (offline)"),
            warningNote = "Consider OsmAnd or Organic Maps for offline navigation without tracking.",
            developer = "Google LLC",
            dataCollected = listOf("Real-time location", "Location history", "Search queries", "Route data"),
            permissions = listOf(
                perm("android.permission.ACCESS_FINE_LOCATION", "Precise Location", "Navigation and search", critical = true, dangerous = true),
                perm("android.permission.ACCESS_BACKGROUND_LOCATION", "Background Location", "Location history timeline", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.googlequicksearchbox",
            displayName = "Google App (Search, Feed, Assistant)",
            description = "Google's main search app providing the Google Search widget, Discover Feed (personalized news), Google Assistant integration, and voice search. Collects search history and browsing patterns to personalize the Discover Feed.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("DuckDuckGo Browser", "Firefox with DDG search"),
            warningNote = "Remove if you don't use Google Feed or Voice Search. May affect some widgets.",
            developer = "Google LLC",
            dataCollected = listOf("Search queries", "App interactions", "Voice recordings (when using Assistant)", "Discover feed engagement")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.wellbeing",
            displayName = "Digital Wellbeing",
            description = "Screen time tracking and app usage monitoring. Provides app timers, focus modes, and parental control integration. Collects detailed app-by-app usage statistics.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "⚠️ May affect parental controls and work profile restrictions. Remove with caution.",
            developer = "Google LLC",
            dataCollected = listOf("Per-app usage time", "Unlock frequency", "Notification counts"),
            permissions = listOf(
                perm("android.permission.PACKAGE_USAGE_STATS", "App Usage Stats", "Screen time tracking", critical = true, dangerous = false),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.nbu.files",
            displayName = "Files by Google",
            description = "Google's file manager with storage cleaning recommendations. Scans your storage to suggest deletions and optionally backs up files to Google Drive. Can upload file metadata to Google servers.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Solid Explorer", "MiXplorer", "Total Commander"),
            warningNote = "Replace with a privacy-focused file manager that doesn't upload metadata.",
            developer = "Google LLC",
            dataCollected = listOf("File names and metadata", "Storage usage patterns")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.keep",
            displayName = "Google Keep",
            description = "Google's note-taking app synced to your Google account. Notes are stored on Google servers and accessible via the web.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Standard Notes (E2E encrypted)", "Obsidian", "Joplin"),
            warningNote = "Consider Standard Notes for end-to-end encrypted note-taking.",
            developer = "Google LLC",
            dataCollected = listOf("Note content", "Reminders", "Labels")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.messaging",
            displayName = "Google Messages",
            description = "Google's SMS and RCS messaging app. Supports end-to-end encrypted RCS chats between Google Messages users. Google scans messages on the server for spam and may read message content for Smart Reply suggestions.",
            category = AppCategory.COMMUNICATION,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Signal (E2E encrypted)", "Telegram"),
            warningNote = "Replace with Signal for true end-to-end encrypted messaging.",
            developer = "Google LLC",
            dataCollected = listOf("SMS content", "Contact information", "Message metadata"),
            permissions = listOf(
                perm("android.permission.READ_SMS", "Read SMS", "Display messages", critical = true, dangerous = true),
                perm("android.permission.READ_CONTACTS", "Read Contacts", "Caller ID and contact names", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.gm",
            displayName = "Gmail",
            description = "Google's email client. Syncs email from Google accounts and supports other IMAP/POP3 accounts. Google scans emails for Smart Reply, Smart Compose, and ad targeting (they state this scanning is done on-device for smart features).",
            category = AppCategory.COMMUNICATION,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("FairEmail (FOSS)", "K-9 Mail (FOSS)", "ProtonMail"),
            warningNote = "Replace with FairEmail or K-9 Mail for open-source, privacy-respecting email.",
            developer = "Google LLC",
            dataCollected = listOf("Email content", "Attachment metadata", "Sender/recipient info")
        ))

        add(BloatAppInfo(
            packageName = "com.android.chrome",
            displayName = "Google Chrome",
            description = "Google's web browser. Sends your browsing history, search queries, and visited URLs to Google for sync and security features. Has a built-in ad engine that Google controls. Does not support advanced content filtering without extensions.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Firefox", "Brave (ad-blocking built-in)", "Mull (privacy hardened Firefox)"),
            warningNote = "Replace with Brave or Firefox for better privacy and built-in ad blocking.",
            developer = "Google LLC",
            dataCollected = listOf("Browsing history", "Search queries", "Cookies", "Device fingerprint")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.marvin.talkback",
            displayName = "TalkBack",
            description = "Android's built-in screen reader for users with visual impairments. Reads screen content aloud and supports Braille displays. Required for accessibility compliance.",
            category = AppCategory.UTILITY,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "⚠️ Remove only if accessibility features are absolutely not needed on this device.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.feedback",
            displayName = "Google Feedback",
            description = "A background telemetry service that collects app crash reports, ANR (App Not Responding) reports, and user-submitted feedback and sends them to Google servers.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Telemetry service. Safe to remove for privacy.",
            developer = "Google LLC",
            dataCollected = listOf("Crash logs", "System state", "User feedback")
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.partnersetup",
            displayName = "Google Partner Setup",
            description = "A one-time setup service for devices sold by Google's OEM partners. Configures partner-specific features and agreements during first boot. Has no function after initial setup.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove after device initial setup.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.onetimeinitializer",
            displayName = "Google One Time Init",
            description = "A service that runs exactly once during initial device setup to configure Google services. Has no purpose after the first boot completes.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove after initial setup.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.podcasts",
            displayName = "Google Podcasts",
            description = "Google's podcast app — officially discontinued in 2024 and shut down. Any remaining installation is a dead app that serves no function.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("AntennaPod (FOSS)", "Pocket Casts", "Spotify"),
            warningNote = "🔴 Discontinued app. Remove and replace with AntennaPod (open-source).",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.apps.docs",
            displayName = "Google Docs",
            description = "Google's online document editor integrated with Google Drive. Documents are stored on Google servers. Offline mode requires enabling per document.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("LibreOffice (offline)", "OnlyOffice"),
            warningNote = "Remove if you access Google Docs via browser, or use LibreOffice for offline editing.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.videos",
            displayName = "Google TV / Play Movies & TV",
            description = "Google's movie and TV show purchase/rental marketplace. Rarely used by most users. Requires Google account for purchases.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't buy or rent movies from Google.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.contacts",
            displayName = "Google Contacts",
            description = "Google's contact management app that syncs contacts with your Google account.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Simple Contacts Pro (local only)"),
            warningNote = "Replace with Simple Contacts Pro for local-only contact storage.",
            developer = "Google LLC",
            permissions = listOf(
                perm("android.permission.READ_CONTACTS", "Read Contacts", "Display contacts", critical = true, dangerous = true),
                perm("android.permission.WRITE_CONTACTS", "Write Contacts", "Sync and edit contacts", critical = true, dangerous = true),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.calendar",
            displayName = "Google Calendar",
            description = "Google's calendar app synced to Google account. Events are stored on Google servers.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Simple Calendar Pro (local)", "Proton Calendar"),
            warningNote = "Replace with Simple Calendar Pro if you want a local-only calendar.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.google.android.calculator",
            displayName = "Google Calculator",
            description = "Google's basic and scientific calculator app. Does not require internet access or any sensitive permissions.",
            category = AppCategory.GOOGLE_BLOAT,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("OpenCalc (FOSS)", "Calculator++"),
            warningNote = "Safe to replace with any local calculator app.",
            developer = "Google LLC"
        ))

        add(BloatAppInfo(
            packageName = "com.miui.analytics",
            displayName = "MIUI Analytics",
            description = "A background service that continuously collects and transmits detailed device telemetry including app usage statistics, screen-on events, button presses, notification interactions, and behavioral patterns to Xiaomi servers. Has been cited in multiple independent privacy analyses as one of the most aggressive telemetry apps on any Android OEM ROM.",
            category = AppCategory.XIAOMI_MIUI,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.XIAOMI,
            canBeRestored = true,
            warningNote = "🔴 PRIVACY: Runs 24/7 sending behavioral data to Xiaomi. Always remove.",
            developer = "Xiaomi Inc.",
            dataCollected = listOf("App usage statistics", "Button press events", "Notification interactions", "Screen usage patterns", "Device identifiers"),
            permissions = listOf(
                perm("android.permission.PACKAGE_USAGE_STATS", "App Usage Stats", "Collects which apps you use and for how long", critical = true, dangerous = false),
                perm("android.permission.READ_LOGS", "System Logs", "Reads crash and debug logs", critical = true, dangerous = false),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.miui.msa.global",
            displayName = "MIUI System Ads (MSA)",
            description = "Responsible for injecting advertisements into MIUI system apps such as the Weather app, File Manager, Security Center, and App Vault. Collects usage data for ad targeting. Multiple security researchers have flagged this as adware behavior.",
            category = AppCategory.XIAOMI_MIUI,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.XIAOMI,
            canBeRestored = true,
            warningNote = "🔴 ADWARE: Injects ads into system apps. Remove immediately.",
            developer = "Xiaomi Inc.",
            dataCollected = listOf("App usage", "Ad targeting profile", "Device identifiers")
        ))

        add(BloatAppInfo(
            packageName = "com.xiaomi.mipicks",
            displayName = "Mi App Store / GetApps Ads",
            description = "Sends promotional push notifications for sponsored app recommendations and can silently install promoted apps in the background on certain MIUI versions. Functions as an ad delivery mechanism disguised as an app store.",
            category = AppCategory.XIAOMI_MIUI,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.XIAOMI,
            canBeRestored = true,
            warningNote = "🔴 Delivers ads and can install apps silently. Remove for a cleaner experience.",
            developer = "Xiaomi Inc.",
            permissions = listOf(
                perm("android.permission.INSTALL_PACKAGES", "Install Packages", "Can silently install promoted apps", critical = true, dangerous = true),
                perm("android.permission.REQUEST_INSTALL_PACKAGES", "Request App Install", "Installs advertised apps", critical = true, dangerous = false),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.miui.browser",
            displayName = "Mi Browser",
            description = "Xiaomi's built-in web browser. Security researchers have documented that Mi Browser sends browsing URLs, search queries, and device identifiers to Xiaomi servers without user consent, even when using incognito mode.",
            category = AppCategory.XIAOMI_MIUI,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.XIAOMI,
            canBeRestored = true,
            alternatives = listOf("Firefox", "Brave", "DuckDuckGo Browser"),
            warningNote = "🔴 Known to track browsing history including incognito. Replace immediately.",
            developer = "Xiaomi Inc.",
            dataCollected = listOf("Browsing history (including incognito)", "Search queries", "Bookmarks", "Form data")
        ))

        add(BloatAppInfo(
            packageName = "com.miui.cleanmaster",
            displayName = "Mi Clean Master / Security",
            description = "Xiaomi's RAM and storage cleaner and security scanner. Notorious for aggressively killing background apps which disrupts notifications and causes apps to restart from scratch. The 'cleaning' is often counterproductive — Android manages RAM better on its own.",
            category = AppCategory.XIAOMI_MIUI,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.XIAOMI,
            canBeRestored = true,
            warningNote = "🔴 Aggressively kills apps, breaking notifications. Modern Android doesn't need RAM cleaners.",
            developer = "Xiaomi Inc.",
            permissions = listOf(
                perm("android.permission.KILL_BACKGROUND_PROCESSES", "Kill Background Apps", "Aggressively kills running apps", critical = true, dangerous = false),
            )
        ))

        add(BloatAppInfo(
            packageName = "com.android.browser",
            displayName = "AOSP Browser (Legacy)",
            description = "The original legacy Android browser from before Chrome became the default. No longer maintained and missing modern security features. Should not be used as a primary browser.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("Firefox", "Brave"),
            warningNote = "Obsolete. Replace with a modern, maintained browser.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.email",
            displayName = "AOSP Email",
            description = "The legacy Android IMAP/POP3 email client. Rarely updated and lacks modern features.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            alternatives = listOf("FairEmail (FOSS)", "K-9 Mail (FOSS)"),
            warningNote = "Replace with FairEmail for a more feature-rich and privacy-respecting client.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.musicfx",
            displayName = "Music FX / Equalizer",
            description = "An AOSP audio effects module and equalizer used by some music players. Only useful if your music player routes audio through the system equalizer.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if your music player has its own built-in equalizer.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.wallpaper.livepicker",
            displayName = "Live Wallpaper Picker",
            description = "The system tool for browsing and setting animated live wallpapers. Redundant if you only use static wallpapers.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if you use only static wallpapers.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.dreams.basic",
            displayName = "Daydream / Screensaver",
            description = "Android's interactive screensaver feature shown while the device is charging and connected to a dock. Shows photos, clock, or other content on the screen.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't use the screensaver while charging.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.android.quicksearchbox",
            displayName = "Quick Search Box (AOSP)",
            description = "The AOSP search widget that predates the Google Search app. Largely superseded on modern Android with Google Search.",
            category = AppCategory.STOCK_ANDROID,
            safetyTag = SafetyTag.REPLACEABLE,
            deviceType = DeviceType.ALL,
            canBeRestored = true,
            warningNote = "Safe to remove if using Google Search or another search provider.",
            developer = "Android Open Source Project"
        ))

        add(BloatAppInfo(
            packageName = "com.att.android.attsmartwifi",
            displayName = "AT&T Smart Wi-Fi",
            description = "AT&T's Wi-Fi management app that automatically connects to AT&T Wi-Fi hotspots. Pre-installed carrier bloatware.",
            category = AppCategory.CARRIER_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.CARRIER,
            canBeRestored = true,
            warningNote = "🔴 Carrier bloatware. Safe to remove.",
            developer = "AT&T"
        ))

        add(BloatAppInfo(
            packageName = "com.att.tv",
            displayName = "AT&T TV",
            description = "AT&T's streaming TV app bundled on carrier devices. Only useful if subscribed to AT&T TV service.",
            category = AppCategory.CARRIER_BLOAT,
            safetyTag = SafetyTag.RECOMMENDED_REMOVE,
            deviceType = DeviceType.CARRIER,
            canBeRestored = true,
            warningNote = "🔴 Carrier bloatware. Remove if not subscribed.",
            developer = "AT&T"
        ))

        add(BloatAppInfo(
            packageName = "com.vzw.apnlib",
            displayName = "Verizon APN Library",
            description = "Verizon's carrier configuration library for APN (Access Point Name) settings. May be required for proper data connectivity on Verizon networks.",
            category = AppCategory.CARRIER_BLOAT,
            safetyTag = SafetyTag.CAUTION,
            deviceType = DeviceType.CARRIER,
            canBeRestored = true,
            warningNote = "⚠️ Verizon devices only. May affect mobile data. Remove with caution.",
            developer = "Verizon"
        ))

        add(BloatAppInfo(
            packageName = "com.t_mobile.tuesdays",
            displayName = "T-Mobile Tuesdays",
            description = "T-Mobile's weekly deals and offers app providing discounts from partner brands.",
            category = AppCategory.CARRIER_BLOAT,
            safetyTag = SafetyTag.SAFE_TO_REMOVE,
            deviceType = DeviceType.CARRIER,
            canBeRestored = true,
            warningNote = "Safe to remove if you don't use T-Mobile Tuesday deals.",
            developer = "T-Mobile"
        ))

    }.associateBy { it.packageName }

    fun getBloatInfo(packageName: String): BloatAppInfo? {
        return database[packageName]
    }

    fun getAllBloatPackages(): List<BloatAppInfo> {
        return database.values.toList()
    }

    fun getByCategory(category: AppCategory): List<BloatAppInfo> {
        return database.values.filter { it.category == category }
    }

    fun getByDeviceType(deviceType: DeviceType): List<BloatAppInfo> {
        return database.values.filter { it.deviceType == deviceType || it.deviceType == DeviceType.ALL }
    }

    fun getBloatwareOnly(): List<BloatAppInfo> {
        return database.values.filter {
            it.safetyTag == SafetyTag.RECOMMENDED_REMOVE || it.safetyTag == SafetyTag.SAFE_TO_REMOVE
        }
    }
}