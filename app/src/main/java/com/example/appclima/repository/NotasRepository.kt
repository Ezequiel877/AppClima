package com.example.appclima.repository

import androidx.lifecycle.LiveData
import com.example.appclima.model.Notas
import com.example.appclima.model.NotasEntity

class NotasRepository (private val dao: ClimaDao){


    suspend fun saveNotasMemo(notas: NotasEntity) {
        dao.saveNotas(notas)
    }

    suspend fun getNotas(): List<NotasEntity> {
        return dao.getNotas()
    }

    suspend fun delete(id: Int) {
        dao.delete(id)
    }

    suspend fun update(id: Int,title:String, text:String) {
        dao.update(id, title, text)
    }
}