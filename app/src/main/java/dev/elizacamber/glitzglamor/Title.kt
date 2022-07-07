package dev.elizacamber.glitzglamor

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Title() {
    val transitionData = updateTitleTransitionData()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(transitionData.color)
            .clickable { },
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
private fun updateTitleTransitionData(): TitleTransitionData {
    val transition = rememberInfiniteTransition()

    val bgColor1 = MaterialTheme.colorScheme.primary
    val bgColor2 = MaterialTheme.colorScheme.secondary
    val bgColor = transition.animateColor(
        initialValue = bgColor1,
        targetValue = bgColor2,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    val scale = transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    return remember(transition) { TitleTransitionData(bgColor, scale) }
}

@Composable
@Preview
fun TitlePreview() {
    Title()
}
