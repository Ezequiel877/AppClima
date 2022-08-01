package com.example.appclima.model.local

import com.example.appclima.model.NotasEntity
import com.example.appclima.repository.NotasDao

/*implementacion de los metodos de persistencia */

class LocalDataSource(private val dao: NotasDao) {


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