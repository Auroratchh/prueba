package com.example.exa.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exa.models.Formulario

@Database(entities = [Formulario::class], version = 1, exportSchema = false)
abstract class FormularioDatabase : RoomDatabase() {
    abstract fun formularioDao(): FormularioDatabaseDao
}