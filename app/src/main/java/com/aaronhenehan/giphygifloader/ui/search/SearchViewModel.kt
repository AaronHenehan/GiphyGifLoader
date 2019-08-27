package com.aaronhenehan.giphygifloader.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aaronhenehan.giphygifloader.api.GiphyService
import com.aaronhenehan.giphygifloader.model.GifResponse

class SearchViewModel : ViewModel() {
    private val TAG = "SearchViewModel"

    private var currentSearchTerm = ""

    val gifResponse: LiveData<GifResponse> = GiphyService.gifResponse

    fun onSearchClicked(searchTerm: String) {
        currentSearchTerm = searchTerm
        GiphyService.searchGifs(searchTerm, 0)
    }

    fun onLoadMore() {
        Log.d(TAG, "onLoadMore")
        val offset = gifResponse.value?.pagination?.offset!! + gifResponse.value?.pagination?.count!!
        GiphyService.searchGifs(currentSearchTerm, offset)
    }

    fun getApiError(): LiveData<String> {
        return GiphyService.apiError
    }
}