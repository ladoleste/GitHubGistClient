package br.com.ladoleste.githubgistclient.dto

import com.google.gson.annotations.SerializedName

data class ChangeStatus(
        @SerializedName("total") val total: Int,
        @SerializedName("additions") val additions: Int,
        @SerializedName("deletions") val deletions: Int
)