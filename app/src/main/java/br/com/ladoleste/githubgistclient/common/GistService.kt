package br.com.ladoleste.githubgistclient.common

import br.com.ladoleste.githubgistclient.dto.Gist
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by Anderson on 08/12/2017.
 */
interface GistService {
    @GET("gists/public")
    fun getGists(@Query("page") page: Int): Single<List<Gist>>

    @GET("gists/{id}")
    fun getGist(@Path("id") id: String): Single<Gist>
}