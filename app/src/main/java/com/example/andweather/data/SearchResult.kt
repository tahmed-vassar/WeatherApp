package com.example.andweather.data

import com.google.gson.annotations.SerializedName

data class SearchResult (

    @SerializedName("name"        ) var name       : String?     = null,
    @SerializedName("local_names" ) var localNames : LocalNames? = LocalNames(),
    @SerializedName("lat"         ) var lat        : Double?     = null,
    @SerializedName("lon"         ) var lon        : Double?     = null,
    @SerializedName("country"     ) var country    : String?     = null

)

data class LocalNames (

    @SerializedName("ar"           ) var ar          : String? = null,
    @SerializedName("ascii"        ) var ascii       : String? = null,
    @SerializedName("bg"           ) var bg          : String? = null,
    @SerializedName("de"           ) var de          : String? = null,
    @SerializedName("en"           ) var en          : String? = null,
    @SerializedName("fa"           ) var fa          : String? = null,
    @SerializedName("feature_name" ) var featureName : String? = null,
    @SerializedName("fi"           ) var fi          : String? = null,
    @SerializedName("fr"           ) var fr          : String? = null,
    @SerializedName("he"           ) var he          : String? = null,
    @SerializedName("ja"           ) var ja          : String? = null,
    @SerializedName("lt"           ) var lt          : String? = null,
    @SerializedName("nl"           ) var nl          : String? = null,
    @SerializedName("pl"           ) var pl          : String? = null,
    @SerializedName("pt"           ) var pt          : String? = null,
    @SerializedName("ru"           ) var ru          : String? = null,
    @SerializedName("sr"           ) var sr          : String? = null

)