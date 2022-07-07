package dev.elizacamber.glitzglamor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun VisitedCityDetails(navController: NavHostController, country: String?, city: String?) {
    requireNotNull(country)
    requireNotNull(city)

    Column(Modifier.fillMaxSize()) {
        DetailsBanner(country = country, city = city)
    }
}

@Composable
fun DetailsBanner(country: String, city: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
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
        Text(
            text = city,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VisitedCityDetailsPreview() {
    VisitedCityDetails(rememberNavController(), "United States of America", "Chicago")
}