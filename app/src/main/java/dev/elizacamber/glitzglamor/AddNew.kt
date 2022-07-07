package dev.elizacamber.glitzglamor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AddNew(navController: NavController) {
    Text(text = "Screen for adding new cities in the list")
}

@Composable
@Preview
fun AddNewPreview() {
    AddNew(navController = rememberNavController())
}