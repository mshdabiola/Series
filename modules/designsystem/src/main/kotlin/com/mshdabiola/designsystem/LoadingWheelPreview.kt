package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SeriesLoadingWheel
import com.mshdabiola.designsystem.component.SeriesOverlayLoadingWheel
import com.mshdabiola.designsystem.theme.SeriesTheme

@ThemePreviews
@Composable
fun NiaLoadingWheelPreview() {
    SeriesTheme {
        Surface {
            SeriesLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

@ThemePreviews
@Composable
fun NiaOverlayLoadingWheelPreview() {
    SeriesTheme {
        Surface {
            SeriesOverlayLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}
