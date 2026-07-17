package com.app.chapter_one.ui.features.common.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.chapter_one.R
import com.app.chapter_one.ui.theme.ChapterOneTheme
import com.app.chapter_one.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun LauncherScreen(onNavigateToMain: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToMain()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp),
            contentDescription = "Logo",
        )
        Text(
            "Compose",
            style = Typography.titleLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LauncherPreview() {
    ChapterOneTheme {
        LauncherScreen(onNavigateToMain = {})
    }
}