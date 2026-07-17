package com.app.chapter_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.chapter_one.nav.AppNavHost
import com.app.chapter_one.ui.theme.ChapterOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChapterOneTheme {
                AppNavHost()
            }
        }
    }
}

