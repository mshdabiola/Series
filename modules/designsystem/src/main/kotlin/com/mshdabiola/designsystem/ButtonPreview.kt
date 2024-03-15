package com.mshdabiola.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesBackground
import com.mshdabiola.designsystem.component.SeriesButton
import com.mshdabiola.designsystem.icon.SeriesIcons
import com.mshdabiola.designsystem.theme.SeriesTheme

@ThemePreviews
@Composable
fun ButtonPreview() {
    SeriesTheme {
        SeriesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonPreview2() {
    SeriesTheme {
        SeriesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesButton(onClick = {}, text = { Text("Test button") })
        }
    }
}

@ThemePreviews
@Composable
fun ButtonLeadingIconPreview() {
    SeriesTheme {
        SeriesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            SeriesButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = { Icon(imageVector = SeriesIcons.Add, contentDescription = null) },
            )
        }
    }
}
