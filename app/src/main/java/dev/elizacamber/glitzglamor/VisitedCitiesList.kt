package dev.elizacamber.glitzglamor

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun VisitedCitiesList(
    navController: NavHostController,
    viewModel: VisitedCitiesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingListScreen()
    } else {
        when (uiState) {
            is CitiesUiState.CitiesEmpty -> EmptyCitiesScreen()
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

@Composable
fun LoadingListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Title(stringResource(id = R.string.app_title))
        LazyColumn() {
            repeat(5) {
                item {
                    LoadingVisitedCitiesItem()
                }
            }
        }
    }

}

@Composable
fun EmptyCitiesScreen() {
    var triggerAnimation by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("empty_list_lottie.json"))
    val animationState = animateLottieCompositionAsState(
        composition = composition,
        isPlaying = triggerAnimation
    )

    DisposableEffect(triggerAnimation) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = {
            triggerAnimation = !triggerAnimation
        }
        handler.postDelayed(runnable, 4000)
        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    Column(Modifier.fillMaxSize()) {
        Title(stringResource(id = R.string.app_title))
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Your visited cities will appear here",
            Modifier
                .fillMaxWidth()
                .padding(24.dp), textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.alpha(0.2f)) {
            LottieAnimation(composition = composition, { animationState.progress })
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