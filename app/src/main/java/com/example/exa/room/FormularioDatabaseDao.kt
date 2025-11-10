package com.example.exa.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exa.models.Formulario
import kotlinx.coroutines.flow.Flow

@Dao
interface FormularioDatabaseDao {
    @Query("SELECT * FROM formularios")
    fun getFormularios(): Flow<List<Formulario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formulario: Formulario)
}