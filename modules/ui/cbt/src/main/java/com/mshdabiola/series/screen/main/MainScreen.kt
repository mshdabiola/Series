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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.series.screen.ExamType
import com.mshdabiola.series.screen.MainViewModel
import com.mshdabiola.ui.ContinueCard
import com.mshdabiola.ui.OtherCard
import com.mshdabiola.ui.StartCard
import com.mshdabiola.ui.cbt.R
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen(onQuestion: () -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    val mainState = viewModel.mainState.collectAsStateWithLifecycle()
    MainScreen(
        mainState = mainState.value,
        onQuestion = onQuestion,
        onStartExam = viewModel::startExam,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
            .semantics { this.testTagsAsResourceId = true },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = stringResource(id = R.string.subject))
                        Text(text = stringResource(id = R.string.type), style = MaterialTheme.typography.labelMedium)
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
                    Text(text = "Hello Abiola", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Step right up and test your skills. " +
                                "Wellcome to Physics test that will challenge and entertain you",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center

                    )

                }
                Image(painter = painterResource(id = R.drawable.layer_2), contentDescription = "")

            }
            mainState.currentExam?.let {

                ContinueCard(
                    year = it.year,
                    progress = finishPercent,
                    enabled = mainState.isSubmit.not(),
                    timeRemain = mainState.totalTime - mainState.currentTime,
                    part = stringArrayResource(id = com.mshdabiola.ui.cbt.R.array.exam_part)[mainState.examPart],
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
                    painter = painterResource(id = R.drawable.layer__1),
                    onClick = {

                        onStartExam(ExamType.RANDOM, -1, 1)
                        onQuestion()
                    }
                )
                OtherCard(
                    title = "Fast finger",
                    painter = painterResource(id = R.drawable.layer_1),
                    onClick = {
                        onStartExam(ExamType.FAST_FINGER, -1, 1)
                        onQuestion()
                    }
                )
            }


        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(
            listOfAllExams = listOf(
                ExamUiState(
                    id = 7353L,
                    subjectID = 7692L,
                    year = 6756L,
                    subject = "Taya",
                    isObjOnly = false,
                    examTime = 400
                ),
            ).toImmutableList(),
            currentExam = ExamUiState(
                id = 0L,
                subjectID = 0L,
                year = 1556L,
                subject = "Math",
                isObjOnly = false,
                examTime = 0L
            )
        )
    )
}
