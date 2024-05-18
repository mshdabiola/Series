package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList

@Composable
expect fun Leaderboard(getRank: (ImmutableList<UserRank>) -> Unit)

@Composable
expect fun UserRankUiState(userRank: UserRank)

@Composable
expect fun MoreRankButton(modifier: Modifier = Modifier)
