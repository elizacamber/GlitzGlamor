package dev.elizacamber.glitzglamor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesItem(city: City) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlagIcon(res = getFlagForCountry(city.country))
        Spacer(Modifier.width(8.dp))
        Column {
            Text(text = city.name)
            TimesVisitedText(visits = city.timesVisited)
        }
    }
}

@Composable
fun FlagIcon(res: Int?) {
    Image(
        painter = painterResource(
            id = res ?: R.drawable.ic_launcher_background
        ),
        contentDescription = "flag",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}

@Composable
fun TimesVisitedText(visits: Int) {
    Text(
        text = stringResource(id = R.string.title_times_visited, visits),
        style= MaterialTheme.typography.bodySmall)
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    GlitzGlamorTheme {
        VisitedCitiesItem(City("Chicago", "United States of America", 1))
    }
}