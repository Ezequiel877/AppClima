package com.example.appclima.repository

import androidx.room.*
import com.example.appclima.model.NotasEntity

@Dao
interface ClimaDao {
    @Query("SELECT * FROM memos")
     fun getNotas():List<NotasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveNotas(notas: NotasEntity)

    @Delete
    fun delete(notas: NotasEntity)
    @Update
    fun update(notas: NotasEntity)


}