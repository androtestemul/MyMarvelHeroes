package com.apska.mymarvelheroes.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apska.mymarvelheroes.data.model.DatabaseHero

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes")
    fun getHeroes() : LiveData<List<DatabaseHero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(heroes: List<DatabaseHero>)
}