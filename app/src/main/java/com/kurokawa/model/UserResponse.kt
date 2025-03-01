package com.kurokawa.model

import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName("idFirebase") val idFirebase: String
)