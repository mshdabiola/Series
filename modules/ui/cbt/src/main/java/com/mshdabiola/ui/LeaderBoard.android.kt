package com.mshdabiola.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.images.ImageManager
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.leaderboard.LeaderboardVariant
import com.mshdabiola.ui.cbt.R
import com.mshdabiola.ui.state.UserRank
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
actual fun Leaderboard(getRank: (ImmutableList<UserRank>) -> Unit) {
    val activity = LocalContext.current as Activity
    val leaderboardsClient = PlayGames.getLeaderboardsClient(activity)
    val id = stringResource(id = R.string.leaderboard_ranks)

    LaunchedEffect(key1 = Unit) {
        leaderboardsClient.loadTopScores(
            id,
            LeaderboardVariant.TIME_SPAN_ALL_TIME,
            LeaderboardVariant.COLLECTION_PUBLIC,
            25,
        )
            .addOnCompleteListener { annotatedDataTask ->
                val data = annotatedDataTask.result?.let { annotatedData ->
                    annotatedData.get()?.scores
                        ?.mapNotNull {
                            if (it == null) {
                                null
                            } else {
                                UserRank(
                                    imageUrl = it.scoreHolderIconImageUri.toString(),
                                    name = it.scoreHolderDisplayName,
                                    score = it.rawScore,
                                    position = it.rank,
                                )
                            }
                        }
                }
                if (data != null) {
                    getRank(data.toImmutableList())
                }
            }
            .addOnFailureListener { it.printStackTrace() }
    }
}

@Composable
actual fun UserRankUiState(userRank: UserRank) {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = userRank) {
        imageBitmap = loadImage(context, userRank.imageUrl)
    }
    ListItem(
        headlineContent = { Text(text = userRank.name) },
        supportingContent = { Text(text = "Score : ${userRank.score}") },
        trailingContent = {
            Text(
                text = userRank.position.toString(),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingContent = {
            if (imageBitmap != null) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    bitmap = imageBitmap!!,
                    contentDescription = "",
                )
            }
        },
    )
}

suspend fun loadImage(context: Context, path: String?) = suspendCoroutine {
    if (path == null) {
        Timber.e("load image path is null")
        it.resume(null)
    }
    val image = ImageManager.create(context)
    image.loadImage({ _: Uri, drawable: Drawable?, isRequestDrawable: Boolean ->

        if (isRequestDrawable && drawable != null) {
            val image2 = ImageBitmap(100, 100, ImageBitmapConfig.Argb8888)
            val canvas = Canvas(image2)

            drawable.bounds = Rect(0, 0, image2.width, image2.height)

            drawable.draw(canvas.nativeCanvas)

            Timber.e("load image")

            it.resume(image2)
        }
    }, Uri.parse(path))
}

@Composable
actual fun MoreRankButton(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    val leaderboardsClient = PlayGames.getLeaderboardsClient(activity)
    val id = stringResource(id = R.string.leaderboard_ranks)

    TextButton(onClick = {
        try {
            leaderboardsClient
                .getLeaderboardIntent(id)
                .addOnSuccessListener {
                    activity.startActivityForResult(it, 23)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // leaderboardsClient.getLeaderboardIntent(id,LeaderboardVariant.TIME_SPAN_ALL_TIME,LeaderboardVariant.COLLECTION_PUBLIC)
    }) {
        Text(modifier = modifier, text = "More")
    }
}
