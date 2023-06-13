package com.mshdabiola.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MainScreen(onQuestion: () -> Unit) {
    val viewModel: MainViewModel = koinViewModel()
    val mainState = viewModel.mainState.collectAsStateWithLifecycle()
    val modelst = viewModel.modelState.collectAsStateWithLifecycle()
    MainScreen(
        onQuestion = onQuestion,
        mainState = mainState.value,
        items = modelst.value,
        setName = viewModel::addName
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun MainScreen(
    mainState: MainState = MainState(),
    onQuestion: () -> Unit = {},
    items: ImmutableList<ModelUiState>,
    setName: (String) -> Unit = {}
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
//    LaunchedEffect(key1 = mainState.messages, block = {
//        Timber.d(mainState.messages.joinToString())
//    })
    var name by remember {
        mutableStateOf("")
    }
//    NotifySnacker(snackHostState = snackbarHostState, notifys = mainState.messages)
    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = "Physics")
                        Text(text = "Waec series", style = MaterialTheme.typography.labelMedium)
                    }

                })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
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
            Spacer(modifier = Modifier.height(8.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color.Yellow)) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = "You are soon closed to end, finish your quiz and find out your scores")
                    Text(text = "Year :  2011")
                    Text(text = "Question progress : 50%")
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = 0.5f)

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onQuestion
                    ) {
                        Text(text = "Continue Exam")
                    }

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color.Yellow)) {
                Row(Modifier.padding(8.dp)) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Ready to challenge yourself with new test? Let go!"
                    )
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Start exam")
                    }

                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedCard {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Blue.copy(alpha = 0.4f))
                                .padding(16.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(64.dp),
                                painter = painterResource(id = R.drawable.layer__1),
                                contentDescription = "random test",
                                tint = Color.Blue
                            )
                        }

                        Text(text = "Random test")
                    }
                }
                OutlinedCard {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Blue.copy(alpha = 0.4f))
                                .padding(16.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(64.dp),
                                painter = painterResource(id = R.drawable.layer_1),
                                contentDescription = "random test",
                                tint = Color.Blue
                            )
                        }

                        Text(text = "Fast finger")
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(),
        items = listOf(ModelUiState(2, "")).toImmutableList()
    )
}
