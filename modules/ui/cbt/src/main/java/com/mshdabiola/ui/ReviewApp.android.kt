package com.mshdabiola.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
actual fun ReviewApp(show: Boolean) {
    val context = LocalContext.current
    val review = {
        val manager = ReviewManagerFactory.create(context)

        val request = manager.requestReviewFlow()
        request.addOnSuccessListener {
            val flow = manager.launchReviewFlow(context as Activity, it)
            flow.addOnSuccessListener {
            }
        }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
    LaunchedEffect(key1 = show) {
        if (show) {
            review()
        }
    }
}
