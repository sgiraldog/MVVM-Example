package com.cuemby.mvvmexample.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cuemby.mvvmexample.data.db.entity.WEATHER_LOCATION_ID
import com.cuemby.mvvmexample.data.db.entity.WeatherLocation


@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}