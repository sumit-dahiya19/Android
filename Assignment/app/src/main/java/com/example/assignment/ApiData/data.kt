package com.example.assignment

import com.google.gson.annotations.SerializedName

data class VechicleInfo(
    val success: Boolean,
    val data: List<Data>,
    val meta: Meta
)

data class Data(
    val id: Long,

    @SerializedName("display_number")
    val displayNumber: String,

    @SerializedName("icon_kind")
    val iconKind: String,

    @SerializedName("last_location")
    val lastLocation: LastLocation,

    @SerializedName("icon_details")
    val iconDetails: IconDetails,

    @SerializedName("vehicle_make")
    val vehicleMake: Any? = null,

    @SerializedName("vehicle_model")
    val vehicleModel: Any? = null


)

data class IconDetails(

    @SerializedName( "icon_kind")
    val iconKind: String,

    @SerializedName( "vehicle_icon_id")
    val vehicleIconID: Any? = null,

    @SerializedName( "is_custom")
    val isCustom: Boolean,

    @SerializedName("service_url")
    val serviceURL: String
)

data class LastLocation(
    val lat: Double? ,
    val long: Double?,
    val orientation: Double?,
    val speed: Double?,

    @SerializedName( "received_ts")
    val receivedTs: Long? = null
)

data class Meta(
    val total: Long
)

