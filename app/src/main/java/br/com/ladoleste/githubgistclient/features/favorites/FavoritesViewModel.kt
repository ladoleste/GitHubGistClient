package br.com.ladoleste.githubgistclient.features.favorites

import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.features.common.BaseViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepository
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class FavoritesViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: GistRepository

    init {
        CustomApplication.component.inject(this)
    }

    fun loadFavorites() = repo.getFavorites()
}
