package com.test.iab.arthursady.pokemvvm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.test.iab.arthursady.pokemvvm.utils.ApiConstants

class PokemonFormMinified {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

    val relativeUrl: String?
        get() {
            url?.let {
                return it.removePrefix(ApiConstants.BASE_URL)
            }
            return null
        }
}
