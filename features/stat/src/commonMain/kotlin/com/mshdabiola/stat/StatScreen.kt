/*
 *abiola 2022
 */

package com.mshdabiola.stat

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
import com.mshdabiola.ui.MoreRankButton
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.UserRankUiState
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,


) {
    val viewModel: StatViewModel = KoinCommonViewModel()
    var userRankList by remember {
        mutableStateOf(emptyList<UserRank>().toImmutableList())
    }


    StatScreen(
        statState = StatState(),
        userRankList = userRankList,
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
            horizontalAlignment = Alignment.CenterHorizontally,
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

//@Composable
//expect fun StatScreenPreview()