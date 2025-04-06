package com.example.movieapp.utils.image

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest

object ImageUtil {
    @Composable
    fun loadImage(
        imageUrl : String,
        modifier : Modifier = Modifier,
        loaderPlaceHolder : @Composable ()-> Unit,
        errorPlaceHolder : @Composable () -> Unit
    ){
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .scale(coil3.size.Scale.FILL)
                .build(),
            loading = { loaderPlaceHolder()},
            error = { errorPlaceHolder() },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}