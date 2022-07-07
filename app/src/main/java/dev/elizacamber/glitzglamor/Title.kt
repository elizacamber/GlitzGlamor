package dev.elizacamber.glitzglamor

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun Title(title: String) {
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
            text = title,
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
    val bgColor = transition.animateColor(label = "title_color") {
        if (it.value) bgColor2 else bgColor1
    }
    val scale = transition.animateFloat(label = "title_scale") {
        if (it.value) 1.5f else 1f
    }
    return remember(transition) { TitleTransitionData(bgColor, scale) }
}

@Composable
@Preview
fun TitlePreview() {
    Title(stringResource(id = R.string.app_title))
}
