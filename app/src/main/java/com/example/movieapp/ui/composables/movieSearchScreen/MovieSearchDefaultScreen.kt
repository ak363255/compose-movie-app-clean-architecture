package com.example.movieapp.ui.composables.movieSearchScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.YoutubeSearchedFor
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
fun MovieSearchDefaultScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MovieAppColor.White)
    ) {
        val (searchIcon, title, subtitle) = createRefs()
        Image(
            imageVector = Icons.Default.YoutubeSearchedFor,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(searchIcon) {
                    top.linkTo(parent.top, margin = 56.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "Find Your Next Watch",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(searchIcon.bottom, margin = 32.dp)
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
            text = "Start typing to search for movies and shows.",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(searchIcon.bottom, margin = 32.dp)
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