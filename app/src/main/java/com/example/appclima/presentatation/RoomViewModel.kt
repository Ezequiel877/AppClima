package com.example.appclima.presentatation

import androidx.lifecycle.*
import com.example.appclima.model.NotasEntity
import com.example.appclima.repository.NotaRepository
import com.example.appclima.utils.getStatus
import kotlinx.coroutines.Dispatchers

class RoomViewModel(private val clima:NotaRepository):ViewModel() {

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


    class RoomFactory(private val repo: NotaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(NotaRepository::class.java).newInstance(repo)
        }
}
}