package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.LiveData
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

    private val _gists = MutableLiveData<List<GistResponse>>()
    private val _gistsError = MutableLiveData<Throwable>()

    var gists: LiveData<List<GistResponse>> = _gists
    var gistsError: LiveData<Throwable> = _gistsError

    init {
        CustomApplication.component.inject(this)
    }

    fun getGists() = cDispose.add(repo.getGists()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError({ t -> Timber.e(t) })
            .subscribe({
                _gists.postValue(it)
            }, {
                _gistsError.postValue(it)
            }))
}
