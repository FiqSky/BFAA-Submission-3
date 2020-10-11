package com.fiqsky.customerapp.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("login")
    val userName: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("company")
    val company: String = "",
    @SerializedName("blog")
    val blog: String = "",
    @SerializedName("public_repos")
    val publicRepos: String = "",
    @SerializedName("followers")
    val totalFollowers: String = "",
    @SerializedName("following")
    val totalFollowing: String = ""
) : Parcelable