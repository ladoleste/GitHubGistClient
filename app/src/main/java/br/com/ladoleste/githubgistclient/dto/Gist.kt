package br.com.ladoleste.githubgistclient.dto

import br.com.ladoleste.githubgistclient.common.toString
import com.google.gson.annotations.SerializedName
import java.util.*


/**
 * Created by Anderson on 27/03/2018.
 */

data class Gist(
        @SerializedName("url") val url: String,
        @SerializedName("forks_url") val forksUrl: String,
        @SerializedName("commits_url") val commitsUrl: String,
        @SerializedName("id") val id: String,
        @SerializedName("git_pull_url") val gitPullUrl: String,
        @SerializedName("git_push_url") val gitPushUrl: String,
        @SerializedName("html_url") val htmlUrl: String,
        @SerializedName("files") val files: Files,
        @SerializedName("public") val public: Boolean,
        @SerializedName("created_at") private val createdAt: Date,
        @SerializedName("updated_at") val updatedAt: Date,
        @SerializedName("description") val description: String,
        @SerializedName("comments") val comments: Int,
        @SerializedName("user") val user: Any,
        @SerializedName("comments_url") val commentsUrl: String,
        @SerializedName("owner") val owner: Owner,
        @SerializedName("forks") val forks: List<Any>,
        @SerializedName("history") val history: List<History>,
        @SerializedName("truncated") val truncated: Boolean
) {
    val created get() = createdAt.toString("dd/MM/yyyy HH:mm")
    val author get() = "Author: ${owner.login}"
    val title get() = if (description.isEmpty()) "no title" else description
}