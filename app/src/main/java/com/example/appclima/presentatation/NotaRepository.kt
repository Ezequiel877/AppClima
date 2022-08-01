package com.example.appclima.presentatation

import androidx.lifecycle.LiveData
import com.example.appclima.model.NotasEntity
import com.example.appclima.utils.getStatus

interface NotaRepository {
    //trae el listao de notas
    suspend fun getNotas(): List<NotasEntity>

    //guarda una nota
    suspend fun saveNotas(notas: NotasEntity)
    //elimina una nota
    suspend fun delete(notas: Int)
    //actualiza una nota
    suspend fun update(id: Int,title:String, text:String)
}