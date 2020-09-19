package com.bradie.app.apiservice

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagesModel(
    @SerializedName("total")
    @Expose
    var total: Int? = null,

    @SerializedName("totalHits")
    @Expose
    var totalHits: Int? = null,

    @SerializedName("hits")
    @Expose
    var hits: List<Hit>? = null
)