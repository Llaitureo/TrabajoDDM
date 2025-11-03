package com.pasteleria.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pasteleria.ui.theme.HuertohogarTheme
import com.pasteleria.ui.theme.Marron
import com.pasteleria.ui.theme.rosado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    username: String
) {
    val userState by profileViewModel.user.collectAsState()
    
    LaunchedEffect(username) {
        profileViewModel.loadUser(username)
    }
    
    HuertohogarTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Perfil") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = rosado,
                        titleContentColor = Marron
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                userState?.let { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Información Personal",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Marron
                            )
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            ProfileInfoRow(label = "Nombre de Usuario:", value = user.username)
                            
                            ProfileInfoRow(
                                label = "Fecha de Nacimiento:", 
                                value = user.birthDate ?: "No especificada"
                            )
                            
                            ProfileInfoRow(
                                label = "Código Promocional:", 
                                value = user.discountCode ?: "Ninguno"
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Descuentos Activos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Marron
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            if (user.hasFiftyPercentDiscount) {
                                DiscountChip("50% - Mayor de 50 años")
                            }
                            
                            if (user.hasTenPercentDiscount) {
                                DiscountChip("10% - Código FELICES50")
                            }
                            
                            if (user.hasFreeCakeOnBirthday) {
                                DiscountChip("Torta gratis en cumpleaños")
                            }
                            
                            if (!user.hasFiftyPercentDiscount && !user.hasTenPercentDiscount && !user.hasFreeCakeOnBirthday) {
                                Text(
                                    text = "No tienes descuentos activos",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } ?: run {
                    Text(
                        text = "Cargando información...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Marron
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun DiscountChip(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = rosado),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = "✓ $text",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Marron,
            fontWeight = FontWeight.Medium
        )
    }
}