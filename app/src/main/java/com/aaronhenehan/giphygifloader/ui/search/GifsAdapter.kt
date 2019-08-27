package com.aaronhenehan.giphygifloader.ui.search

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aaronhenehan.giphygifloader.R
import com.aaronhenehan.giphygifloader.model.Gif
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class GifsAdapter (val clickListener: (String) -> Unit): RecyclerView.Adapter<GifsAdapter.GifHolder>() {
    private val gifs: ArrayList<Gif> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return GifHolder(view)
    }

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        val gif = gifs[position]
        val imageUrl = gif.images.fixedWidth.url
        Glide.with(holder.itemView.context)
            .load(imageUrl)
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
                    holder.loadingSpinner.visibility = GONE
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
                    holder.loadingSpinner.visibility = GONE
                    return false
                }
            })
            .into(holder.gifImage)
        holder.itemView.setOnClickListener { clickListener(imageUrl) }
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    fun setGifs(gifs: List<Gif>) {
        this.gifs.clear()
        this.gifs.addAll(gifs)
        notifyDataSetChanged()
    }

    fun addGifs(gifs: List<Gif>) {
        val oldSize = this.gifs.size
        this.gifs.addAll(gifs)
        notifyItemRangeInserted(oldSize, gifs.size)
    }

    class GifHolder(v: View) : RecyclerView.ViewHolder(v) {
        var loadingSpinner: View = v.findViewById(R.id.gif_loading_spinner)
        var gifImage: ImageView = v.findViewById(R.id.gif_image)
    }
}
