package dev.elizacamber.glitzglamor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun VisitedCityDetails(navController: NavHostController, city: String?) {
    Text("Details for $city")
}

@Preview(showBackground = true)
@Composable
fun VisitedCityDetailsPreview() {
    VisitedCityDetails(rememberNavController(), "Chicago")
}