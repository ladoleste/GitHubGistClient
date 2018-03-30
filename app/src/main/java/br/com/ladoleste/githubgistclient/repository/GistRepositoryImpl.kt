package br.com.ladoleste.githubgistclient.repository

import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.common.GistService
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.repository.room.MyDatabase
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class GistRepositoryImpl : GistRepository {
    @Inject
    lateinit var gistService: GistService

    @Inject
    lateinit var db: MyDatabase

    init {
        CustomApplication.component.inject(this)
    }

    override fun getGists(page: Int) = gistService.getGists(page)

    override fun getGist(id: String) = gistService.getGist(id)

    override fun getFavorites() = db.gistDao().loadFavoriteGists()

    override fun addToFavorite(gist: Gist) {
        db.gistDao().insert(gist)
    }

    override fun removeFromFavorites(gist: Gist) {
        db.gistDao().delete(gist)
    }

    override fun isFavorite(id: String) = db.gistDao().isFavorite(id)
}