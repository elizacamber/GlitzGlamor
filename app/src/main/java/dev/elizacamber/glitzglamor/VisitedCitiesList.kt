package dev.elizacamber.glitzglamor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesList(list: List<City>){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list, itemContent = { city ->
            VisitedCitiesItem(city = city)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun VisitedCitiesListPreview() {
    GlitzGlamorTheme {
        VisitedCitiesList(
            listOf(
                City("Chicago", "United States of America", 1),
                City("Madrid", "Spain", 4)
            ))
    }
}