package com.example.movieapp.ui.composables.favMovie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.example.domain.models.MovieDetail
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun Screen(movies: List<MovieDetail>) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Get screen width in pixels
    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.toPx()
    }

    val screenWidthDp = configuration.screenWidthDp
    val moviePosterWidthDp = screenWidthDp.dp * 0.70f
    val moviePosterSpacingDp = moviePosterWidthDp + 20.dp
    var indexSelected by remember { mutableStateOf(0) }
    val carouselState = rememberCarouselState(
        spacing = moviePosterSpacingDp,
        onSelectedIndexChange = {
            indexSelected = it
        }
    )
    Box {
        MovieCarousel(
            movies,
            spacing = moviePosterSpacingDp,
            state = carouselState,
            foreGroundContent = { item, expanded ->
                MoviePoster(
                    expanded = expanded,
                    expandedWidth = screenWidthDp.dp,
                    normalWidth = moviePosterWidthDp,
                    movie = item,
                )
            },
            getForeGroundImage = { movie ->
                movie.poster
            },
            getBackgroundImage = { movie ->
                movie.poster
            },
        )
        BuyTicketButton(
            modifier = Modifier
                .width(moviePosterWidthDp)
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            text = "Buy Ticket"
        ) {
            carouselState.expandSelectedItem()
        }
    }
}

private fun indexToOffset(index: Int, spacing: Float): Float = -1 * index * spacing
private fun offsetToIndex(offset: Float, spacing: Float): Int = floor(-offset / spacing).toInt()


@Composable
fun rememberCarouselState(
    spacing: Dp,
    onSelectedIndexChange: (Int) -> Unit
): CarouselState {
    val density = LocalDensity.current
    val moviePosterSpacingPx = with(density) { spacing.toPx() }
    val animatedOffset = remember { Animatable(initialValue = 0f) }
    val coroutineScope = rememberCoroutineScope()
    val expanded = remember { Animatable(initialValue = 0f) }
    val carouselState = CarouselState(
        onSelectedIndexChange,
        spacingInPx = moviePosterSpacingPx,
        expanded = expanded,
        animateExpanded = { animateTo ->
            coroutineScope.launch {
                expanded.animateTo(animateTo, animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,

                ),
                )
            }
        },
        animatedOffset = animatedOffset,
        animateTargetOffset = { targetOffset, smooth ->
            coroutineScope.launch {
                if (smooth) {
                    animatedOffset.animateTo(
                        targetOffset,
                        animationSpec = tween(durationMillis = 100)
                    )
                } else {
                    animatedOffset.snapTo(targetOffset)
                }
            }
        }
    )
    return remember { carouselState }
}

class CarouselState(
    private val onSelectedIndexChange: (Int) -> Unit,
    val spacingInPx: Float,
    val animatedOffset: Animatable<Float, AnimationVector1D>,
    val expanded: Animatable<Float, AnimationVector1D>,
    private val animateExpanded: (targetValue: Float) -> Unit,
    val animateTargetOffset: (Float, Boolean) -> Unit
) {
    private var itemCount: Int = 0
    private val maxOffset: Float get() = -1 * ((itemCount - 1) * spacingInPx)
    private val minOffset: Float = 0f
    var expandedIndex by mutableStateOf<Int?>(null)
        private set

    val cntrl = ScrollableState {
        consumeDelta(it)
    }
    private var selectedIndex: Int = 0

    fun onSelectedIndex(index: Int) {
        selectedIndex = index
        onSelectedIndexChange(index)
    }

    fun getSelectedIndex() = selectedIndex

    fun updateItemCount(count: Int) {
        this.itemCount = count
    }

    fun expandSelectedItem() {
        if (expandedIndex != null) {
            expandedIndex = null
            animateExpanded(0f)
        } else {
            expandedIndex = selectedIndex
            animateExpanded(1f)
        }
    }

    private fun consumeDelta(delta: Float): Float {
        var consumed = 0f
        if(expandedIndex == null){
            val newOffset = (animatedOffset.value + delta).coerceIn(maxOffset, minOffset)
             consumed = newOffset - animatedOffset.value
            animateTargetOffset(newOffset, false)
        }
        return consumed
    }

}


