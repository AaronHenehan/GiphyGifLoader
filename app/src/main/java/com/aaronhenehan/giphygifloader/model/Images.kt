package com.aaronhenehan.giphygifloader.model

import com.google.gson.annotations.SerializedName

data class Images (
    @SerializedName("fixed_width")
    var fixedWidth: FixedWidth)