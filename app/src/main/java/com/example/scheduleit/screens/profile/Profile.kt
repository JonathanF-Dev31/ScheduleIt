package com.example.scheduleit.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scheduleit.R
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import com.example.scheduleit.models.User
import com.example.scheduleit.components.LoadScreen

@Composable
fun Profile(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()

    val isLoading = viewModel.isLoading.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LoadScreen(modifier = Modifier.fillMaxSize())
        }

        Scaffold(
            topBar = { if (!isLoading) Header() },
            bottomBar = { if (!isLoading) BottomNavBar(navController) }
        ) { paddingValues ->
            ProfileBodyContent(modifier = Modifier.padding(paddingValues), navController)
        }
    }
}



@Composable
fun ProfileBodyContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val user: State<User?> = viewModel.user.collectAsState()
    val isLoading: Boolean = viewModel.isLoading.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            LoadScreen()
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8473A8)),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Profile", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(BorderStroke(2.dp, Color(0xFF74708C)), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.photo_camera),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(100.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF74708C)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_a_photo),
                        contentDescription = "Change Profile Picture",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileInfoItem(
                icon = R.drawable.profile,
                label = "Name",
                value = user.value?.name.toString()
            )
            ProfileInfoItem(
                icon = R.drawable.mail,
                label = "E-mail",
                value = user.value?.email.toString()
            )
            ProfileInfoItem(
                icon = R.drawable.phone,
                label = "Phone",
                value = user.value?.phone.toString()
            )
            ProfileInfoItem(
                icon = R.drawable.school,
                label = "Level",
                value = user.value?.currentlyLevel.toString()
            )
        }
    }
}

@Composable
fun ProfileInfoItem(icon: Int, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666)
            )
        }
    }
}