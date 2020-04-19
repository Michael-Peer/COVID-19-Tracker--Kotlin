package com.example.coronaapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coronaapp.network.CoronaApi
import com.example.coronaapp.network.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    var result = emptyMap<String, List<Property>>()

    val dataList = MutableLiveData<List<Float>>()


    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private val _properties = MutableLiveData<List<Property>>()

    val properties: LiveData<List<Property>>
        get() = _properties

    private val _property = MutableLiveData<Property>()

    val property: LiveData<Property>
        get() = _property

//    @Bindable //enable two way data binding
    val editTextContent = MutableLiveData<String>()

    private val _displayEditTextContent = MutableLiveData<String>()

    val displayEditTextContent: LiveData<String>
        get() = _displayEditTextContent

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        // Because Retrofit does all its work on a background thread, there's no reason to use any other thread for the scope.
        viewModelJob + Dispatchers.Main
    )

    init {
        Log.i("MainScreenViewModel", "MainScreenViewModel created!")
        getCoronavirusPlaces()
//        _death.value = 0f
    }

    //get from the api
    private fun getCoronavirusPlaces() {
        Log.i("MainScreenViewModel", "getCoronavirusPlaces created!")

        coroutineScope.launch {
            var getCoronavirusPlacesDeffers = CoronaApi.retrofitService.getPropeties()
            try {
                 result = getCoronavirusPlacesDeffers.await()
                if (result.isNotEmpty()) {
                    setMapCalculation()
                }
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    private fun setMapCalculation() {
        //get last value
        val lastCountry: Map<String, Property> = result.mapValues { (country, data) -> data.last() }

        //sum by type
        val sumDeaths: Int = lastCountry.map { it.value }.sumBy { it.deaths }
        val sumRecoverd: Int = lastCountry.map { it.value }.sumBy { it.recovered }
        val sumConfirmed: Int = lastCountry.map { it.value }.sumBy { it.confirmed }

        //assign values
        var _death = sumDeaths.toFloat()
        var _recoverd = sumRecoverd.toFloat()
        var _confirmed = sumConfirmed.toFloat()

        //add to list
        var listPlace: ArrayList<Float> = ArrayList()
        listPlace.add(_death)
        listPlace.add(_recoverd)
        listPlace.add(_confirmed)
        dataList.value = listPlace
    }

    fun onDisplayEditTextContent() {
        _displayEditTextContent.value = editTextContent.value // through data binding
    }

    fun getSingleCountryData(singleCountry: String): Property? {
        Log.i("MainScreenViewModel", " CPA ${singleCountry.capitalize()} ")
        val capCountry = singleCountry.toLowerCase().capitalize()
        if (result.containsKey(capCountry)) {
            val country: List<Property>? = result[capCountry]
            return country!!.last()
        }
        return null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}