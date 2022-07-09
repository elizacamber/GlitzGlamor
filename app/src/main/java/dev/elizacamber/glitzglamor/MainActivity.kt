package dev.elizacamber.glitzglamor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.elizacamber.glitzglamor.database.dummyCityList
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme


@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlitzGlamorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Scaffold(
                        floatingActionButton = {
                            if (navBackStackEntry?.destination?.route == "list") {
                                FloatingActionButton(onClick = { navController.navigate("new") }) {
                                    Icon(Icons.Default.Add, "Add new city to the list")
                                }
                            }
                        }
                    ) {
                        AnimatedNavHost(navController = navController, startDestination = "list") {
                            composable("list") { VisitedCitiesList(dummyCityList, navController) }
                            composable(
                                "details/{country}/{cityName}",
                                arguments = listOf(navArgument("country") {
                                    type = NavType.StringType
                                }, navArgument("cityName") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                VisitedCityDetails(
                                    navController,
                                    backStackEntry.arguments?.getString("country"),
                                    backStackEntry.arguments?.getString("cityName")
                                )
                            }
                            composable(
                                "new",
                                enterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Up,
                                        tween(700)
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Down,
                                        tween(700)
                                    )
                                }
                            ) { AddNew(navController) }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GlitzGlamorTheme {
        VisitedCitiesList(dummyCityList, rememberNavController())
    }
}