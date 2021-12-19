package com.example.andweather.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.andweather.R
import com.example.andweather.ScrollingActivity
import com.example.andweather.data.City
import com.example.andweather.data.SearchResult
import com.example.andweather.data.WeatherResult
import com.example.andweather.databinding.CityDialogBinding
import com.example.andweather.retrofit.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CityDialog : DialogFragment(), AdapterView.OnItemClickListener {

    val token = "a53db61059912cbaf6d595616867f16c"
    var itemClicked: Boolean = false

    interface CityHandler {
        fun cityCreated(newCity: City)
    }

    lateinit var cityHandler: CityHandler
    lateinit var weatherService: WeatherAPI
    var suggestions = mutableListOf<String>()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler) {
            cityHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the CityHandler interface."
            )
        }
    }

    private lateinit var dialogBinding: CityDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle(getString(R.string.add_new_city))

        dialogBinding = CityDialogBinding.inflate(requireActivity().layoutInflater)
        dialogBuilder.setView(dialogBinding.root)

        dialogBuilder.setPositiveButton("OK") { _, _ ->
        }

        dialogBuilder.setNegativeButton("Cancel") { _, _ ->
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherAPI::class.java)

        return dialogBuilder.create()
    }


    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        dialogBinding.autoTextView.onItemClickListener = this


        positiveButton.setOnClickListener {
            if (itemClicked) {
                handleCityCreate(weatherService)
                dialog.dismiss()

            } else {
                dialogBinding.autoTextView.error = getString(R.string.search_error)
            }
        }



        dialogBinding.btnSearch.setOnClickListener {

            val userSearch = dialogBinding.autoTextView.text.toString()

            if (userSearch.isNotEmpty()) {
                val searchCall = weatherService.getSearchResult(userSearch, limit = 5, token)


                searchCall.enqueue(object : Callback<List<SearchResult>> {
                    override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                        dialogBinding.autoTextView.error = t.message
                    }

                    override fun onResponse(
                        call: Call<List<SearchResult>>,
                        response: Response<List<SearchResult>>
                    ) {
                        val searchResults = response.body()

                        if (searchResults.isNullOrEmpty()) {
                            dialogBinding.autoTextView.error = getString(R.string.error_msg2)

                        } else {
                            for (res in searchResults!!) {
                                val name = res.name.toString()
                                val country = res.country.toString()

                                suggestions.add("$name, $country")

                            }

                            dialogBinding.autoTextView.setAdapter(
                                ArrayAdapter(
                                    context as ScrollingActivity,
                                    android.R.layout.simple_list_item_1,
                                    suggestions
                                )
                            )
                            dialogBinding.autoTextView.showDropDown()
                        }
                    }
                })


            } else {
                dialogBinding.autoTextView.error = getString(R.string.error_msg)
            }
        }
    }

    private fun handleCityCreate(weatherService: WeatherAPI) {
        val city = dialogBinding.autoTextView.text.toString()

        val weatherCall = weatherService.getWeatherResult(city, "imperial", token)

        weatherCall.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherResult = response.body()

                val newCity = City(
                    null,
                    city,
                    weatherResult?.coord?.lat,
                    weatherResult?.coord?.lon,
                    weatherResult?.weather?.get(0)?.description,
                    weatherResult?.main?.temp,
                    weatherResult?.main?.pressure,
                    weatherResult?.main?.humidity,
                    weatherResult?.main?.tempMin,
                    weatherResult?.main?.tempMax,
                    weatherResult?.weather?.get(0)?.icon
                )
                cityHandler.cityCreated(newCity)

            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                dialogBinding.autoTextView.error = t.message
            }

        })

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        itemClicked = true
        dialogBinding.autoTextView.dismissDropDown()

    }
}