@Composable
fun <T> MovieCarousel(
    movies: List<T>,
    spacing: Dp,
    state: CarouselState,
    foreGroundContent: @Composable (item: T, expanded: Boolean) -> Unit,
    getBackgroundImage: (T) -> Any,
    getForeGroundImage: (T) -> Any,
    modifier: Modifier = Modifier
) {
    state.updateItemCount(movies.size)
    val density = LocalDensity.current
    val moviePosterSpacingPx = state.spacingInPx
    val animatedOffset = state.animatedOffset
    val indexFraction = -animatedOffset.value / moviePosterSpacingPx
    val expanded = state.expanded
    LaunchedEffect(state.cntrl.isScrollInProgress) {
        if (!state.cntrl.isScrollInProgress) {
            val nearestIndex = (-state.animatedOffset.value / moviePosterSpacingPx).roundToInt()
            val targetOffset = -nearestIndex * moviePosterSpacingPx
            state.onSelectedIndex(nearestIndex)
            state.animateTargetOffset(targetOffset, true)
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        movies.forEachIndexed { index, item ->
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .carouselBackground(
                        getExpandedFloat = { expanded.value },
                        index = index,
                        getIndexFraction = {
                            indexFraction
                        }
                    ),
                model = getBackgroundImage(item),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(spacing)
                    .fillMaxHeight(0.75f)
                    .carouselExpandedBackground(
                        state = state,
                        getExpandedFloat = { expanded.value },
                        index = index,
                        getIndexFraction = {
                            indexFraction
                        }
                    ),
                model = getForeGroundImage(item),
                contentDescription = null,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f) // Gradient height
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.7f),
                            Color.White
                        ),
                        startY = 20f, // Start fading slightly above the bottom
                        endY = 200f // Extend transition further
                    )
                )
        )
        movies.forEachIndexed { index, item ->
            val centre = moviePosterSpacingPx * index
            val disFromCentre = abs(animatedOffset.value - centre)
            val maxOffset = with(density) {
                30.dp.toPx()
            }
            val yOffset = lerp(maxOffset, 0f, 1 - abs(indexFraction - index))
            Box(
                modifier = Modifier
                    .padding(top = 300.dp)
                    .zIndex(if (state.expandedIndex == index) 1f else 0f)
                    .padding(bottom = if(state.expandedIndex == index) 0.dp else   80.dp)
                    .align(Alignment.BottomCenter)
                    .scrollable(state = state.cntrl, orientation = Orientation.Horizontal)
                    .graphicsLayer {
                        translationX = animatedOffset.value + (moviePosterSpacingPx) * index
                        translationY = yOffset
                    }
            ) {
                foreGroundContent(item, state.expandedIndex == index)
            }
        }

    }

}

@Composable
fun Modifier.carouselBackground(
    index: Int,
    getIndexFraction: () -> Float,
    getExpandedFloat: () -> Float
) = this.graphicsLayer {
    val indexFraction = getIndexFraction()
    val currentIndex = indexFraction.toInt()
    val isCurrent = currentIndex == index
    val isNext = currentIndex + 1 == index
    val isRange = isCurrent || isNext
    val bgAlpha = if (isRange) 1f - getExpandedFloat() else 0f
    val rectShape = when {
        !isRange -> RectangleShape
        else -> FractionalRectangle(
            leftFraction = indexFraction - index,
            rightFraction = 1 + indexFraction - index
        )
    }
    alpha = bgAlpha
    shape = rectShape
    clip = true
}

@Composable
fun Modifier.carouselExpandedBackground(
    state: CarouselState,
    index: Int,
    getIndexFraction: () -> Float,
    getExpandedFloat: () -> Float
) = this
    .graphicsLayer {
        val indexFraction = getIndexFraction()
        val currentIndex = indexFraction.toInt()
        val isCurrent = currentIndex == index
        val isNext = currentIndex + 1 == index
        val isPrev = currentIndex - 1 == index
        val isRange = isCurrent || isNext
        val bgAlpha = if (currentIndex == index || isPrev || isNext) getExpandedFloat() else 0f

        alpha = bgAlpha
        translationX = (index - state.getSelectedIndex()) * state.spacingInPx * 0.75f
        translationY = if(isCurrent)lerp(0f, -400f, getExpandedFloat())  else lerp(0f, -300f, getExpandedFloat())
    }
    .zIndex(if (index == getIndexFraction().toInt()) 1f else 0f)

fun FractionalRectangle(leftFraction: Float, rightFraction: Float): Shape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                top = 0f,
                left = leftFraction * size.width,
                right = rightFraction * size.width,
                bottom = size.height
            )
        )
    }

}

@Composable
fun getScreenWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp  // Returns width in DP
}

