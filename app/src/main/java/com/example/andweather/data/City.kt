package com.example.andweather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cityTable")
data class City(
    @PrimaryKey(autoGenerate = true) var _id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lat") var lat: Double?,
    @ColumnInfo(name = "lon") var lon: Double?,
    @ColumnInfo(name = "weather") var weather: String?,
    @ColumnInfo(name = "temp") var temp: Double?,
    @ColumnInfo(name = "pressure") var pressure: Int?,
    @ColumnInfo(name = "humidity") var humidity: Int?,
    @ColumnInfo(name = "minTemp") var minTemp: Double?,
    @ColumnInfo(name = "maxTemp") var maxTemp: Double?,
    @ColumnInfo(name = "img") var img: String?
) : Serializable {


    fun getImageURL() : String{
        val baseURL = "https://openweathermap.org/img/wn/"
        return ("$baseURL$img@2x.png")
    }

    }




