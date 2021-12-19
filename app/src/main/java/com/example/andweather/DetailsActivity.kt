package com.example.andweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.andweather.data.City
import com.example.andweather.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding.root)

        val city = intent.extras?.get("CITY") as City

        detailsBinding.tvName.text = city.name
        detailsBinding.tvCoord.text = city.lat.toString() + ", " + city.lon.toString()
        detailsBinding.tvWeather.text = city.weather
        detailsBinding.tvTemperature.text = city.temp.toString()
        detailsBinding.tvPressure.text = city.pressure.toString()
        detailsBinding.tvHumidity.text = city.humidity.toString()
        detailsBinding.tvMaxTemp.text = city.maxTemp.toString()
        detailsBinding.tvMinTemp.text = city.minTemp.toString()

        if (city.getImageURL().isNotEmpty()) {
            Glide.with(this).load(city.getImageURL()).into(detailsBinding.imgWeather)
            detailsBinding.imgWeather.visibility = View.VISIBLE
        } else {
            detailsBinding.imgWeather.visibility = View.GONE
        }

    }


    override fun onResume() {
        super.onResume()

        detailsBinding.btnBack.setOnClickListener {
            finish()
        }



    }
}