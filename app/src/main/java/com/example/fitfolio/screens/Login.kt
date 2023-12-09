package com.example.fitfolio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.fitfolio.data.Repository
import com.example.fitfolio.data.Routine
import com.example.fitfolio.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

//Contains signing in and signing up forms
@Composable
fun LoginScreen(
    navController: NavController,
    onLogin: (String, String) -> LiveData<Boolean>,
    onRegister: (String, String) -> LiveData<Boolean>,
    repository: Repository) {
    var isLoginSelected by rememberSaveable { mutableStateOf(true) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var isEmailValid by rememberSaveable { mutableStateOf(false) }
    var isPasswordValid by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordValid by rememberSaveable { mutableStateOf(false) }

    var showLoginError by remember { mutableStateOf(false) }
    var showRegisterError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            // Top section with FitFolio text and login toggle buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailField(
                    email = email,
                    onEmailChange = {email = it},
                    isEmailValid = isEmailValid,
                    setIsEmailValid = {isEmailValid = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                if (!isLoginSelected) {
                    RegisterPasswordFields(
                        onPasswordChange = {password = it},
                        onConfirmPasswordChange = {confirmPassword = it},
                        isPasswordValid = isPasswordValid,
                        setIsPasswordValid = {isPasswordValid = it},
                        setIsConfirmPasswordValid = {isConfirmPasswordValid = it},
                        password = password,
                        confirmPassword = confirmPassword,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    // Error message
                    if (showRegisterError) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { signUser(navController, onRegister, email, password, repository, { message ->
                            showRegisterError = true
                            errorMessage = message
                        }, true) },
                        enabled = allFieldsValid(isLoginSelected, isEmailValid, isPasswordValid, isConfirmPasswordValid)
                    ) {
                        Text("Register")
                    }
                }
                else {
                    LoginPasswordField(
                        onPasswordChange = {password = it},
                        password = password,
                        isPasswordValid = isPasswordValid,
                        setIsPasswordValid = {isPasswordValid = it},
                        modifier = Modifier
                    )

                    // Error message
                    if (showLoginError) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { signUser(navController, onLogin, email, password, repository, { message ->
                            showLoginError = true
                            errorMessage = message
                        }) },
                        enabled = allFieldsValid(isLoginSelected, isEmailValid, isPasswordValid, isConfirmPasswordValid)
                    ) {
                        Text("Login")
                    }
                }
            }
        }

    }
}

fun signUser(
    navController: NavController,
    signInOrUp: (String, String) -> LiveData<Boolean>,
    email :String,
    password: String,
    repository: Repository,
    onError: (String) -> Unit,
    createUser: Boolean = false
) {
    signInOrUp(email, password).observeForever { success ->
        if (success) {
            // Registration successful, navigate to another screen or perform other actions.
            var user = User(email, password)
            CoroutineScope(Dispatchers.IO).launch {
                if(createUser){
                    // List of deferred jobs for adding routines
                    val jobs = getMockRoutines().map { routine ->
                        async {
                            repository.addRoutine(routine)
                        }
                    }

                    // Wait for all routines to be added
                    jobs.awaitAll()
                }

            }
            navController.navigate("RoutinesOverview")

        } else {
            if(createUser){
                onError("The email has already been used. Sign in or use a different email")
            }
            else{
                onError("Invalid login credentials. Please try again.")
            }
        }
    }
}

fun getMockRoutines(): List<Routine> {
    return listOf<Routine>(
        Routine(Routine.generateUniqueId(), "Chest Day", null),
        Routine(Routine.generateUniqueId(),"Back Day", null),
        Routine(Routine.generateUniqueId(),"Leg Day", null)
    )
}

//Validates all user input fields
fun allFieldsValid (
    isLoginSelected: Boolean,
    isEmailValid: Boolean,
    isPasswordValid: Boolean,
    isConfirmPasswordValid: Boolean
): Boolean {
    if(isLoginSelected) {
        return isEmailValid && isPasswordValid;
    }
    else {
        return isEmailValid && isPasswordValid && isConfirmPasswordValid
    }
}

//Buttons for toggling between sign in form and sign up form
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

//User input field for email with validation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailValid: Boolean,
    setIsEmailValid: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var emailErrorText by remember { mutableStateOf("Enter a valid email") }

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            setIsEmailValid(isEmailValid(it))
        },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = !isEmailValid && email != "",
        modifier = modifier
            .fillMaxWidth(),
        trailingIcon = {
            if (!isEmailValid && email != "") {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    )

    if (!isEmailValid && email != "") {
        Text(
            text = emailErrorText,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

//Validates an email using regex
fun isEmailValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"//Regex retrieved from ChatGPT
    return email.matches(emailPattern.toRegex())
}

//User input password field for signing in with validation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPasswordField(
    onPasswordChange: (String) -> Unit,
    password: String,
    isPasswordValid: Boolean,
    setIsPasswordValid: (Boolean) -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = {
                onPasswordChange(it)
                setIsPasswordValid(it.isNotEmpty()) // Validate at least 1 character
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )

    }
}

//User input password fields (password, re-type password) for signing up with validation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPasswordFields(
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    isPasswordValid: Boolean,
    setIsPasswordValid: (Boolean) -> Unit,
    setIsConfirmPasswordValid: (Boolean) -> Unit,
    password: String,
    confirmPassword: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = {
                onPasswordChange(it)
                setIsPasswordValid(it.length >= 8)
            },
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


        if (password.length < 8 && password != "") {
            Text(
                text = "Password must be at least 8 characters",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                onConfirmPasswordChange(it)
                setIsConfirmPasswordValid(password == it)
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError = password != confirmPassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            trailingIcon = {
                if (password != confirmPassword) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        if (password != confirmPassword) {
            Text(
                text = "Passwords do not match",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}
