package com.example.exa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exa.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val formularios by viewModel.formularios.collectAsState()

            MaterialTheme(
                colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
            ) {
                Surface {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            Dashboard(
                                onNavigateToTheme = { navController.navigate("theme") },
                                onNavigateToForm = { navController.navigate("form") }
                            )
                        }

                        composable("theme") {
                            Themeac(
                                isDarkMode = isDarkMode,
                                onToggleTheme = { viewModel.toggleTheme() },
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable("form") {
                            FormularioScreen(
                                formularios = formularios,
                                onGuardar = { nombre, apellido, mensaje ->
                                    viewModel.guardarFormulario(nombre, apellido, mensaje)
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}