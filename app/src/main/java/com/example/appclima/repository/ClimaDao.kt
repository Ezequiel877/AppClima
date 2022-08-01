package com.example.appclima.repository

import androidx.room.*
import com.example.appclima.model.NotasEntity



@Dao
interface ClimaDao {
    @Query("SELECT * FROM memos")
     fun getNotas():List<NotasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveNotas(notas: NotasEntity)

    @Query("DELETE FROM memos WHERE id=:notas")
    suspend fun delete(notas: Int)


    @Query("UPDATE memos SET title=:title, text=:text WHERE id=:id ")
    suspend fun update(id: Int, title: String, text:String)


}