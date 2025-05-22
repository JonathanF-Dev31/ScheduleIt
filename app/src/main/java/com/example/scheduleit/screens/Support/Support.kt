package com.example.scheduleit.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.LoadScreen
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.runtime.*

@Composable
fun Support(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(500)
        isLoading = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LoadScreen(modifier = Modifier.fillMaxSize())
        } else {
            Scaffold(
                topBar = { Header() },
                bottomBar = { BottomNavBar(navController) }
            ) { paddingValues ->
                ContactBodyContent(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun ContactBodyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8473A8)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Contáctanos", color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(32.dp))
        ContactRow(
            icon = Icons.Filled.Call,
            label = "Teléfono",
            info = "+57 234 567 8901",
            link = "tel:+15551234567"
        )
        Spacer(modifier = Modifier.height(24.dp))
        ContactRow(
            icon = Icons.Filled.Email,
            label = "Correo",
            info = "academiaIdiomas@ejemplo.com",
            link = "mailto:contacto@ejemplo.com"
        )
        Spacer(modifier = Modifier.height(24.dp))
        ContactRow(
            icon = Icons.Filled.Chat,
            label = "WhatsApp",
            info = "+1 555 765 4321",
            link = "https://wa.link/a6ernc"
        )
    }
}

@Composable
fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    info: String,
    link: String? = null
) {
    val context = LocalContext.current
    val labelColor = Color(0xFF8473A8)
    val infoColor = Color(0xFF858181)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = labelColor,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = labelColor
            )
            Text(
                text = info,
                fontSize = 16.sp,
                color = infoColor,
                modifier = if (link != null) {
                    Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(intent)
                    }
                } else Modifier
            )
        }
    }
}