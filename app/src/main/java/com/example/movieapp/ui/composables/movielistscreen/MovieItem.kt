package com.example.movieapp.ui.composables.movielistscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.models.Movie
import com.example.movieapp.ui.theme.MovieAppColor
import com.example.movieapp.utils.image.ImageUtil

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieItemClicked : (Movie) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onMovieItemClicked(movie)
            },

        ) {
        val (image) = createRefs()
        ImageUtil.loadImage(
            imageUrl = movie.moviePoster,
            modifier = Modifier
                .aspectRatio(9 / 16f)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            loaderPlaceHolder = {},
            errorPlaceHolder = {}
        )
    }
}