package com.example.littlelemon.presentation.cart

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.util.rememberRootCartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val cartViewModel = rememberRootCartViewModel(navController)
    val items by cartViewModel.items.collectAsState()
    val context = LocalContext.current
    fun money(v: Double) = String.format(java.util.Locale.US, "%.2f", v)
    Scaffold(
        topBar = {
            TopAppBar(
                //iconLeft = R.drawable.back_button,
                modifier = Modifier
                    .background(color = Color.Black)
                    .padding(10.dp),
                iconMiddle = R.drawable.littlelemonimgtxt,
                iconRight = R.drawable.cart,
//                leftOnClick = {
//                    navController.popBackStack()
//                },
                navController = navController
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    cartViewModel.checkout()
                    Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = if (items.isEmpty()) false else true,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                )
            ) {
                Text(
                    "Checkout",
                    style = TextStyle(
                        color = Color.Black,
                        letterSpacing = 0.7.sp,
                        fontFamily = FontFamily.Serif
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
            ) {
                //Order Summary Text
                Text(
                    "Order Summary",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.3.sp,
                        fontFamily = FontFamily.Serif
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                //Items Text
                Text(
                    "Items",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .padding(7.dp),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif

                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                //Items added to cart
                CartList(
                    items = items,
                    onRemove = { id -> cartViewModel.remove(id) },
                    onClick = { id -> navController.navigate(Destinations.DISH_DETAIL.getDishId(id)) }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                verticalArrangement = Arrangement.Bottom
            ) {
                //Subtotal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Subtotal",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        money(cartViewModel.subtotal()),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                //Delivery
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Delivery",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        "${cartViewModel.deliveryFee}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                //Services
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Services",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        "${cartViewModel.serviceFee}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                //Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        "$${money(cartViewModel.total())}",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}