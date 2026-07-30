package com.hiaashuu.debloatzzz.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hiaashuu.debloatzzz.model.Screen
import com.hiaashuu.debloatzzz.ui.screens.AppDetailScreen
import com.hiaashuu.debloatzzz.ui.screens.AppListScreen
import com.hiaashuu.debloatzzz.ui.screens.BackupRestoreScreen
import com.hiaashuu.debloatzzz.ui.screens.BatGeneratorScreen
import com.hiaashuu.debloatzzz.ui.screens.HomeScreen
import com.hiaashuu.debloatzzz.ui.screens.HowToUseScreen
import com.hiaashuu.debloatzzz.viewmodel.AppViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAppList = {
                    navController.navigate(Screen.AppList.route)
                },
                onNavigateToBackup = {
                    navController.navigate(Screen.BackupRestore.route)
                },
                onNavigateToGuide = {
                    navController.navigate(Screen.HowToUse.route)
                }
            )
        }

        composable(route = Screen.AppList.route) {
            AppListScreen(
                viewModel = viewModel,
                onAppClick = { packageName ->
                    navController.navigate(Screen.appDetailRoute(packageName))
                }
            )
        }

        composable(
            route = Screen.AppDetail.route,
            arguments = listOf(
                navArgument("packageName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedPkg = backStackEntry.arguments?.getString("packageName") ?: ""
            val packageName = Uri.decode(encodedPkg)
            AppDetailScreen(
                packageName = packageName,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.BackupRestore.route) {
            BackupRestoreScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.BatGenerator.route) {
            BatGeneratorScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.HowToUse.route) {
            HowToUseScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}