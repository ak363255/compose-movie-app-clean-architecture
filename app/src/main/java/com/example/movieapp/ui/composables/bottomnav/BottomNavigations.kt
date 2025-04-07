package com.example.movieapp.ui.composables.bottomnav

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieapp.route.MovieRoutes
import com.example.movieapp.route.route
import com.example.movieapp.ui.theme.MovieAppColor

object BottomNavigations {


    sealed class BottomNavItem(
        val tabName: String,
        val imageVector: ImageVector,
        val page: MovieRoutes
    ) {
        object Feed : BottomNavItem(
            tabName = "Feed",
            imageVector = Icons.Default.DynamicFeed,
            page = MovieRoutes.MovieListScreen
        )

        object Favorite : BottomNavItem(
            tabName = "Favorite",
            imageVector = Icons.Default.Favorite,
            page = MovieRoutes.MovieFavScreen
        )
    }

    data class MovieNavItem(
        val title: String,
        val isSelected: Boolean = false,
        val id: Int,
        val imageVector: ImageVector
    )

    val bottomNavList = listOf<BottomNavItem>(
        BottomNavItem.Feed,
        BottomNavItem.Favorite,
    )

    @Composable
    fun MovieHomeBottomNavigation(
        modifier: Modifier = Modifier,
        navController: NavController,
        bottomNavList: List<BottomNavItem>,
        bottomNavItemClicked: (BottomNavItem) -> Unit
    ) {
        val currentBackStackEntryState = navController.currentBackStackEntryAsState()
        NavigationBar(
            modifier = modifier
                .fillMaxWidth()
                .clip(bottomNavCustomShape(centerWidth = 80.dp, borderRadius = 20.dp))
                .background(MovieAppColor.GrayB3)
        ) {
            bottomNavList.forEachIndexed { index, navItem ->
                val isSelected = currentBackStackEntryState.value?.destination?.route == navItem.page.route()
                Log.d("ROUTE","is Selected ${isSelected} item ${navItem.page.route()} current ${navController.currentDestination?.route}")
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {bottomNavItemClicked(navItem)},
                    icon = {
                        Icon(
                            imageVector = navItem.imageVector,
                            contentDescription = navItem.tabName
                        )
                    },
                    label = {
                        Text(
                            text = navItem.tabName,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MovieAppColor.Black
                            )
                        )
                    },
                )
            }

        }
        /* ConstraintLayout(
             modifier = modifier
                 .fillMaxWidth()
                 .clip(bottomNavCustomShape(centerWidth = 60.dp, borderRadius = 20.dp))
                 .background(MovieAppColor.DarkGrayD3)
         ) {

             val (feed, favorite) = createRefs()
            // createHorizontalChain(feed,favorite, chainStyle = ChainStyle.SpreadInside)
             Column(
                 modifier = Modifier
                     .constrainAs(feed) {
                         top.linkTo(parent.top, margin = 4.dp)
                         bottom.linkTo(parent.bottom, margin = 4.dp)
                         start.linkTo(parent.start, margin = 48.dp)
                        // end.linkTo(favorite.start)
                     }
             ) {
                 Icon(
                     Icons.Default.DynamicFeed,
                     contentDescription = null,
                     modifier = Modifier.size(16.dp),
                 )
                 Text("Feed", style = TextStyle(
                     fontSize = 12.sp,
                     fontWeight = FontWeight.SemiBold,
                     color = MovieAppColor.Black
                 ))
             }
             Column(
                 modifier = Modifier
                     .constrainAs(favorite) {
                         top.linkTo(parent.top, margin = 4.dp)
                         bottom.linkTo(parent.bottom, margin = 4.dp)
                         end.linkTo(parent.end,margin = 48.dp)
                       //  start.linkTo(feed.end)
                     }
                     .padding(vertical = 4.dp)
             ) {
                 Icon(
                     Icons.Default.DynamicFeed,
                     contentDescription = null,
                     modifier = Modifier.size(16.dp),
                 )
                 Text("Feed", style = TextStyle(
                     fontSize = 12.sp,
                     fontWeight = FontWeight.SemiBold,
                     color = MovieAppColor.Black
                 ))

             }


         }*/
    }


    fun getMovieHomeScreenBottomNavCutomSPath(
        centerWidth: Dp,
        borderRadius: Dp,
        size: Size,
        density: Density
    ): Path = Path().apply {
        val curveRadiusInPx = with(density) { borderRadius.toPx() }
        val centerWidthInPx = with(density) { centerWidth.toPx() }
        val halfCenterWidthInPx = centerWidthInPx / 2
        val width = size.width
        val cx = width / 2
        val height = size.height
        val cy = height / 2
        moveTo(0f, curveRadiusInPx)
        //top-left corner
        arcTo(
            rect = Rect(0f, 0f, curveRadiusInPx * 2, curveRadiusInPx * 2),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(cx - halfCenterWidthInPx, 0f)
        /*quadraticTo(
            cx - 2 * halfCenterWidthInPx,
            0f,
            cx - halfCenterWidthInPx,
            halfCenterDepthInPx / 2,

            )
        quadraticTo(
            cx, halfCenterDepthInPx, // Control point (pulls the curve downward)
            cx + halfCenterWidthInPx, halfCenterDepthInPx / 2 // End at the rightmost point (top edge)
        )

        quadraticTo(
            cx + 2 * halfCenterWidthInPx,
            0f,
            cx + 3 * halfCenterWidthInPx,
            0f
        )*/
        /*   arcTo(
               rect = Rect(cx - curveRadiusInPx, 0f - curveRadiusInPx, cx + curveRadiusInPx, curveRadiusInPx * 2 - curveRadiusInPx),
               startAngleDegrees = 180f,  // Start from the right side
               sweepAngleDegrees = -180f,  // Sweep counterclockwise to the left side
               forceMoveTo = false
           )*/
        arcTo(
            rect = Rect(
                left = cx - halfCenterWidthInPx,
                top = 0f - halfCenterWidthInPx,
                right = cx + halfCenterWidthInPx,
                bottom = halfCenterWidthInPx,
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -180f,
            forceMoveTo = false
        )
        lineTo(width - curveRadiusInPx, 0f)
        //draw top-right radius
        arcTo(
            rect = Rect(
                width - 2 * curveRadiusInPx, 0f, width, 2 * curveRadiusInPx
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(width, height - curveRadiusInPx)
        arcTo(
            rect = Rect(
                left = width - 2 * curveRadiusInPx,
                top = height - 2 * curveRadiusInPx,
                right = width,
                bottom = height
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(2 * curveRadiusInPx, height)
        arcTo(
            rect = Rect(
                left = 0f,
                top = height - 2 * curveRadiusInPx,
                right = 2 * curveRadiusInPx,
                bottom = height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(0f, 2 * curveRadiusInPx)
        close()
    }

    fun bottomNavCustomShape(centerWidth: Dp, borderRadius: Dp) =
        object : Shape {
            override fun createOutline(
                size: Size,
                layoutDirection: LayoutDirection,
                density: Density
            ): Outline {
                val curveRadiusInPx = with(density) { borderRadius.toPx() }
                val path = getMovieHomeScreenBottomNavCutomSPath(
                    borderRadius = borderRadius,
                    size = size,
                    centerWidth = centerWidth,
                    density = density
                )
                return Outline.Generic(path)


            }


        }
}