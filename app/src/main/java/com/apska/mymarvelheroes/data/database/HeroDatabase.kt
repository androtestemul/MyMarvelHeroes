package com.apska.mymarvelheroes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apska.mymarvelheroes.data.model.DatabaseHero
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [DatabaseHero::class], version = 1)
abstract class HeroDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "heroes"

        @Volatile
        private var INSTANCE: HeroDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context) : HeroDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            HeroDatabase::class.java,
                            DATABASE_NAME)
                        .build()
                }

                return INSTANCE as HeroDatabase
            }
        }
    }


    abstract fun heroDao(): HeroDao
}