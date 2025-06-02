package com.laennecai.poc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.laennecai.poc.features.bmi.presentation.BmiCalculatorScreen
import com.laennecai.poc.features.post.presentation.PostDetailScreen
import com.laennecai.poc.features.post.presentation.PostListScreen
import com.laennecai.poc.features.post.presentation.PostViewModel
import com.laennecai.poc.features.todo.presentation.TodoListScreen
import com.laennecai.poc.features.todo.presentation.TodoViewModel
import com.laennecai.poc.navigation.NestedRoutes
import com.laennecai.poc.ui.theme.PocTheme

// Main navigation routes for bottom bar
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Posts : Screen("posts_root", "Posts", Icons.Filled.Email)
    data object BmiCalculator : Screen("bmi_calculator_root", "BMI Cal", Icons.Filled.Face)
    data object TodoList : Screen("todo_list_root", "Todo", Icons.Filled.List)
}

val bottomNavItems = listOf(
    Screen.Posts,
    Screen.BmiCalculator,
    Screen.TodoList
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val context = LocalContext.current
    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName

    Scaffold(
        topBar = {
            when (currentRoute) {
                Screen.Posts.route -> {
                    TopAppBar(
                        title = { Text("Posts: APP VERSION: $versionName") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
                Screen.BmiCalculator.route -> {
                    TopAppBar(
                        title = { Text("BMI Calculator") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
                Screen.TodoList.route -> {
                    TopAppBar(
                        title = { Text("Todo List") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Posts.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Posts.route) {
                val viewModel: PostViewModel = viewModel()
                PostListScreen(
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier
                )
            }
            composable(Screen.BmiCalculator.route) {
                BmiCalculatorScreen(modifier = Modifier)
            }
            composable(Screen.TodoList.route) {
                val viewModel: TodoViewModel = viewModel()
                TodoListScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                )
            }
            composable(
                route = NestedRoutes.POST_DETAIL,
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                val viewModel: PostViewModel = viewModel()
                val post = viewModel.postsResult?.getOrNull()?.find { it.id == postId }
                PostDetailScreen(
                    post = post!!,
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PocTheme {
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}