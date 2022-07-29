package com.example.appclima.repository

import androidx.lifecycle.LiveData
import com.example.appclima.model.Notas
import com.example.appclima.model.NotasEntity

class NotasRepository (private val dao: ClimaDao){


    fun saveNotasMemo(notas: NotasEntity){
        dao.saveNotas(notas)
    }
}