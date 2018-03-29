package br.com.ladoleste.githubgistclient.common

import br.com.ladoleste.githubgistclient.dto.Gist
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 *Created by Anderson on 08/12/2017.
 */
interface Api {
    @GET("gists/public")
    fun getGists(): Single<List<Gist>>

    @GET("gists/{id}")
    fun getGist(@Path("id") id: UUID): Single<Gist>
}