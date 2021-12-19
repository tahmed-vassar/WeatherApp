package com.example.andweather.retrofit

import com.example.andweather.data.City
import com.example.andweather.data.SearchResult
import com.example.andweather.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable


//link: api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
//host: https://openweathermap.org
// Query prams:
//   access_key=a53db61059912cbaf6d595616867f16c


interface WeatherAPI {


    @GET("/data/2.5/weather")
    fun getWeatherResult(@Query("q") cityQuery: String, @Query("units") unit: String, @Query("appid") key:String)  : Call<WeatherResult>


    @GET("/geo/1.0/direct")
    fun getSearchResult(@Query( "q") cityQuery: String, @Query("limit") limit: Int = 5, @Query("appid") key: String) : Call<List<SearchResult>>


}