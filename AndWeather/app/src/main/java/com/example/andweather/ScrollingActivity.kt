package com.example.andweather

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.andweather.adapter.CityRecyclerAdapter
import com.example.andweather.data.AppDatabase
import com.example.andweather.data.City
import com.example.andweather.databinding.ActivityScrollingBinding
import com.example.andweather.dialog.CityDialog
import com.example.andweather.touch.RecyclerTouchCallback


class ScrollingActivity : AppCompatActivity(), CityDialog.CityHandler {

    companion object {
        const val token = "a53db61059912cbaf6d595616867f16c"
    }


    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: CityRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title


        initCityRecyclerView()

    }


    override fun onResume() {
        super.onResume()

        binding.fab.setOnClickListener { view ->
            CityDialog().show(supportFragmentManager, "CITY_DIALOG")

        }
    }


    private fun initCityRecyclerView() {
        adapter = CityRecyclerAdapter(this)
        binding.recyclerCity.adapter = adapter

        var liveDataItems = AppDatabase.getInstance(this).cityDao().getAllCity()
        liveDataItems.observe(this, Observer { items ->
            adapter.submitList(items)

        })


        val touchCallbackList = RecyclerTouchCallback(adapter)
        val touchHelper = ItemTouchHelper(touchCallbackList)
        touchHelper.attachToRecyclerView(binding.recyclerCity)
    }


    override fun cityCreated(newCity: City) {

        val dbThread = Thread {
            AppDatabase.getInstance(this).cityDao().addCity(newCity)
        }
        dbThread.start()
    }

    fun showViewScreen(cityToView: City) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("CITY", cityToView)
        startActivity(intent)
    }



}