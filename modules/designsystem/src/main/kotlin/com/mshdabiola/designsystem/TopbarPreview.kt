package com.mshdabiola.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SeriesTopAppBar
import com.mshdabiola.designsystem.icon.SeriesIcons
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Preview("Top App Bar")
@Composable
private fun SkTopAppBarPreview() {
    SeriesTopAppBar(
        titleRes = "Testing",
        navigationIcon = SeriesIcons.Search,
        navigationIconContentDescription = "Navigation icon",
        actionIcon = SeriesIcons.MoreVert,
        actionIconContentDescription = "Action icon",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun DetailTopAppBarPreview() {
    DetailTopAppBar()
}
