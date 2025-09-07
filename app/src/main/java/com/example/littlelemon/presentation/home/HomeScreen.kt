package com.example.littlelemon.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.MenuListScreen
import com.example.littlelemon.components.OrderOnline
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.presentation.auth.AuthViewModel
import com.example.littlelemon.presentation.cart.CartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ContextCastToActivity")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val activity = (LocalContext.current as? Activity)
    var showProfile by remember { mutableStateOf(false) }
    val cartViewModel: CartViewModel = hiltViewModel()
    LaunchedEffect(Unit) { cartViewModel.syncMerge() }
    BackHandler(enabled = true) {
        if (showProfile) {
            showProfile = false
        } else {
            activity?.finish()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                iconLeft = R.drawable.pp,
                iconMiddle = R.drawable.littlelemonimgtxt_nobg,
                iconRight = R.drawable.cart,
                rightOnClick = {
                    navController.navigate(Destinations.CART.route) {
                        popUpTo(Destinations.HOME.route) {
                            inclusive = true
                        }
                    }
                },
                leftOnClick = { showProfile = true },
                navController = navController
            )
        },
        modifier = Modifier.windowInsetsPadding(insets = WindowInsets.statusBars)
    ) { paddingValues ->
        val displayName = viewModel.currentUser()?.displayName ?: "Guest"
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            UpperPanel(navController = navController)
            LowerPanel(navController = navController)
        }
        //Profile
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Profile(
                visible = showProfile,
                onDismiss = { showProfile = false },
                onLogout = {
                    viewModel.signOut()
                    navController.navigate(Destinations.LOGIN.route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
                userDisplayName = displayName,
                userEmail = viewModel.currentUser()?.email ?: "",
                onOrders = {
                    showProfile = false
                    navController.navigate(Destinations.ORDER.route)
                }
            )
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun UpperPanel(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val config = LocalConfiguration.current
    val screenSize = config.screenWidthDp.dp
    val verticalScroll = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(color = Color(0xFF495E57))
            .padding(10.dp)
            .verticalScroll(verticalScroll)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            //Little Lemon Text
            Text(
                "Little Lemon",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF5CE15),
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Location
            Text(
                "Chicago",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Serif,
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Description & Logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        letterSpacing = 0.7.sp
                    ),
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                Image(
                    painter = painterResource(id = R.drawable.upperpanelimage),
                    "Upper panel Image",
                    modifier = Modifier
                        .size(screenSize * 0.35f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            //Reserve a table Button
            Button(
                onClick = { navController.navigate(Destinations.RESERVE_TABLE.route) },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14)
                )
            ) {
                Text(
                    "Reserve a table",
                    color = Color.Black,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun LowerPanel(modifier: Modifier = Modifier, navController: NavController) {
    Column {
        OrderOnline()
        MenuListScreen(navController = navController)
    }
}

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    visible: Boolean,
    userDisplayName: String,
    userEmail: String,
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    onOrders: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it / 8 },
        exit = fadeOut() + slideOutVertically { it / 8 }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.4f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDismiss() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Account
                        Text(
                            text = "Account",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    //name
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Username: ",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = userDisplayName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif

                        )
                    }
                    //email
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Email: ",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = userEmail,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Serif
                        )
                    }
                    HorizontalDivider(
                        Modifier.padding(vertical = 4.dp),
                        DividerDefaults.Thickness,
                        DividerDefaults.color
                    )
                    //Spacer(Modifier.height(4.dp))
                    ProfileMenuItem(
                        title = "Your Orders",
                        subtitle = "View past orders and totals",
                        onClick = onOrders
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        //cancel
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        ) { Text("Cancel") }
                        //logout
                        Button(
                            onClick = onLogout,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) { Text("Logout") }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp)
    ) {
        //Order title
        Text(
            title, style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold
            )
        )
        //Order subtitle
        if (subtitle != null) {
            Text(
                subtitle, style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}



