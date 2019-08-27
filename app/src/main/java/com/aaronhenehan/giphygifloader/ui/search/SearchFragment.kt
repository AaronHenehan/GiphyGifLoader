package com.aaronhenehan.giphygifloader.ui.search

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaronhenehan.giphygifloader.R
import com.aaronhenehan.giphygifloader.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private val TAG = "SearchFragment"
    private var currentSearchTerm: String = ""
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    var isLoading = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        val gifRecycler = binding.searchRecycler
        val searchEntry = binding.searchEntry
        val searchButton = binding.searchButton

        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        gifRecycler.layoutManager = gridLayoutManager
        gifRecycler.setHasFixedSize(true)

        val gifAdapter = GifsAdapter { imageUrl : String -> onGifItemClicked(imageUrl) }
        gifRecycler.adapter = gifAdapter

        searchViewModel.gifResponse.observe(this, Observer {
            Log.d(TAG, "Offset: " + it.pagination.offset)
            if (it.pagination.offset == 0) {
                gifAdapter.setGifs(it.gifs)
            } else {
                gifAdapter.addGifs(it.gifs)
            }
            isLoading = false
        })

        searchViewModel.getApiError().observe(this, Observer {
            Toast.makeText(context, getString(R.string.network_error, it), LENGTH_LONG).show()
        })

        searchButton.setOnClickListener {
            isLoading = true
            currentSearchTerm = searchEntry.text.toString()
            if (searchEntry.text.toString().isNotEmpty()) {
                searchViewModel.onSearchClicked(currentSearchTerm)
            }
        }

        gifRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                Log.d(TAG, "lastVisibleItemPosition: $lastVisibleItemPosition $totalItemCount")
                if (lastVisibleItemPosition >= totalItemCount - 2 && !isLoading) {
                    isLoading = true
                    searchViewModel.onLoadMore()
                }
            }
        })

        return binding.root
    }

    private fun onGifItemClicked(gifUrl: String) {
        val action = SearchFragmentDirections.actionSearchToDetail(gifUrl)
        findNavController().navigate(action)
    }
}