package com.example.appclima.presentatation

import android.app.Application
import androidx.lifecycle.*
import com.example.appclima.model.Notas
import com.example.appclima.model.NotasEntity
import com.example.appclima.model.local.AppDataBase
import com.example.appclima.repository.NotasRepository
import com.example.appclima.utils.getStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(application: Application):AndroidViewModel(application) {

    val usedao: LiveData<List<Notas>>?=null
    private var repository:NotasRepository?=null
init {
    val usedao=AppDataBase.getDataBase(application).climadao()
    repository= NotasRepository(usedao)
}

    fun insertNotas(notas: NotasEntity){
        viewModelScope.launch(Dispatchers.IO) {
        repository!!.saveNotasMemo(notas)
        }
    }
}