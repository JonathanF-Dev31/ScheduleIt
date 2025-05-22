package com.example.scheduleit.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import coil3.compose.rememberAsyncImagePainter
import com.example.scheduleit.R
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.scheduleit.components.LoadScreen
@Composable
fun Profile(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(700)
        isLoading = false
    }

    if (isLoading) {
        LoadScreen(modifier = Modifier.fillMaxSize())
    } else {
        Scaffold(
            topBar = {
                Header()
            },
            bottomBar = {
                BottomNavBar(navController)
            }
        ) { paddingValues ->
            ProfileBodyContent(Modifier.padding(paddingValues), navController)
        }
    }
}



@Composable
fun ProfileBodyContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {


    val user by viewModel.user.collectAsState()
    val uriPhoto = user?.photo?.let { Uri.parse(it) }

    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val internalUri = viewModel.saveImageToInternalStorage(context, it)
            internalUri?.let { savedUri ->
                user?.email?.let { email ->
                    viewModel.updateUserPhoto(email, savedUri.toString())
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                if (uriPhoto != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = uriPhoto),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(170.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.photo_camera),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF74708C))
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
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

        ProfileInfoItem(icon = R.drawable.profile, label = "Name", value = listOfNotNull(user?.name, user?.lastName).joinToString(" "))
        ProfileInfoItem(icon = R.drawable.mail, label = "E-mail", value = user?.email ?: "")
        ProfileInfoItem(icon = R.drawable.phone, label = "Phone", value = user?.phone ?: "")
        ProfileInfoItem(icon = R.drawable.school, label = "Level", value = user?.currentlyLevel ?: "")
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
            Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF666666))
        }
    }
}