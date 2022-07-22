package com.example.android_imperative.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.model.TVShowDetails
import com.example.android_imperative.model.TVShowPopular
import com.example.android_imperative.repository.TVShowRepository
import com.example.android_imperative.utils.Logger.Companion.d
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowPopular = MutableLiveData<TVShowPopular>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    val tvShowsFromDB = MutableLiveData<List<TVShow>>()


    /**
     * Retrofit Related
     */

    fun apiTVShowPopular(page: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    tvShowPopular.postValue(resp)
                    var localShows = tvShowsFromApi.value
                    if (localShows == null) localShows = ArrayList()
                    val serverShows = resp!!.tv_shows
                    localShows.addAll(serverShows)
                    tvShowsFromApi.postValue(localShows)
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun apiTVShowDetails(q: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowDetails(q)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val reps = response.body()
                    tvShowDetails.postValue(reps!!)
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /**
     * Room Related
     */

    fun getTVShowsFromDB() {
        viewModelScope.launch {
            val tvShows = tvShowRepository.getTVShowsFromDB()
            tvShowsFromDB.postValue(tvShows)
        }
    }

    fun deleteTVShowsFromDB() {
        viewModelScope.launch {
            tvShowRepository.deleteTvShowsFromDB()
        }
    }

    fun insertTVShowToDB(tvShow: TVShow) {
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }


}