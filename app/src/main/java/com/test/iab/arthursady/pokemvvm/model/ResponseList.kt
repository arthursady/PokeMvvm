package com.test.iab.arthursady.pokemvvm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseList<T> {

    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("next")
    @Expose
    var next: String? = null
    @SerializedName("previous")
    @Expose
    var previous: String? = null
    @SerializedName("results")
    @Expose
    var results: List<T>? = null
}
