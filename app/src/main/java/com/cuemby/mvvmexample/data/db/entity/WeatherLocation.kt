package com.cuemby.mvvmexample.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime


const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: String,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    val lon: String,
    val name: String,
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID


    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)

        }
}