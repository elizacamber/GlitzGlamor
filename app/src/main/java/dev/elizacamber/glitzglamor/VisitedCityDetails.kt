package dev.elizacamber.glitzglamor

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
                        Column(Modifier.padding(16.dp)) {
                            Text(text = "Times visited: ${city.visits.size}")
                            Text(text = "Number of total days: 170")
                            Text(text = "Last day visited: 27/05/2022")
                            //Text(text = "Trips: 1. 10/09/2021 - 19/09/2021 2. 04/01/2022 - 27/05/2022")
                        }
                    }
                }
            }
        }
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