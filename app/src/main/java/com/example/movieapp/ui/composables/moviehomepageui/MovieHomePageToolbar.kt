package com.example.movieapp.ui.composables.moviehomepageui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
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
fun MovieToolBar(modifier: Modifier = Modifier, onSearchClicked: () -> Unit) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MovieAppColor.White)
    ) {
        val (title, search, mode) = createRefs()
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(parent.bottom)
            }, text = "Movie", style = TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MovieAppColor.Black
            )
        )
        Icon(
            modifier = Modifier
                .constrainAs(search) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(mode.start, margin = 16.dp)

                }
                .size(24.dp)
                .clickable {
                    onSearchClicked()
                },
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MovieAppColor.Black,

            )
        Icon(
            modifier = Modifier
                .constrainAs(mode) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)

                }
                .size(24.dp),
            imageVector = Icons.Outlined.DarkMode,
            contentDescription = null,
            tint = MovieAppColor.Black,
        )
    }
}