package dev.elizacamber.glitzglamor

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.elizacamber.glitzglamor.ui.theme.GlitzGlamorTheme

@Composable
fun LoadingVisitedCitiesItem() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(140.dp, 48.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingItemPreview() {
    GlitzGlamorTheme {
        LoadingVisitedCitiesItem()
    }
}