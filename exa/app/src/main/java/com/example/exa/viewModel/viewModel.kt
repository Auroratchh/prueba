package com.example.exa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exa.ui.theme.Formulario
import com.example.exa.ui.theme.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val themePrefs: ThemePreferences
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _formularios = MutableStateFlow<List<Formulario>>(emptyList())
    val formularios: StateFlow<List<Formulario>> = _formularios

    init {
        viewModelScope.launch {
            themePrefs.isDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }

        viewModelScope.launch {
            themePrefs.formularios.collect { lista ->
                _formularios.value = lista
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newValue = !_isDarkMode.value
            themePrefs.toggleTheme(newValue)
        }
    }

    fun guardarFormulario(nombre: String, apellido: String, mensaje: String) {
        viewModelScope.launch {
            themePrefs.guardarFormulario(nombre, apellido, mensaje)
        }
    }
}