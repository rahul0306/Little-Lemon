package com.example.littlelemon.presentation.reservation.reserve_table_checkout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.presentation.reservation.reservationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReserveTableCheckoutScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel = reservationViewModel(navController = navController)
    val state by viewModel.state.collectAsState()
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
                    navController.popBackStack()
                },
                rightOnClick = {
                    navController.navigate(Destinations.CART.route)
                },
                navController = navController
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate(Destinations.TABLE_RESERVED.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                )
            ) {
                Text(
                    "Reserve a table",
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
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                //Reserve a table text
                Text(
                    "Reserve a Table",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Serif
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Dine in date
                    Text(
                        "Dine in date :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(state.date)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //time
                    Text(
                        "Time :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(state.time)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //guest count
                    Text(
                        "Guest count :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(state.guestCount)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //occasion
                    Text(
                        "Occasion :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(state.occasion)
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Name
                    Text(
                        "Name :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        value = state.name,
                        onValueChange = viewModel::updateName,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Contact Number
                    Text(
                        "Contact No :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        value = state.contact,
                        onValueChange = viewModel::updateContact,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Email
                    Text(
                        "Email :",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        ),
                        modifier = Modifier.fillMaxWidth(0.45f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        value = state.email,
                        onValueChange = viewModel::updateEmail,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }
        }
    }
}