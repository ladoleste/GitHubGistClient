package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.MutableLiveData
import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.dto.GistResponse
import br.com.ladoleste.githubgistclient.features.common.BaseViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: GistRepository

    val created = MutableLiveData<String>()
    val url = MutableLiveData<String>()

    val gists = MutableLiveData<GistResponse>()
    val gistsError = MutableLiveData<Throwable>()

    init {
        CustomApplication.component.inject(this)
    }

    fun loadGists() = cDispose.add(repo.getGists()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError({ t -> Timber.e(t) })
            .subscribe({
                created.postValue(it.first().created)
                url.postValue(it.first().url)
                gists.postValue(it.first())
            }, {
                gistsError.postValue(it)
            }))
}
