package com.example.littlelemon.presentation.reservation.table_reserved

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.presentation.reservation.reservationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TableReservedScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel = reservationViewModel(navController = navController)
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                //iconLeft = R.drawable.back_button,
                modifier = Modifier
                    .background(color = Color.Black)
                    .clip(RoundedCornerShape(100))
                    .padding(10.dp),
                iconMiddle = R.drawable.littlelemonimgtxt_nobg,
                iconRight = R.drawable.cart,
                rightOnClick = {
                    navController.navigate(Destinations.CART.route)
                },
                navController = navController
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate(Destinations.HOME.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                )
            ) {
                Text(
                    "Home",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Table reserved image
            Image(
                painter = painterResource(id = R.drawable.reserved),
                "Table Reserved image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )
            Spacer(modifier = Modifier.height(35.dp))
            //table reserved text
            Text(
                "Table Reserved",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            //Name
            Text(
                state.name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(35.dp))
            //description
            Text(
                "Your Reservation has been made successfully!",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                ),
            )
            Spacer(modifier = Modifier.height(35.dp))
            //confirmation text
            Text(
                "You will get a confirmation via email on",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            //Email
            Text(
                state.email,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif
                )
            )
        }
    }
}
