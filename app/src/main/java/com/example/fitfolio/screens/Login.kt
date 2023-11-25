package com.example.fitfolio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var isLoginSelected by rememberSaveable { mutableStateOf(true) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        // Top section with FitFolio text and login toggle buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "FitFolio",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LoginToggleButtons(
                onSignInClick = { isLoginSelected = true },
                onSignUpClick = { isLoginSelected = false }
            )
        }

        // Middle section with email and password fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(
                email = email,
                onEmailChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            if (!isLoginSelected) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Re-Type Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                Button(onClick = { /* Handle login click */ }) {
                    Text("Register")
                }
            }
            else {
                Button(onClick = { /* Handle login click */ }) {
                    Text("Login")
                }
            }

        }

    }
}


@Composable
fun LoginToggleButtons(onSignInClick: () -> Unit, onSignUpClick: () -> Unit) {
    var isLoginSelected by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                if (!isLoginSelected) {
                    isLoginSelected = true
                    onSignInClick()
                }
            },
            colors = if (isLoginSelected) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text("Sign in")
        }

        Button(
            onClick = {
                if (isLoginSelected) {
                    isLoginSelected = false
                    onSignUpClick()
                }
            },
            colors = if (!isLoginSelected) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text("Sign up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isEmailValid by remember { mutableStateOf(true) }
    var emailErrorText by remember { mutableStateOf("Enter a valid email") }

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            isEmailValid = isEmailValid(it)
        },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = !isEmailValid,
        modifier = modifier
            .fillMaxWidth(),
        trailingIcon = {
            if (!isEmailValid) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    )

    if (!isEmailValid) {
        Text(
            text = emailErrorText,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}
