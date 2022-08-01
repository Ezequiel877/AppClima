package com.example.appclima.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appclima.model.NotasEntity


@Database(entities = [NotasEntity::class], version = 1)
abstract class AppDataBase:RoomDatabase() {

    abstract fun climadao():ClimaDao

    companion object{
        private var INSTANCE: AppDataBase?=null
         fun getDataBase(context:Context): AppDataBase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "clima_datalocal"
            ).build()
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE =null
        }
    }
}