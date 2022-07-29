package com.example.appclima.utils

import android.app.Application
import androidx.room.Room
import com.example.appclima.model.local.AppDataBase

class App:Application() {
    val room=Room.databaseBuilder(this, AppDataBase::class.java,"Notas").build()
}