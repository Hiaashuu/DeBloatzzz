package com.hiaashuu.debloatzzz

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hiaashuu.debloatzzz.model.Screen
import com.hiaashuu.debloatzzz.navigation.AppNavGraph
import com.hiaashuu.debloatzzz.shizuku.ShizukuHelper
import com.hiaashuu.debloatzzz.ui.screens.DisclaimerScreen
import com.hiaashuu.debloatzzz.ui.theme.ComposeEmptyActivityTheme
import com.hiaashuu.debloatzzz.ui.theme.DisclaimerPrefs
import com.hiaashuu.debloatzzz.viewmodel.AppViewModel
import rikka.shizuku.Shizuku

class MainActivity : ComponentActivity() {

    private val requestPermissionResultListener =
        Shizuku.OnRequestPermissionResultListener { requestCode, grantResult ->
            ShizukuHelper.onPermissionResult(requestCode, grantResult)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ShizukuHelper.init()
        Shizuku.addRequestPermissionResultListener(requestPermissionResultListener)

        setContent {
            ComposeEmptyActivityTheme {
                val context = LocalContext.current
                var disclaimerAccepted by remember {
                    mutableStateOf(DisclaimerPrefs.hasAccepted(context))
                }

                if (!disclaimerAccepted) {
                    DisclaimerScreen(
                        onAccepted = { disclaimerAccepted = true }
                    )
                } else {
                    PressBackToExitApp {
                        DeBloatzzApp()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ShizukuHelper.destroy()
        try {
            Shizuku.removeRequestPermissionResultListener(requestPermissionResultListener)
        } catch (e: Exception) { }
    }
}

@Composable
fun DeBloatzzApp() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.AppList.route,
        Screen.BackupRestore.route,
        Screen.BatGenerator.route,
        Screen.HowToUse.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                DeBloatBottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { _ ->
        AppNavGraph(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun DeBloatBottomNav(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(tonalElevation = 0.dp) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { onNavigate(Screen.Home.route) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.AppList.route,
            onClick = { onNavigate(Screen.AppList.route) },
            icon = { Icon(Icons.Outlined.Apps, contentDescription = "Apps") },
            label = { Text("Apps") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.BackupRestore.route,
            onClick = { onNavigate(Screen.BackupRestore.route) },
            icon = { Icon(Icons.Outlined.CloudUpload, contentDescription = "Backup") },
            label = { Text("Backup") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.BatGenerator.route,
            onClick = { onNavigate(Screen.BatGenerator.route) },
            icon = { Icon(Icons.Filled.Terminal, contentDescription = "Script") },
            label = { Text("Scripts") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.HowToUse.route,
            onClick = { onNavigate(Screen.HowToUse.route) },
            icon = { Icon(Icons.Filled.Info, contentDescription = "Guide") },
            label = { Text("Guide") }
        )
    }
}

@Composable
fun PressBackToExitApp(content: @Composable () -> Unit) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            (context as? ComponentActivity)?.finish()
        } else {
            backPressedTime = currentTime
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    content()
}