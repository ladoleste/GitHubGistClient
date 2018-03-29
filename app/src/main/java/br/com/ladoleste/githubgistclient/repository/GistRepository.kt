package br.com.ladoleste.githubgistclient.repository

import br.com.ladoleste.githubgistclient.dto.Gist
import io.reactivex.Single
import java.util.*

/**
 * Created by Anderson on 23/03/2018
 */
interface GistRepository {
    fun getGists(): Single<List<Gist>>
    fun getGist(id: UUID): Single<Gist>
}