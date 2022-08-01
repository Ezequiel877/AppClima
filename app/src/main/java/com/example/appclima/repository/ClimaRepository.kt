package com.example.appclima.repository

import androidx.lifecycle.LiveData
import com.example.appclima.model.NotasEntity
import com.example.appclima.utils.getStatus

interface ClimaRepository {

    //trae el listado de notas
    suspend fun getNotas(): List<NotasEntity>

    //guarda una nota
    suspend fun saveNotas(notas: NotasEntity)
    //elimina una nota
    suspend fun delete(notas: Int)
    //actualiza una nota
    suspend fun update(id: Int,title:String, text:String)
}