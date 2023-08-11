package com.mshdabiola.series.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource("drawables/logo.png"),
            modifier = Modifier.size(100.dp),
            contentDescription = "Logo"
        )
    }

}