package br.com.ladoleste.githubgistclient.repository

import br.com.ladoleste.githubgistclient.dto.GistResponse
import io.reactivex.Single
import java.util.*

/**
 * Created by Anderson on 23/03/2018
 */
interface GistRepository {
    fun getGists(): Single<List<GistResponse>>
    fun getGist(id: UUID): Single<GistResponse>
}