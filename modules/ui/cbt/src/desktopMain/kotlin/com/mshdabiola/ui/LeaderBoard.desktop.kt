package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList

@Composable
actual fun Leaderboard(getRank: (ImmutableList<UserRank>) -> Unit) {
}

@Composable
actual fun UserRankUiState(userRank: UserRank) {
}

@Composable
actual fun MoreRankButton(modifier: Modifier) {
}
