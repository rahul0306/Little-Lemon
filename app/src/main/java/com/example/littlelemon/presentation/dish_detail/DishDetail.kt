package com.example.littlelemon.presentation.dish_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.domain.CartItem
import com.example.littlelemon.data.dish.DishRepository
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.util.rememberRootCartViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun DishDetail(
    modifier: Modifier = Modifier,
    id: Int,
    navController: NavController
) {
    val cartViewModel = rememberRootCartViewModel(navController)
    val dish = requireNotNull(DishRepository.getDish(id))
    var counter by remember { mutableStateOf(1) }
    val price = dish.price.toDoubleOrNull() ?: 0.0
    val totalLabel by remember(counter, price) {
        derivedStateOf { (counter * price) }
    }
    val items by cartViewModel.items.collectAsState()
    LaunchedEffect(items, dish.id) {
        val existing = items.find { it.dishId == dish.id }
        counter = (existing?.quantity ?: 1).coerceAtLeast(1)
    }

    fun money(v: Double) = String.format(Locale.US, "%.2f", v)
    Scaffold(
        topBar = {
            TopAppBar(
                iconLeft = R.drawable.back_button,
                modifier = Modifier
                    .background(color = Color.Black)
                    .clip(RoundedCornerShape(100))
                    .padding(10.dp),
                iconMiddle = R.drawable.littlelemonimgtxt_nobg,
                iconRight = R.drawable.cart,
                leftOnClick = {
                    navController.navigate(Destinations.HOME.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                rightOnClick = {
                    navController.navigate(Destinations.CART.route)
                },
                navController = navController
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Counter buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Counter Minus button
                    TextButton(onClick = { counter = (counter - 1).coerceAtLeast(1) }) {
                        Text(
                            "-",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                color = Color.Black
                            )
                        )
                    }
                    Text(
                        "$counter", style = TextStyle(
                            fontSize = MaterialTheme.typography.displaySmall.fontSize
                        )
                    )
                    //Counter plus button
                    TextButton(onClick = { counter++ }) {
                        Text(
                            "+", style = TextStyle(
                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                color = Color.Black

                            )
                        )
                    }
                }
                //Add to cart button
                Button(
                    onClick = {
                        val existing = items.find { it.dishId == dish.id }
                        if (existing == null) {
                            cartViewModel.addOrUpdate(
                                CartItem(
                                    dishId = dish.id,
                                    title = dish.title,
                                    price = price,
                                    quantity = counter
                                )
                            )
                        } else {
                            cartViewModel.updateQuantity(dish.id, counter)
                        }
                        navController.navigate(Destinations.CART.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF4CE14)
                    )
                ) {
                    Text(
                        "Add for $${money(totalLabel)}",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            //Dish Image
            Image(
                painter = painterResource(id = dish.image),
                "Dish Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Dish Name
                    Text(
                        dish.title.uppercase(Locale.getDefault()),
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontFamily = FontFamily.Serif
                        )
                    )
                    //Dish Price
                    Text(
                        "$${dish.price}",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontFamily = FontFamily.Serif
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                //Dish description
                Text(
                    dish.description, style = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        letterSpacing = 0.8.sp,
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}