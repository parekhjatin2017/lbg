package com.sample.topmovies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lbg.presentation.theme.CinemaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class TMDBActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                runBlocking {
                    delay(SPLASH_DURATION_MS)
                }
                false
            }
        }
        setContent {
            CinemaTheme {
                AppNavigation()
            }
        }
    }

    companion object {
        private const val SPLASH_DURATION_MS = 3000L
    }
}
