package dev.elizacamber.glitzglamor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.elizacamber.glitzglamor.database.City
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesList(
    list: List<City>,
    navController: NavHostController,
    viewModel: VisitedCitiesViewModel = viewModel()
) {
    Column {
        Title(stringResource(id = R.string.app_title))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list, itemContent = { city ->
                VisitedCitiesItem(city = city, onClick = {
                    navController.navigate("details/${city.country}/${city.name}")
                })
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitedCitiesListPreview() {
    GlitzGlamorTheme {
        VisitedCitiesList(
            listOf(
                City(0, "Chicago", "United States of America", 1),
                City(1, "Madrid", "Spain", 4)
            ),
            rememberNavController()
        )
    }
}