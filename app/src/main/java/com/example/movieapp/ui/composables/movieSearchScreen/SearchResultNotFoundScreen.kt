package com.example.movieapp.ui.composables.movieSearchScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movieapp.ui.theme.MovieAppColor


@Composable
fun SearchResultNotFound(modifier: Modifier = Modifier){
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MovieAppColor.White)
    ) {
        val (noDataFound, title, subtitle) = createRefs()
        Image(
            imageVector = Icons.AutoMirrored.Filled.PlaylistAddCheck,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(noDataFound) {
                    top.linkTo(parent.top, margin = 56.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "No Results Found",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(noDataFound.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MovieAppColor.Black
            )
        )
        Text(
            text = "Please try another search.",
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MovieAppColor.Black
            )
        )

    }
}