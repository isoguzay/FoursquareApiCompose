package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.adyen.android.assignment.ui.screens.home.view.HomeScreen
import com.adyen.android.assignment.ui.theme.AdyenApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keepSplashScreen = true

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                keepSplashScreen
            }
        }

        lifecycleScope.launchWhenCreated {

            delay(2000)
            keepSplashScreen = false

            setContent {
                AdyenApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        HomeScreen()
                    }
                }
            }

        }
    }

}
