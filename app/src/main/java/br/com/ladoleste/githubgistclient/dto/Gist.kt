package br.com.ladoleste.githubgistclient.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * Created by Anderson on 27/03/2018.
 */
@Entity
data class Gist(
        @PrimaryKey
        @SerializedName("id") var id: String = "",

//        @SerializedName("url") var url: String = "",
//        @SerializedName("forks_url") var forksUrl: String = "",
//        @SerializedName("commits_url") var commitsUrl: String = "",
//        @SerializedName("git_pull_url") var gitPullUrl: String = "",
//        @SerializedName("git_push_url") var gitPushUrl: String = "",
//        @SerializedName("html_url") var htmlUrl: String = "",
        @SerializedName("files") var files: Map<String, File>? = null,
//        @SerializedName("public") var public: Boolean = false,
//        @SerializedName("created_at") var createdAt: Date = Date(),
//        @SerializedName("updated_at") var updatedAt: Date = Date(),
        @SerializedName("description") var description: String? = "",
//        @SerializedName("comments") var comments: Int = 0,
//        @SerializedName("user") var user: Any,
//        @SerializedName("comments_url") var commentsUrl: String = "",
        @Embedded(prefix = "owner")
        @SerializedName("owner") var owner: Owner? = null
//        @SerializedName("forks") var forks: List<Any>,
//        @SerializedName("history") var history: List<History>,
//        @SerializedName("truncated") var truncated: Boolean = false
) {
    val author get() = "Author: ${owner?.login}"
    val title get(): String = if (description.isNullOrBlank()) "" else description!!
    val hasTitle get() = title.isNotBlank()
    val isFavorite get() = false
    val languages: String
        get() {

            var str = "Languages: "
            val distinct = files!!.map { it.value.language }.toList().distinct()
            distinct.forEach { str = "$str, $it" }
            return str.replace(": , ", ": ").replace("null", "")
        }
}