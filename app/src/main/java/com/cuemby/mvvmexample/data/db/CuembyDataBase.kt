package com.cuemby.mvvmexample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cuemby.mvvmexample.data.db.dao.CurrentWeatherDao
import com.cuemby.mvvmexample.data.db.dao.WeatherLocationDao
import com.cuemby.mvvmexample.data.db.entity.CurrentWeatherEntry
import com.cuemby.mvvmexample.data.db.entity.WeatherLocation

@Database(
    entities = [CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)
abstract class CuembyDataBase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile
        private var instance: CuembyDataBase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CuembyDataBase::class.java,
            "cuemby.db"
        )
            .build()
    }
}