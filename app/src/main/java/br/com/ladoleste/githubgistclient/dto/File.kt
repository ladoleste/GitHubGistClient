package br.com.ladoleste.githubgistclient.dto

import com.google.gson.annotations.SerializedName

data class File(
        @SerializedName("filename") val filename: String,
        @SerializedName("type") val type: String,
        @SerializedName("language") val language: String?,
        @SerializedName("raw_url") val rawUrl: String,
        @SerializedName("size") val size: Int,
        @SerializedName("truncated") val truncated: Boolean,
        @SerializedName("content") val content: String
)