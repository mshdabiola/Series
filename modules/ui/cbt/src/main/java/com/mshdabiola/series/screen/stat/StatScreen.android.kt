package com.mshdabiola.series.screen.stat

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
actual fun StatScreenPreview() {
    StatScreen(
        statState = StatState(),
        userRankList = listOf(UserRank(null,"abiola",10,45)).toImmutableList()
    )
}