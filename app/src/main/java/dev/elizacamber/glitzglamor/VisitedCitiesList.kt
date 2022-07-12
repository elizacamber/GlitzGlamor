package dev.elizacamber.glitzglamor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesList(
    navController: NavHostController,
    viewModel: VisitedCitiesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        when (uiState) {
            is CitiesUiState.CitiesError -> Text(text = stringResource(id = R.string.error_generic))
            is CitiesUiState.CitiesSuccess -> {
                Column {
                    Title(stringResource(id = R.string.app_title))
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(items = uiState.cities, itemContent = { city ->
                            VisitedCitiesItem(city = city, onClick = {
                                navController.navigate("details/${city.cityId}")
                            })
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitedCitiesListPreview() {
    GlitzGlamorTheme {
        VisitedCitiesList(rememberNavController())
    }
}