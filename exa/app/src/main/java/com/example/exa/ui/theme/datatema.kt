package com.example.exa.ui.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class Formulario(
    val id: String,
    val nombre: String,
    val apellido: String,
    val mensaje: String,
    val timestamp: Long = System.currentTimeMillis()
)

class ThemePreferences(private val context: Context) {

    private val gson = Gson()
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val FORMULARIOS_KEY = stringPreferencesKey("formularios")

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    suspend fun toggleTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDark
        }
    }
    val formularios: Flow<List<Formulario>> = context.dataStore.data.map { preferences ->
        val json = preferences[FORMULARIOS_KEY] ?: "[]"
        val type = object : TypeToken<List<Formulario>>() {}.type
        try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun guardarFormulario(nombre: String, apellido: String, mensaje: String) {
        context.dataStore.edit { preferences ->
            val json = preferences[FORMULARIOS_KEY] ?: "[]"
            val type = object : TypeToken<MutableList<Formulario>>() {}.type
            val lista: MutableList<Formulario> = try {
                gson.fromJson(json, type) ?: mutableListOf()
            } catch (e: Exception) {
                mutableListOf()
            }

        val nuevoFormulario = Formulario(
            id = System.currentTimeMillis().toString(),
            nombre = nombre,
            apellido = apellido,
            mensaje = mensaje
        )

        lista.add(0, nuevoFormulario)
        preferences[FORMULARIOS_KEY] = gson.toJson(lista)
    }
}
}