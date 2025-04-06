package com.example.movieapp.utils.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object DimensionExt {
    @Composable
    fun Dp.toPx(): Int{
        val density = LocalDensity.current
        return  with(density){ this@toPx.toPx().toInt() }
    }
}