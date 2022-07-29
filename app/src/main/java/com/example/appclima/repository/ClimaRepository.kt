package com.example.appclima.repository

import androidx.lifecycle.LiveData
import com.example.appclima.model.NotasEntity

interface ClimaRepository {
    suspend fun getNotas(): LiveData<List<NotasEntity>>
    fun saveNotas(notas: NotasEntity)
}