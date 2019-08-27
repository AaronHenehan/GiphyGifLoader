package com.aaronhenehan.giphygifloader.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aaronhenehan.giphygifloader.model.GifResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GiphyService {
    private val TAG = "GiphyService"
    private val API_KEY = "INSERT_API_KEY_HERE"
    private val GIF_LIMIT = 25
    private val BASE_URL = "https://api.giphy.com/v1/gifs/"

    private var _apiError: MutableLiveData<String> = MutableLiveData()
    val apiError: LiveData<String>
        get() = _apiError

    private var _gifResponse: MutableLiveData<GifResponse> = MutableLiveData()
    val gifResponse: LiveData<GifResponse>
        get() = _gifResponse

    private val giphyApi by lazy {
        create()
    }

    fun searchGifs(searchTerm: String, offset: Int) {
        Log.d(TAG, "We are searching for: $searchTerm")
        giphyApi.searchGifs(API_KEY, searchTerm, GIF_LIMIT, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.v(TAG, "Search Successful")
                _gifResponse.value = it
            }, { it ->
                _apiError.value = handleCode((it as HttpException).code())
            })
    }

    private fun handleCode(code: Int): String {
        return when (code) {
            200 ->
                // Successful response
                "Gifs loaded"
            400 ->
                // Your request was formatted incorrectly or missing a required parameter(s)
                "Bad Request"
            403 ->
                // You weren't authorized to make your request; most likely this indicates an issue with your API Key
                "Forbidden"
            404 ->
                // The particular GIF or Sticker you are requesting was not found. This occurs, for example, if you request a GIF by using an id that does not exist
                "Not Found"
            404 ->
                // Your API Key is making too many requests. Read about requesting a Production Key to upgrade your API Key rate limits.
                "Too Many Requests"
            else ->
                // Generic Error response
                "Connection error"
        }
    }

    private fun create(): GiphyApi {

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(GiphyApi::class.java)
    }
}