package com.mshdabiola.series.screen.stat

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.semanticsCommon
import com.mshdabiola.series.screen.MainViewModel
import com.mshdabiola.ui.Leaderboard
import com.mshdabiola.ui.MoreRankButton
import com.mshdabiola.ui.UserRankUiState
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun StatScreen() {
    val viewModel: MainViewModel = KoinCommonViewModel()
    var userRankList by remember {
        mutableStateOf(emptyList<UserRank>().toImmutableList())
    }
    Leaderboard {
        userRankList = it
    }

    StatScreen(
        statState = StatState(),
        userRankList = userRankList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StatScreen(
    statState: StatState,
    userRankList: ImmutableList<UserRank>,
) {
    Scaffold(
        modifier = Modifier.semanticsCommon {},
        topBar = {
            TopAppBar(
                title = { Text(text = "Ranks") },

                )
        },

        ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(userRankList) {
                UserRankUiState(it)
            }
            item {
                if (userRankList.isNotEmpty()) {

                    MoreRankButton()
                }
            }
        }
    }

}

@Composable
expect fun StatScreenPreview()
