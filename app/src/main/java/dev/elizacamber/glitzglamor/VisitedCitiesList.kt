package dev.elizacamber.glitzglamor

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesList(list: List<City>, navController: NavHostController) {
    Column {
        Title()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list, itemContent = { city ->
                VisitedCitiesItem(city = city, onClick = {
                    navController.navigate("details/${city.name}")
                })
            })
        }
    }
}

@Composable
fun Title() {
    val scaled = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (scaled.value) 1.5f else 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { scaled.value = !scaled.value },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.scale(scale.value)
        )
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
            ),
            rememberNavController()
        )
    }
}