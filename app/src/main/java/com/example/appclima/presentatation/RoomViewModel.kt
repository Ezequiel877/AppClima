package com.example.appclima.presentatation

import androidx.lifecycle.*
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.LocaDataSource
import com.example.appclima.repository.ClimaRepository
import com.example.appclima.utils.getStatus
import kotlinx.coroutines.Dispatchers
import java.net.IDN

class RoomViewModel(private val clima:ClimaRepository):ViewModel() {

    fun save(notas: NotasEntity) = liveData(Dispatchers.IO) {
        emit(getStatus.Loading())
        try {
            emit(getStatus.Succes(clima.saveNotas(notas)))
        } catch (e: Exception) {
            emit(getStatus.Failure(e))
        }
    }

    fun getMemos() = liveData(Dispatchers.IO) {
        emit(getStatus.Loading())
        try {
            emit(getStatus.Succes(clima.getNotas()))
        } catch (e: Exception) {
            emit(getStatus.Failure(e))
        }
    }

    fun delete(id:Int) = liveData(Dispatchers.IO) {
        emit(getStatus.Loading())
        try {
            emit(getStatus.Succes(clima.delete(id)))
        } catch (e: Exception) {
            emit(getStatus.Failure(e))
        }
    }
    fun update(id: Int,title:String, text:String) = liveData(Dispatchers.IO) {
        emit(getStatus.Loading())
        try {
            emit(getStatus.Succes(clima.update(id, title, text)))
        } catch (e: Exception) {
            emit(getStatus.Failure(e))
        }
    }


    class RoomFactory(private val repo: ClimaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(ClimaRepository::class.java).newInstance(repo)
        }
}
}