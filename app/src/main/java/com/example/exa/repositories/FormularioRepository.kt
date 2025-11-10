package com.example.exa.repositories

import com.example.exa.models.Formulario
import com.example.exa.room.FormularioDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FormularioRepository @Inject constructor(
    private val formularioDatabaseDao: FormularioDatabaseDao
) {
    fun getAllFormularios(): Flow<List<Formulario>> = formularioDatabaseDao.getFormularios()
        .flowOn(Dispatchers.IO)
        .conflate()

    suspend fun insertFormulario(formulario: Formulario) = formularioDatabaseDao.insert(formulario)
}