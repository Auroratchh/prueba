package com.example.exa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exa.models.Formulario
import com.example.exa.repositories.FormularioRepository
import com.example.exa.ui.theme.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themePrefs: ThemePreferences,
    private val repository: FormularioRepository
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _formularios = MutableStateFlow<List<Formulario>>(emptyList())
    val formularios = _formularios.asStateFlow()

    init {
        themePrefs.isDarkMode.onEach {
            _isDarkMode.value = it
        }.launchIn(viewModelScope)

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFormularios().collect {
                _formularios.value = it
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themePrefs.toggleTheme(false)
        }
    }

    fun guardarFormulario(nombre: String, apellido: String, mensaje: String) {
        viewModelScope.launch {
            val formulario = Formulario(
                nombre = nombre,
                apellido = apellido,
                mensaje = mensaje
            )
            repository.insertFormulario(formulario)
        }
    }
}