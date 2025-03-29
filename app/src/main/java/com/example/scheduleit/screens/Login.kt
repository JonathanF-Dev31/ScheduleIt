package com.example.scheduleit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scheduleit.R
import com.example.scheduleit.components.Header

/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun Login(navController: NavController) {
    Scaffold(
        topBar = { Header(name = "Student", isLoginScreen = true) }
    ) { paddingValues ->
        LoginBodyContent(Modifier.padding(paddingValues), navController)
    }
}

@Composable
fun LoginBodyContent(modifier: Modifier = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.scheduleit_logo),
            contentDescription = "Logo",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = 30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail", color = Color(0xFF54505A)) },
            placeholder = { Text("example@example.com") },
            textStyle = TextStyle(color = Color(0xFF54505A)),
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFF8F5BBD),
                unfocusedIndicatorColor = Color(0xFF89858E)
            ),
            modifier = Modifier
                .width(340.dp)
                .padding(horizontal = 16.dp, vertical = 5.dp)
        )

        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xFF54505A)) },
            textStyle = TextStyle(color = Color(0xFF54505A)),
            shape = RoundedCornerShape(4.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = "Toggle Password Visibility",
                        tint = Color(0xFF89858E)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFF8F5BBD),
                unfocusedIndicatorColor = Color(0xFF89858E)
            ),
            modifier = Modifier
                .width(340.dp)
                .padding(horizontal = 16.dp, vertical = 0.dp)
        )


        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .width(340.dp)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9E99BF),
                contentColor = Color.White
            )
        ) {
            Text("LOG IN")
        }

        Text(
            text = "Forgot password?",
            style = TextStyle(
                color = Color(0xFFC2C0CD),
                fontSize = 12.sp,
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
        )

        Spacer(modifier = Modifier.height(240.dp))

        HorizontalDivider(
            color = Color(0xFFC2C0CD),
            thickness = 1.dp,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            text = "@ScheduleIt",
            style = TextStyle(
                color = Color(0xFFC2C0CD),
                fontSize = 12.sp,
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.instagram_logo),
                contentDescription = "Instagram Logo",
                modifier = Modifier
                    .size(25.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.facebook_logo),
                contentDescription = "Facebook Logo",
                modifier = Modifier
                    .size(25.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.linkedin_logo),
                contentDescription = "LinkedIn Logo",
                modifier = Modifier
                    .size(25.dp)
            )
        }

    }
}

