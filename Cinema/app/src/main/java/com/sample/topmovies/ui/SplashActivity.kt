package com.sample.topmovies.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                SplashScreen()
            }
        }

        lifecycleScope.launchWhenResumed {
            delay(3000)
            startActivity(Intent(baseContext, TMDBActivity::class.java))
            finish()
        }
    }

    @Composable
    fun SplashScreen() {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
            Text(text = "Welcome to Cinema")
        }
    }
}