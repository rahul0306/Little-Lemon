package com.example.littlelemon.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {
    val conf = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = with(density) { (conf.screenWidthDp.dp).toPx() }

    val scale = remember { Animatable(0.9f) }
    val alpha = remember { Animatable(0f) }
    val frame = remember { Animatable(-screenWidth) }
    val lemon = remember { Animatable(+screenWidth) }

    val auth = FirebaseAuth.getInstance()
    LaunchedEffect(Unit) {
        val route = runCatching {
            auth.currentUser?.reload()?.await()
            val user = auth.currentUser
            if (user != null && user.isEmailVerified) {
                Destinations.HOME.route
            } else {
                auth.signOut()
                Destinations.LOGIN.route
            }
        }.getOrElse {
            Destinations.LOGIN.route
        }
        val slideSpec = tween<Float>(durationMillis = 450, easing = LinearOutSlowInEasing)
        val fadeSpec = tween<Float>(durationMillis = 250, easing = LinearOutSlowInEasing)
        val scaleSpec = tween<Float>(durationMillis = 250, easing = LinearOutSlowInEasing)

        launch { frame.animateTo(0f, slideSpec) }
        launch { lemon.animateTo(0f, slideSpec) }
        launch { alpha.animateTo(1f, fadeSpec) }
        launch { scale.animateTo(1f, scaleSpec) }

        delay(2000)
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.littlelemontxt),
            "Little lemon Splash logo",
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(translationX = frame.value)
        )
        Image(
            painter = painterResource(id = R.drawable.littlelemonimg),
            "Little lemon logo",
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    translationX = lemon.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                )
                .alpha(alpha.value)
        )
    }
}
