package dev.elizacamber.glitzglamor

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

enum class TitleAnimationState {
    Regular,
    Animated
}

@Composable
fun Title() {
    val isAnimated = remember { mutableStateOf(false) }

    val transition = updateTransition(isAnimated, label = "title_animation")

    val scale = transition.animateFloat(label = "title_scale") {
        if (it.value) 1.5f else 1f
    }
    val bgColor1 = MaterialTheme.colorScheme.primary
    val bgColor2 = MaterialTheme.colorScheme.secondary
    val bgColor by transition.animateColor(label = "") { state ->
        if (state.value) bgColor2 else bgColor1
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(bgColor)
            .clickable {
                isAnimated.value = !isAnimated.value
            },
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