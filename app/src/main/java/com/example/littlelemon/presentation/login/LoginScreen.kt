package com.example.littlelemon.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Destinations
import com.example.littlelemon.presentation.auth.AuthEvent
import com.example.littlelemon.presentation.auth.AuthNavEvent
import com.example.littlelemon.presentation.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val scrollable = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.nav.collect { event ->
            when (event) {
                AuthNavEvent.GoHome -> {
                    navController.navigate(Destinations.HOME.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }
    LaunchedEffect(state.message) {
        state.message?.let { msg ->
            scope.launch {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollable)
            .padding(20.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.littlelemonlogo),
            contentDescription = "Little Lemon logo"
        )
        Spacer(Modifier.height(10.dp))

        // Username
        TextField(
            value = state.username,
            onValueChange = { viewModel.onEvent(AuthEvent.UsernameChange(it)) },
            label = { Text("Username") },
            singleLine = true
        )
        Spacer(Modifier.height(10.dp))

        // Email
        if (state.isSignUp) {
            TextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(AuthEvent.EmailChange(it)) },
                label = { Text("email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(10.dp))
        }

        // Password
        TextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(AuthEvent.PasswordChange(it)) },
            label = { Text("Password") },
            singleLine = true,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Visibility")
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(Modifier.height(10.dp))

        //Sign in / Sign Up Screen button
        TextButton(onClick = { viewModel.onEvent(AuthEvent.ToggleMode) }) {
            Text(if (state.isSignUp) "HAVE AN ACCOUNT? SIGN IN" else "SIGN UP")
        }
        Spacer(Modifier.height(10.dp))

        //Sign in / Create account button
        Button(
            onClick = {
                viewModel.onEvent(AuthEvent.Submit)
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF495E57)
            )
        ) { Text(if (state.isSignUp) "Create Account" else "Login") }
    }
}
