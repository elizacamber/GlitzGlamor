package dev.elizacamber.glitzglamor

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun VisitedCityDetails(navController: NavHostController, country: String?, city: String?) {
    requireNotNull(country)
    requireNotNull(city)

    Column(Modifier.fillMaxSize()) {
        DetailsBanner(country = country, city = city)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DetailsBanner(country: String, city: String) {
    val visible = remember { mutableStateOf(false) }
    val density = LocalDensity.current

    LaunchedEffect(visible) {
        delay(500)
        visible.value = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clickable { visible.value = !visible.value },
        contentAlignment = Alignment.Center
    ) {
        if (getFlagForCountry(country) != null) {
            Image(
                painter = painterResource(id = getFlagForCountry(country)!!),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                alpha = 0.1f
            )
        }
        AnimatedVisibility(
            visible = visible.value,
            enter = slideInHorizontally {
                // Slide in from 40 dp from the top.
                with(density) { -100.dp.roundToPx() }
            } + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutHorizontally {
                with(density) { +200.dp.roundToPx() }
            } + fadeOut()
        ) {
            Text(
                text = city,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitedCityDetailsPreview() {
    VisitedCityDetails(rememberNavController(), "United States of America", "Chicago")
}