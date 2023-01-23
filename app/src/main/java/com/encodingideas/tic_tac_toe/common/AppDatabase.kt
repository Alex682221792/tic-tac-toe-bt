package com.encodingideas.tic_tac_toe.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.encodingideas.tic_tac_toe.dao.SettingsDao
import com.encodingideas.tic_tac_toe.model.Settings

@Database(entities = [Settings::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    //DAOs
    abstract fun settingsDao(): SettingsDao

    companion object{
        @Volatile
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if(!::instance.isInitialized){
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
                ).build()
            }
            return instance
        }
    }
}