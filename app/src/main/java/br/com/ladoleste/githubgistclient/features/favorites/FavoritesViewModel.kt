package br.com.ladoleste.githubgistclient.features.favorites

import android.arch.lifecycle.MutableLiveData
import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.BaseViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepository
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class FavoritesViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: GistRepository

    val gists = MutableLiveData<List<Gist>>()
    val gistsError = MutableLiveData<Throwable>()

    init {
        CustomApplication.component.inject(this)
    }

    fun loadFavorites() = gists.postValue(repo.getFavorites())
}
