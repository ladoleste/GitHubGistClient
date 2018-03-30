package br.com.ladoleste.githubgistclient.repository

import android.arch.lifecycle.LiveData
import br.com.ladoleste.githubgistclient.dto.Gist
import io.reactivex.Single

/**
 * Created by Anderson on 23/03/2018
 */
interface GistRepository {
    fun getGists(page: Int): Single<List<Gist>>
    fun getGist(id: String): Single<Gist>
    fun getFavorites(): LiveData<List<Gist>>
    fun addToFavorite(gist: Gist)
    fun removeFromFavorites(gist: Gist)
    fun isFavorite(id: String): Boolean
}