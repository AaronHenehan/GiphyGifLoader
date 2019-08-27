package com.aaronhenehan.giphygifloader.api

import com.aaronhenehan.giphygifloader.model.GifResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("search")
    fun searchGifs(@Query("api_key") apiKey: String,
                   @Query("q") search: String,
                    @Query("limit") limit: Int,
                    @Query("offset") offset: Int)
            : Observable<GifResponse>
}