package com.laennecai.poc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.laennecai.poc.features.bmi.presentation.BmiCalculatorScreen
import com.laennecai.poc.features.post.presentation.PostDetailScaffold
import com.laennecai.poc.features.post.presentation.PostListScaffold
import com.laennecai.poc.features.post.presentation.PostViewModel
import com.laennecai.poc.ui.theme.PocTheme

// Main navigation routes for bottom bar
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Posts : Screen("posts_root", "Posts", Icons.Filled.Email)
    data object BmiCalculator : Screen("bmi_calculator_root", "BMI Calc", Icons.Filled.Face)
}

val bottomNavItems = listOf(
    Screen.Posts,
    Screen.BmiCalculator
)

// Nested graph routes
object NestedRoutes {
    const val POST_LIST = "postList"
    const val POST_DETAIL = "postDetail/{postId}"
    fun postDetail(postId: Int) = "postDetail/$postId"

    const val BMI_CALCULATOR = "bmiCalculator"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
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
                    ) {
                        navigation(
                            startDestination = NestedRoutes.POST_LIST,
                            route = Screen.Posts.route
                        ) {
                            composable(NestedRoutes.POST_LIST) {
                                val postViewModel: PostViewModel = viewModel()
                                PostListScaffold(
                                    viewModel = postViewModel,
                                    navController = navController,
                                )
                            }
                            composable(
                                route = NestedRoutes.POST_DETAIL,
                                arguments = listOf(navArgument("postId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val postId = backStackEntry.arguments?.getInt("postId")
                                val postViewModel: PostViewModel = viewModel()
                                val post = postViewModel.postsResult?.getOrNull()?.find { it.id == postId }
                                PostDetailScaffold(post = post, navController = navController)
                            }
                        }
                        navigation(
                            startDestination = NestedRoutes.BMI_CALCULATOR,
                            route = Screen.BmiCalculator.route
                        ) {
                            composable(NestedRoutes.BMI_CALCULATOR) {
                                BmiCalculatorScaffold()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculatorScaffold() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BMI Calculator") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        BmiCalculatorScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PocTheme {
        BmiCalculatorScaffold()
    }
}