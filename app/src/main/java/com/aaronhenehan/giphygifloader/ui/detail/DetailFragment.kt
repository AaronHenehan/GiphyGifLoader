package com.aaronhenehan.giphygifloader.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aaronhenehan.giphygifloader.R
import com.aaronhenehan.giphygifloader.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val gifUrl = arguments?.getString("gifUrl", null)

        if (gifUrl == null) {
            Toast.makeText(context, R.string.error_loading_gif, LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detail_to_search)
        } else {
            loadGif(gifUrl)
        }

        return binding.root
    }

    private fun loadGif(gifUrl: String) {
        context?.let {
            Glide.with(it)
                .load(gifUrl)
                .centerCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Hide the loader
                        binding.detailGifLoadingSpinner.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Hide the loader
                        // TODO: diplay a load error image
                        binding.detailGifLoadingSpinner.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.detailGif)
        }
    }
}