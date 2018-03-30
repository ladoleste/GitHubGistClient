package br.com.ladoleste.githubgistclient.features.detail

import android.arch.lifecycle.MutableLiveData
import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.dto.File
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.BaseViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class DetailsViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: GistRepository

    val gist = MutableLiveData<Gist>()
    val title = MutableLiveData<String>()
    val author = MutableLiveData<String>()
    val languages = MutableLiveData<String>()
    val avatarUrl = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()
    val hasTitle = MutableLiveData<Boolean>()
    val files = MutableLiveData<Map<String, File>>()

    val gistError = MutableLiveData<Throwable>()

    init {
        CustomApplication.component.inject(this)
    }

    fun loadGist(id: String) {

        val favorite = repo.isFavorite(id)

        cDispose.add(repo.getGist(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> Timber.e(t) }).subscribe({
                    gist.postValue(it)
                    title.postValue(it.title)
                    author.postValue(it.author)
                    languages.postValue(it.languages)
                    avatarUrl.postValue(it.owner?.avatarUrl)
                    files.postValue(it.files)
                    isFavorite.postValue(favorite)
                    hasTitle.postValue(it.hasTitle)
                }, {
                    gistError.postValue(it)
                }))
    }
    fun addToFavorites() {
        try {
            repo.addToFavorite(gist.value!!)
        } catch (e: Exception) {
            gistError.postValue(e)
        }
    }

    fun removeFromFavorites() {
        try {
            repo.removeFromFavorites(gist.value!!)
        } catch (e: Exception) {
            gistError.postValue(e)
        }
    }
}
