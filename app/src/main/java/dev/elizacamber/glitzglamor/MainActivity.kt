@file:OptIn(ExperimentalMaterial3Api::class)

package dev.elizacamber.glitzglamor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.elizacamber.glitzglamor.ui.theme.AddNew
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlitzGlamorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    Scaffold(
                        floatingActionButton = {
                            if(navBackStackEntry?.destination?.route == "list") {
                                FloatingActionButton(onClick = { navController.navigate("new") }) {
                                    Icon(Icons.Default.Add, "Add new city to the list")
                                }
                            }
                        }
                    ) {
                        NavHost(navController = navController, startDestination = "list") {
                            composable("list") { VisitedCitiesList(dummyCityList, navController) }
                            composable("details/{cityName}",
                                arguments = listOf(navArgument("cityName") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                VisitedCityDetails(navController, backStackEntry.arguments?.getString("cityName"))
                            }
                            composable("new") { AddNew(navController) }
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