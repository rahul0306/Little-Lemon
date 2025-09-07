package com.example.littlelemon.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.littlelemon.navigation.Destinations

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes iconLeft: Int? = null,
    @DrawableRes iconMiddle: Int? = null,
    @DrawableRes iconRight: Int? = null,
    leftOnClick: (() -> Unit)? = null,
    rightOnClick: (() -> Unit)? = null,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(insets = WindowInsets.statusBars),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { leftOnClick?.let { it() } }) {
            iconLeft?.let {
                Image(
                    painter = painterResource(id = it),
                    "Left Icon",
                    modifier = modifier
                )
            }
        }
        iconMiddle?.let {
            Image(
                painter = painterResource(id = it),
                "Middle Icon",
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .clickable {
                        navController.navigate(
                            Destinations.HOME.route
                        ) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
            )
        }
        IconButton(onClick = { rightOnClick?.let { it() } }) {
            iconRight?.let {
                Image(
                    painter = painterResource(id = it),
                    "Right Icon"
                )
            }
        }
    }
}