package com.syrous.composelearning

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
        bottomBar = { BottomAppBar() }
    ) {

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