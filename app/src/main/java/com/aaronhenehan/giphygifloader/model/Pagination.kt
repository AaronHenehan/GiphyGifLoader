package com.aaronhenehan.giphygifloader.model

import com.google.gson.annotations.SerializedName

data class Pagination (
    var offset: Int,
    @SerializedName("total_count")
    var totalCount: Int,
    var count: Int)