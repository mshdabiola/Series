/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.designsystem.component.SeriesButton
import com.mshdabiola.designsystem.icon.SeriesIcons
import com.mshdabiola.testing.util.captureMultiTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class ButtonScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun niaButton_multipleThemes() {
        composeTestRule.captureMultiTheme("Button") { description ->
            Surface {
                SeriesButton(onClick = {}, text = { Text("$description Button") })
            }
        }
    }

//    @Test
//    fun niaOutlineButton_multipleThemes() {
//        composeTestRule.captureMultiTheme("Button", "OutlineButton") { description ->
//            Surface {
//                NiaOutlinedButton(onClick = {}, text = { Text("$description OutlineButton") })
//            }
//        }
//    }

    @Test
    fun niaButton_leadingIcon_multipleThemes() {
        composeTestRule.captureMultiTheme(
            name = "Button",
            overrideFileName = "ButtonLeadingIcon",
            shouldCompareAndroidTheme = false,
        ) { description ->
            Surface {
                SeriesButton(
                    onClick = {},
                    text = { Text("$description Icon Button") },
                    leadingIcon = {
                        Icon(
                            imageVector = SeriesIcons.Add,
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}
