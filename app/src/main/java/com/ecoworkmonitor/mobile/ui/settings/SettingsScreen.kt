package com.ecoworkmonitor.mobile.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ecoworkmonitor.mobile.ui.components.ExpandableCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val tempLimit by viewModel.tempThreshold.collectAsState()
    val noiseLimit by viewModel.noiseThreshold.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()

    // Handle logout success
    androidx.compose.runtime.LaunchedEffect(logoutState) {
        if (logoutState is LogoutState.Success) {
            viewModel.resetLogoutState()
            onLogout()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Aparência Section
            ExpandableCard(
                title = "Aparência",
                icon = Icons.Default.Palette,
                initiallyExpanded = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tema Escuro",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { viewModel.toggleTheme(it) }
                    )
                }
            }

            // Alertas Section
            ExpandableCard(
                title = "Alertas",
                icon = Icons.Default.Notifications,
                initiallyExpanded = false
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Temperature Threshold
                    Column {
                        Text(
                            text = "Limite de Temperatura: ${tempLimit.toInt()}°C",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = tempLimit,
                            onValueChange = { viewModel.updateTempThreshold(it) },
                            valueRange = 15f..35f,
                            steps = 20
                        )
                    }

                    // Noise Threshold
                    Column {
                        Text(
                            text = "Limite de Ruído: ${noiseLimit.toInt()} dB",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = noiseLimit,
                            onValueChange = { viewModel.updateNoiseThreshold(it) },
                            valueRange = 30f..90f,
                            steps = 60
                        )
                    }
                }
            }

            // Conta Section
            ExpandableCard(
                title = "Conta",
                icon = Icons.Default.AccountCircle,
                initiallyExpanded = false
            ) {
                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    enabled = logoutState !is LogoutState.Loading
                ) {
                    Text(
                        text = if (logoutState is LogoutState.Loading) "Saindo..." else "Sair",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
