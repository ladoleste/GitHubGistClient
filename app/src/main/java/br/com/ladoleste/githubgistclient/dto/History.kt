package br.com.ladoleste.githubgistclient.dto

import com.google.gson.annotations.SerializedName

data class History(
        @SerializedName("user") val user: User,
        @SerializedName("version") val version: String,
        @SerializedName("committed_at") val committedAt: String,
        @SerializedName("change_status") val changeStatus: ChangeStatus,
        @SerializedName("url") val url: String
)