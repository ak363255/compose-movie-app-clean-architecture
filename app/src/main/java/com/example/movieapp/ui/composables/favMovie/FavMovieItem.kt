package com.example.movieapp.ui.composables.favMovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.models.MovieDetail
import com.example.movieapp.utils.image.ImageUtil

@Composable
fun FavMovieItem(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    onMovieItemClicked : (MovieDetail) -> Unit
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
            imageUrl = movie.poster,
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