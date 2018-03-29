package br.com.ladoleste.githubgistclient.repository

import br.com.ladoleste.githubgistclient.common.Api
import br.com.ladoleste.githubgistclient.common.CustomApplication
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class NetworkGistRepositoryImpl : GistRepository {

    @Inject
    lateinit var api: Api

    init {
        CustomApplication.component.inject(this)
    }

    override fun getGists() = api.getGists()

    override fun getGist(id: String) = api.getGist(id)
}