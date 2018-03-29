package br.com.ladoleste.githubgistclient.repository

import br.com.ladoleste.githubgistclient.common.GistService
import br.com.ladoleste.githubgistclient.common.CustomApplication
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class NetworkGistRepositoryImpl : GistRepository {

    @Inject
    lateinit var gistService: GistService

    init {
        CustomApplication.component.inject(this)
    }

    override fun getGists(page: Int) = gistService.getGists(page)

    override fun getGist(id: String) = gistService.getGist(id)
}