@Composable
fun BuyTicketButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        shape = RectangleShape,
        colors = ButtonColors(
            contentColor = Color.DarkGray,
            disabledContentColor = Color.DarkGray,
            disabledContainerColor = Color.DarkGray,
            containerColor = Color.DarkGray
        ),
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    expanded: Boolean,
    expandedWidth: Dp,
    normalWidth: Dp
) {
    val transition = updateTransition(expanded)
    val animateSize by transition.animateDp { expanded ->
        if (expanded) {
            expandedWidth
        } else {
            normalWidth
        }
    }
    val animateAlpha by transition.animateFloat { expanded ->
        if (expanded) {
            0f
        } else {
            1f
        }

    }

    val animateImageWidth by transition.animateDp { expanded ->
        if (expanded) {
            10.dp
        } else {
            normalWidth
        }

    }

    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
            .verticalScroll(state = rememberScrollState())
            .background(color = Color.White)

    ) {
        val (unexpandedId, expandedId) = createRefs()
        ConstraintLayout(
            modifier = modifier
                .constrainAs(unexpandedId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(animateSize)
                .wrapContentHeight()
                .background(color = Color.White)
                .padding(20.dp)
                .animateContentSize()
        ) {
            val (posterId, movieTitleId, genereId) = createRefs()
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(normalWidth)
                    .constrainAs(posterId) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .aspectRatio(259 / 390.0f)
                    .clip(RoundedCornerShape(20.dp))
                    .graphicsLayer {
                        alpha = animateAlpha
                    },
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .constrainAs(movieTitleId) {
                        top.linkTo(posterId.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }
                    .graphicsLayer {
                        alpha = animateAlpha
                    },
                text = movie.title,
                fontSize = 24.sp,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = animateAlpha
                    }
                    .constrainAs(genereId) {
                        top.linkTo(movieTitleId.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                movie.chips.forEach {
                    Chip(label = it)
                }
            }

        }
            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically(
                    initialOffsetY = {
                        440
                    }
                ) + fadeIn()
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .constrainAs(expandedId) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    val (actorTitleId, actorsRowId, descriptionId,movieTitleId,genereId) = createRefs()

                    Text(
                        modifier = Modifier
                            .constrainAs(movieTitleId) {
                                top.linkTo(parent.top, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                            },
                        text = movie.title,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier
                            .constrainAs(genereId) {
                                top.linkTo(movieTitleId.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        movie.chips.forEach {
                            Chip(label = it)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .constrainAs(actorTitleId) {
                                start.linkTo(parent.start, margin = 12.dp)
                                top.linkTo(genereId.bottom, margin = 12.dp)
                            },
                        text = "Actors", style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    )
                    LazyRow(
                        modifier = Modifier
                            .constrainAs(actorsRowId) {
                                start.linkTo(parent.start)
                                top.linkTo(actorTitleId.bottom, margin = 12.dp)
                            }
                    ) {
                        movie.fakeActors.forEachIndexed { index, movieActor ->
                            item {
                                Spacer(modifier = Modifier.width(12.dp))
                                Actor(movieActor)
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .constrainAs(descriptionId) {
                                start.linkTo(parent.start, margin = 12.dp)
                                end.linkTo(parent.end)
                                top.linkTo(actorsRowId.bottom, margin = 12.dp)
                                bottom.linkTo(parent.bottom)
                            }

                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Introduction",
                            style = TextStyle(fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            movie.plot,
                            style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                    }

                }
            }


    }

}

@Composable
fun Actor(actor: com.example.domain.models.MovieActor) {
    Column {
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(200.dp)
        ) {
            AsyncImage(
                model = actor.image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = actor.name,
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.DarkGray,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
fun Chip(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(percent = 50))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}


@Immutable
data class Movie(
    val title: String,
    val posterUrl: String,
    val bgUrl: String,
    val color: Color,
    val chips: List<String>,
    val actors: List<MovieActor> = emptyList(),
    val introduction: String = ""
)

data class MovieActor(
    val name: String,
    val image: String
)

/*val movies = listOf(
    Movie(
        title = "Good Boys",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        color = Color.Red,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            ),
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    ),
    Movie(
        title = "Joker",
        posterUrl = "https://i.etsystatic.com/15963200/r/il/25182b/2045311689/il_794xN.2045311689_7m2o.jpg",
        bgUrl = "https://images-na.ssl-images-amazon.com/images/I/61gtGlalRvL._AC_SY741_.jpg",
        color = Color.Blue,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    ),
    Movie(
        title = "The Hustle",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        color = Color.Yellow,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    ),
    Movie(
        title = "Good Boys",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc1NjIzODAxMF5BMl5BanBnXkFtZTgwMTgzNzk1NzM@._V1_.jpg",
        color = Color.Red,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            ),
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    ),
    Movie(
        title = "Joker",
        posterUrl = "https://i.etsystatic.com/15963200/r/il/25182b/2045311689/il_794xN.2045311689_7m2o.jpg",
        bgUrl = "https://images-na.ssl-images-amazon.com/images/I/61gtGlalRvL._AC_SY741_.jpg",
        color = Color.Blue,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    ),
    Movie(
        title = "The Hustle",
        posterUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        bgUrl = "https://m.media-amazon.com/images/M/MV5BMTc3MDcyNzE5N15BMl5BanBnXkFtZTgwNzE2MDE0NzM@._V1_.jpg",
        color = Color.Yellow,
        chips = listOf("Action", "Drama", "History"),
        actors = listOf(
            MovieActor(
                "Jaoquin Phoenix",
                "https://image.tmdb.org/t/p/w138_and_h175_face/nXMzvVF6xR3OXOedozfOcoA20xh.jpg"
            ),
            MovieActor(
                "Robert De Niro",
                "https://image.tmdb.org/t/p/w138_and_h175_face/cT8htcckIuyI1Lqwt1CvD02ynTh.jpg"
            ),
            MovieActor(
                "Zazie Beetz",
                "https://image.tmdb.org/t/p/w138_and_h175_face/sgxzT54GnvgeMnOZgpQQx9csAdd.jpg"
            )
        ),
        introduction = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.".repeat(20)
    )
)*/
