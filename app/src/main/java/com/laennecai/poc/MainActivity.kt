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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.laennecai.poc.features.todo.presentation.TodoListScaffold
import com.laennecai.poc.features.todo.presentation.TodoViewModel
import com.laennecai.poc.ui.theme.PocTheme

// Main navigation routes for bottom bar
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Posts : Screen("posts_root", "Posts", Icons.Filled.Email)
    data object BmiCalculator : Screen("bmi_calculator_root", "BMI Calc", Icons.Filled.Face)
    data object TodoList : Screen("todo_list_root", "Todo", Icons.Filled.List)
}

val bottomNavItems = listOf(
    Screen.Posts,
    Screen.BmiCalculator,
    Screen.TodoList
)

// Nested graph routes
object NestedRoutes {
    const val POST_LIST = "postList"
    const val POST_DETAIL = "postDetail/{postId}"
    fun postDetail(postId: Int) = "postDetail/$postId"

    const val BMI_CALCULATOR = "bmiCalculator"
    const val TODO_LIST = "todoList"
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
                ) { _ ->
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
                                val post =
                                    postViewModel.postsResult?.getOrNull()?.find { it.id == postId }
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
                        navigation(
                            startDestination = NestedRoutes.TODO_LIST,
                            route = Screen.TodoList.route
                        ) {
                            composable(NestedRoutes.TODO_LIST) {
                                val todoViewModel: TodoViewModel = viewModel()
                                TodoListScaffold(viewModel = todoViewModel)
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
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