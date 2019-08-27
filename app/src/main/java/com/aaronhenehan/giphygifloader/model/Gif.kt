package com.aaronhenehan.giphygifloader.model

import com.google.gson.annotations.SerializedName

data class Gif (
    val type: String,
    val id: String,
    val slug: String,
    val url: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    val title: String,
    val images: Images)