package com.example.andweather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.andweather.DetailsActivity
import com.example.andweather.ScrollingActivity
import com.example.andweather.data.AppDatabase
import com.example.andweather.data.City
import com.example.andweather.databinding.CityRowBinding
import com.example.andweather.touch.TouchHelperCallback
import kotlin.concurrent.thread

class CityRecyclerAdapter : ListAdapter<City, CityRecyclerAdapter.ViewHolder>, TouchHelperCallback {

    val context: Context

    constructor(context: Context) : super(CityDiffCallback()) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cityRowBinding = CityRowBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )
        return ViewHolder(cityRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = getItem(holder.adapterPosition)
        holder.bind(currentCity)

        holder.cityRowBinding.tvCity.text = currentCity.name
        holder.cityRowBinding.tvTemp.text = currentCity.temp.toString()


        holder.cityRowBinding.btnDelete.setOnClickListener {
            deleteCity(holder.adapterPosition)
        }

        holder.cityRowBinding.btnView.setOnClickListener {
            (context as ScrollingActivity).showViewScreen(currentCity)
        }

    }


    fun deleteCity(index: Int) {
        thread {
            AppDatabase.getInstance(context).cityDao().deleteCity(getItem(index))
        }
    }

    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(val cityRowBinding: CityRowBinding) :
        RecyclerView.ViewHolder(cityRowBinding.root) {
        fun bind(city: City) {
            val tvCity: TextView = cityRowBinding.tvCity
            val btnDelete: Button = cityRowBinding.btnDelete

        }
    }


}


class CityDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem._id == newItem._id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}