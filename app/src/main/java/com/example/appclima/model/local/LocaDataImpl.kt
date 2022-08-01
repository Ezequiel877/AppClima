package com.example.appclima.model.local

import com.example.appclima.model.NotasEntity
import com.example.appclima.repository.NotaRepository

class LocaDataImpl(private val room: LocalDataSource) : NotaRepository {

    /* inyeccion de los metodos de persistencia a la base de datos con la interface del viewmodel*/
    override suspend fun getNotas(): List<NotasEntity> {
        return room.getNotas()
    }

    override suspend fun saveNotas(notas: NotasEntity) = room.saveNotasMemo(notas)

    override suspend fun delete(notas: Int) =room.delete(notas)

    override suspend fun update(id: Int,title:String, text:String)=room.update(id, title, text)


}