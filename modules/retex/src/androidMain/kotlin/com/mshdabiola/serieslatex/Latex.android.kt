package com.mshdabiola.serieslatex

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.himamis.retex.renderer.android.FactoryProviderAndroid
import com.himamis.retex.renderer.android.graphics.ColorA
import com.himamis.retex.renderer.android.graphics.ImageA
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.graphics.Image
import java.io.ByteArrayOutputStream

@Composable
actual fun LoadTex(size: Double) {
    val context = LocalContext.current
    val con = LocalConfiguration.current
    val density = androidx.compose.ui.platform.LocalDensity.current

    if (FactoryProvider.getInstance() == null) {
        FactoryProvider.setInstance(
            FactoryProviderAndroid(
                context.assets,
            ),
        )
        fontScale = size * density.density * con.fontScale

    }

}


actual fun Color.toPaintColor(): com.himamis.retex.renderer.share.platform.graphics.Color {
    return ColorA(toArgb())
}

actual fun ImageBitmap.toByteArray(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

actual fun Image.getImageBitmap(): ImageBitmap {
    return (this as ImageA).bitmap
}