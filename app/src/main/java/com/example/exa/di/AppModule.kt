package com.example.exa.di

import android.content.Context
import androidx.room.Room
import com.example.exa.room.FormularioDatabase
import com.example.exa.room.FormularioDatabaseDao
import com.example.exa.ui.theme.ThemePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFormularioDao(formularioDatabase: FormularioDatabase): FormularioDatabaseDao {
        return formularioDatabase.formularioDao()
    }

    @Singleton
    @Provides
    fun providesFormularioDatabase(@ApplicationContext context: Context): FormularioDatabase {
        return Room.databaseBuilder(
            context,
            FormularioDatabase::class.java,
            "formulario_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Singleton
    @Provides
    fun providesThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context)
    }
}