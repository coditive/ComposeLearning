package com.syrous.composelearning

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.bottomappbar.BottomAppBar
import com.syrous.composelearning.ui.theme.ComposeLearningTheme

class DetailsScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLearningTheme {
                DetailsScreenView()
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenView() {
    Scaffold(
        bottomBar = { BottomAppBar() },
        content = { BodyContent() }
    )
}

@Composable
fun BodyContent() {
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
                   .padding(start = 30.dp, top = 40.dp),
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
   }

}

@Preview
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