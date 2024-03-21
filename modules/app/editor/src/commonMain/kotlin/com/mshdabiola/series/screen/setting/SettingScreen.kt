package com.mshdabiola.series.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.DropdownMenu
import kotlinx.collections.immutable.toImmutableList

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingScreen(onBack: () -> Unit) {
    val viewModel: SettingViewModel = KoinCommonViewModel()
    val settingState = viewModel.uiState.collectAsStateWithLifecycleCommon()

    SettingScreen(
        settingState = settingState.value,
        onBack = onBack,
        setThemeBrand = viewModel::setThemeBrand,
        setContrast = viewModel::setThemeContrast,
        setDarkThemeConfig = viewModel::setDarkThemeConfig,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SettingScreen(
    settingState: SettingState,
    onBack: () -> Unit = {},
    setThemeBrand: (ThemeBrand) -> Unit = {},
    setDarkThemeConfig: (DarkThemeConfig) -> Unit = {},
    setContrast: (Contrast) -> Unit = {},

) {
    Scaffold(
        modifier = Modifier, // .semantics { this.testTagsAsResourceId = true },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(text = "Setting")
                },
            )
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Theme")
                DropdownMenu(
                    currentIndex = ThemeBrand.entries.indexOf(settingState.userData.themeBrand),
                    data = ThemeBrand.entries.map { themeBrand ->
                        themeBrand
                            .name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercaseChar()
                            }
                    }
                        .toImmutableList(),
                    onDataChange = {
                        setThemeBrand(ThemeBrand.entries[it])
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Contrast")
                DropdownMenu(
                    currentIndex = Contrast.entries.indexOf(settingState.userData.contrast),
                    data = Contrast.entries.map { themeBrand ->
                        themeBrand
                            .name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercaseChar()
                            }
                    }
                        .toImmutableList(),
                    onDataChange = {
                        setContrast(Contrast.entries[it])
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Dark")
                DropdownMenu(
                    currentIndex = DarkThemeConfig.entries.indexOf(settingState.userData.darkThemeConfig),
                    data = DarkThemeConfig.entries.map { themeBrand ->
                        themeBrand
                            .name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercaseChar()
                            }
                    }
                        .toImmutableList(),
                    onDataChange = {
                        setDarkThemeConfig(DarkThemeConfig.entries[it])
                    },
                )
            }
        }
    }
}
//
// @Preview
// @Composable
// fun MainScreenPreview() {
//    MainScreen(
//        mainState = MainState(),
//       items =listOf(ModelUiState(2, "")).toImmutableList()
//    )
// }

@Composable
expect fun SettingScreenPreview()