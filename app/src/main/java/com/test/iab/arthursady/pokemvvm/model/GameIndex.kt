package com.test.iab.arthursady.pokemvvm.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GameIndex {

    @SerializedName("game_index")
    @Expose
    var gameIndex: Int? = null
    @SerializedName("version")
    @Expose
    var version: Version? = null

}
