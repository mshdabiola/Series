package com.mshdabiola.series.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.collectAsStateWithLifecycleCommon
import com.mshdabiola.mvvn.semanticsCommon
import com.mshdabiola.series.screen.ExamType
import com.mshdabiola.series.screen.MainViewModel
import com.mshdabiola.series.screen.getExamPart
import com.mshdabiola.series.screen.getIconLayer1
import com.mshdabiola.series.screen.getIconLayer2
import com.mshdabiola.series.screen.getIconLayer3
import com.mshdabiola.series.screen.getStringSubject
import com.mshdabiola.series.screen.getStringType
import com.mshdabiola.ui.ContinueCard
import com.mshdabiola.ui.OtherCard
import com.mshdabiola.ui.PlayLogin
import com.mshdabiola.ui.StartCard

@Composable
internal fun MainScreen(onQuestion: () -> Unit) {
    val viewModel: MainViewModel = KoinCommonViewModel()
    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()
    MainScreen(
        mainState = mainState.value,
        onQuestion = onQuestion,
        onStartExam = viewModel::startExam,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    mainState: MainState,
    onQuestion: () -> Unit = {},
    onStartExam: (ExamType, Int, Int) -> Unit = { _, _, _ -> },
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val finishPercent = remember(mainState.choose) {
        var choose = mainState
            .choose
            .flatten()
        choose.count {
            it > -1
        } / choose.size.toFloat()
    }

    val state = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    NotifySnacker(snackHostState = snackbarHostState, notifys = mainState.messages)
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .semanticsCommon {},
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = getStringSubject())
                        Text(
                            text = getStringType(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                    }
                }

            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(state)
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    PlayLogin()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Step right up and test your skills. " +
                                "Wellcome to Physics test that will challenge and entertain you",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center

                    )

                }
                Image(painter = getIconLayer2(), contentDescription = "")

            }
            mainState.currentExam?.let {

                ContinueCard(
                    year = it.year,
                    progress = finishPercent,
                    enabled = mainState.isSubmit.not(),
                    timeRemain = mainState.totalTime - mainState.currentTime,
                    part = getExamPart()[mainState.examPart],
                    onClick = {
                        onQuestion()
                    }
                )

            }

            StartCard(
                exams = mainState.listOfAllExams,
                isSubmit = mainState.isSubmit,
                onClick = { yearIndex, typeIndex ->
                    onStartExam(ExamType.YEAR, yearIndex, typeIndex)
                    onQuestion()
                })
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                OtherCard(
                    title = "Random exam",
                    painter = getIconLayer1(),
                    onClick = {

                        onStartExam(ExamType.RANDOM, -1, 1)
                        onQuestion()
                    }
                )
                OtherCard(
                    title = "Fast finger",
                    painter = getIconLayer3(),
                    onClick = {
                        onStartExam(ExamType.FAST_FINGER, -1, 1)
                        onQuestion()
                    }
                )
            }


        }
    }
}

@Composable
expect fun MainScreenPreview()