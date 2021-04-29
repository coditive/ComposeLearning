package com.syrous.composelearning

import android.content.Context
import android.content.Intent
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import com.syrous.composelearning.ui.theme.ComposeLearningTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLearningTheme {
                val screenState = remember { mutableStateOf(HomeScreenState.MASTER_SCREEN) }
                if(screenState.value == HomeScreenState.MASTER_SCREEN)
                    HomeScreenView(screenState = screenState)
                else
                    DetailsScreenView(screenState = screenState)
            }
        }
    }
}

data class WatchItem(
    @DrawableRes val posterId: Int,
    val itemName: String,
    val itemPrice: String
)

data class WristWatchItem(
    @DrawableRes val wristWatchImageId: Int,
    val wristWatchName: String,
    val wristWatchSpecs: String,
    val wristWatchPrice: String
)

enum class HomeScreenState {
    MASTER_SCREEN,
    DETAILS_SCREEN
}

@Composable
fun HomeScreenView(screenState: MutableState<HomeScreenState>) {
        val context = LocalContext.current

        val itemList = listOf(
            WatchItem(R.drawable.image_4, "EKHOLM", "$249"),
            WatchItem(R.drawable.image_5, "CELSO", "$220"),
            WatchItem(R.drawable.image_6, "HISAKO", "$249")
        )

        val wristWatchList = listOf(
            WristWatchItem(R.drawable.wrist_watch_1, "ORMOUS", "White, size L", "$249"),
            WristWatchItem(R.drawable.wrist_watch_2, "HISAKO", "Black, size L", "$249")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar()

            LazyRow(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 40.dp)
            ) {
                items(3, itemContent = {
                        item ->
                    CarouselItem(
                        posterId = itemList[item].posterId,
                        itemName = itemList[item].itemName,
                        itemPrice = itemList[item].itemPrice,
                        screenState = screenState
                    )
                })
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Discover",
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Icon(painter = painterResource(id = R.drawable.ic_rectangle_2),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp, top = 10.dp, end = 20.dp)
            ) {
                items(2, itemContent = {
                    item ->
                    WristWatchItemView(
                        watchImageId = wristWatchList[item].wristWatchImageId,
                        watchName = wristWatchList[item].wristWatchName,
                        watchSpecs = wristWatchList[item].wristWatchSpecs,
                        watchPrice = wristWatchList[item].wristWatchPrice
                    )
                })
            }
        }
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, top = 60.dp, bottom = 10.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "Watches",
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CarouselItem(
    @DrawableRes posterId: Int,
    itemName: String,
    itemPrice: String,
    screenState: MutableState<HomeScreenState>
) {
    Box(
        modifier = Modifier
            .width(320.dp)
            .height(280.dp)
            .clickable {
                screenState.value = HomeScreenState.DETAILS_SCREEN
            }
    ) {
        Image(
            painter = painterResource(id = posterId),
            contentDescription = null,
            modifier = Modifier
                .width(320.dp)
                .height(280.dp)
                .aspectRatio(0.875f)
        )

        Text(text = itemName , color = Color.White,
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
                    val firstBaseLine = placeable[FirstBaseline]

                    val placeableY = constraints.maxHeight * 0.77f - firstBaseLine
                    val placeableX = constraints.maxWidth * 0.2f
                    layout(placeableX.toInt(), placeableY.toInt()) {
                        placeable.place(placeableX.toInt(), placeableY.toInt())
                    }
                },
            fontSize = 24.sp
        )

        Text(text = itemPrice,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
                    val firstBaseLine = placeable[FirstBaseline]

                    val placeableY = constraints.maxHeight * 0.85f - firstBaseLine
                    val placeableX = constraints.maxWidth * 0.2f

                    layout(placeableX.toInt(), placeableY.toInt()) {
                        placeable.place(placeableX.toInt(), placeableY.toInt())
                    }
                }
        )
    }
}

@Composable
fun WristWatchItemView(
    @DrawableRes watchImageId: Int,
    watchName: String,
    watchSpecs: String,
    watchPrice: String
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFF8F8F8))
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = watchImageId),
                contentDescription = null,
                modifier = Modifier.size(93.dp)
            )

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = watchName, color = Color.Black, fontSize = 17.sp)
                Text(text = watchSpecs, color = Color.Black, fontSize = 13.sp)
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = watchPrice, color = Color.Black, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun DetailsScreenView(screenState: MutableState<HomeScreenState>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .wrapContentSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.image_6),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                contentScale = ContentScale.FillBounds
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 30.dp, top = 40.dp)
                    .clickable {
                        screenState.value = HomeScreenState.MASTER_SCREEN
                    },
                tint = Color.Black

            )

            LinearProgressIndicator(
                progress = 0.4f,
                modifier = Modifier
                    .padding(
                        top = (constraints.maxHeight * .2f).dp,
                        start = (constraints.maxWidth * .08f).dp
                    )
                    .height(2.dp),
                color = Color(0xFFFFFFFF),
                backgroundColor = Color(0xABFFFFFF)
            )

        }

        Text(
            text = "HISAKO",
            fontSize = 24.sp,
            letterSpacing = 10.sp,
            modifier = Modifier
                .padding(top = 40.dp, start = 30.dp),
            color = Color.Black
        )

        Text(
            text = "$249",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 30.dp, start = 30.dp),
            color = Color.Black
        )

        Text(
            text = "Named after asteroid 6 0 9 4 (h i s a k o) \nis currently travelling through time and \nspace.",
            fontSize = 16.sp,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 20.dp, start = 30.dp),
            color = Color.Black
        )

        BottomAppBar()
    }

}

@Composable
fun BottomAppBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier
            .background(color = Color(0xFFD5A587))
            .padding(start = 90.dp, end = 90.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Text("BUY NOW",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Box(modifier = Modifier
            .border(1.dp, color = Color(0xFF020102))
            .padding(18.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                contentDescription = null,
                tint = Color.Black
            )
        }

    }
}

