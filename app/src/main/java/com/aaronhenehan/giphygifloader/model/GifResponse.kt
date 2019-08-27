package com.aaronhenehan.giphygifloader.model

import com.google.gson.annotations.SerializedName

data class GifResponse (
    @SerializedName("data")
    var gifs: List<Gif>,
    var pagination: Pagination)