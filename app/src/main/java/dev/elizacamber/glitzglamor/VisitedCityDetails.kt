package dev.elizacamber.glitzglamor

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.elizacamber.glitzglamor.data.*
import kotlinx.coroutines.delay

enum class DetailsFABState {
    Edit,
    Done
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitedCityDetails(
    navController: NavHostController,
    cityId: Long?,
    viewModel: CityDetailsViewModel = viewModel()
) {
    requireNotNull(cityId)
    LaunchedEffect(Unit) {
        viewModel.cityFlow(cityId)
    }

    var fabState by remember { mutableStateOf(DetailsFABState.Edit) }
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        when (uiState) {
            is CityDetailsUiState.CityError -> TODO()
            is CityDetailsUiState.CitySuccess -> {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            fabState =
                                if (fabState == DetailsFABState.Edit) DetailsFABState.Done else DetailsFABState.Edit
                        }) {
                            Icon(
                                if (fabState == DetailsFABState.Done) Icons.Default.Edit else Icons.Default.Done,
                                "Edit city details"
                            )
                        }
                    }
                ) {
                    val city = uiState.city!!
                    Column(Modifier.fillMaxSize()) {
                        DetailsBanner(flag = city.city.flagRes, city = city.city.name)
                        Spacer(modifier = Modifier.height(16.dp))
                        DetailsCard(city)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailsCard(city: CityWithVisitDetails) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(targetState = expanded, label = "card")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { expanded = !expanded },
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column() {
            transition.AnimatedContent(transitionSpec = fadeThrough()) { targetExpanded ->
                CardContent(city = city, expanded = targetExpanded)
            }
        }
    }
}

@Composable
fun CardContent(city: CityWithVisitDetails, expanded: Boolean) {
    var expandedYear by remember { mutableStateOf<String?>(null) }
    if (!expanded) {
        Column(Modifier.padding(16.dp)) {
            CountryInfo(city.city)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Times visited: ${city.visits.size}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Number of total days: ${city.visits.getTotalDays()}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Last day visited: ${city.visits.getLastVisit()}")
            //Text(text = "Trips: 1. 10/09/2021 - 19/09/2021 2. 04/01/2022 - 27/05/2022")
        }
    } else {
        Column(Modifier.padding(16.dp)) {
            Text(text = "Times visited: ${city.visits.size}")
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.animateContentSize()
            ) {
                val groupedVisits = city.visits.groupByYear()
                groupedVisits.forEach { year ->
                    val yearStr = year.firstOrNull()?.getYear()
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                                expandedYear = if (expandedYear == yearStr) null else yearStr
                            },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = yearStr ?: "(-)",
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(vertical = 14.dp, horizontal = 16.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    if (expandedYear == yearStr) {
                        year.forEach {
                            Text(
                                text = "${it.start_date.epochToReadableDate()} - ${it.end_date.epochToReadableDate()}",
                                modifier = Modifier
                                    .height(32.dp)
                                    .padding(start = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }

        }
    }
}

@Composable
fun CountryInfo(city: City) {
    Text(text = "Country: ${city.country}")
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Flag: ")
        Image(
            painter = painterResource(id = city.flagRes),
            contentDescription = "${city.country}'s flag",
            modifier = Modifier
                .height(32.dp)
                .width(42.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading...")
}

@Composable
fun DetailsBanner(flag: Int, city: String) {
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
        Image(
            painter = painterResource(id = flag),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            contentDescription = "",
            alpha = 0.1f
        )
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
fun DetailsBannerPreview() {
    DetailsBanner(R.drawable.us, "Chicago")
}