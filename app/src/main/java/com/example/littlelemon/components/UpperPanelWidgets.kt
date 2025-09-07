package com.example.littlelemon.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchExpand(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val cfg = LocalConfiguration.current
    val screenWidth = cfg.screenWidthDp.dp
    val circle = remember(screenWidth) {
        (screenWidth * 0.10f).coerceIn(44.dp, 56.dp)
    }

    val minFrac = remember(screenWidth, circle) {
        (circle / screenWidth).coerceIn(0f, 1f)
    }

    val widthFrac by animateFloatAsState(
        targetValue = if (expanded) 1f else minFrac,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )

    val height = circle
    val shape = if (expanded) RoundedCornerShape(height / 2) else CircleShape

    Surface(
        shape = shape,
        tonalElevation = 1.dp,
        modifier = modifier
            .fillMaxWidth(widthFrac)
            .height(height)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            IconButton(
                onClick = { expanded = true },
                enabled = !expanded,
                modifier = Modifier
                    .size(circle * 0.7f)
                    .clip(CircleShape)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = "Search")
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(tween(150)) + expandHorizontally(
                    animationSpec = tween(300, easing = LinearOutSlowInEasing),
                    expandFrom = Alignment.Start
                ),
                exit = fadeOut(tween(150)) + shrinkHorizontally(
                    animationSpec = tween(150, easing = FastOutLinearInEasing),
                    shrinkTowards = Alignment.Start
                ),
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { /* UI-only */ }),
                    decorationBox = { inner ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(Modifier.weight(1f)) {
                                if (query.isEmpty()) {
                                    Text("Search dishes…")
                                }
                                inner()
                            }
                            IconButton(onClick = { query = ""; expanded = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }
                    }
                )
            }
        }
    }
}