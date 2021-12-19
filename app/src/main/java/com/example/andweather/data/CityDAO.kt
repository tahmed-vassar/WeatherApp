package com.example.andweather.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDao {

    @Query("SELECT * FROM cityTable")
    fun getAllCity() : LiveData<List<City>>

    @Insert
    fun addCity(city: City) : Long

    @Delete
    fun deleteCity(city: City)


}