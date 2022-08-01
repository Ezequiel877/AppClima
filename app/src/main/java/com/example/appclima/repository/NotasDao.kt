package com.example.appclima.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appclima.model.NotasEntity


@Dao
interface NotasDao {
    @Query("SELECT * FROM memos")
    suspend fun getNotas():List<NotasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotas(notas: NotasEntity)

    @Query("DELETE FROM memos WHERE id=:notas")
    suspend fun delete(notas: Int)


    @Query("UPDATE memos SET title=:title, text=:text WHERE id=:id ")
    suspend fun update(id: Int, title: String, text:String)


}