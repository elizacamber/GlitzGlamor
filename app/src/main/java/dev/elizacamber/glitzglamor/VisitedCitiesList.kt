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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
    val animState = remember { mutableStateOf(false) }
    val transitionData = updateTitleTransitionData(animState)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(transitionData.color)
            .clickable { animState.value = !animState.value },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.scale(transitionData.scale)
        )
    }
}

private class TitleTransitionData(color: State<Color>, scale: State<Float>) {
    val color by color
    val scale by scale
}

// Create a Transition and return its animation values.
@Composable
private fun updateTitleTransitionData(isAnimated: MutableState<Boolean>): TitleTransitionData {
    val transition = updateTransition(isAnimated, label = "title_animation")

    val bgColor1 = MaterialTheme.colorScheme.primary
    val bgColor2 = MaterialTheme.colorScheme.secondary
    val bgColor = transition.animateColor(label = "") {
        if (it.value) bgColor2 else bgColor1
    }
    val scale = transition.animateFloat(label = "title_scale") {
        if (it.value) 1.5f else 1f
    }
    return remember(transition) { TitleTransitionData(bgColor, scale) }
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