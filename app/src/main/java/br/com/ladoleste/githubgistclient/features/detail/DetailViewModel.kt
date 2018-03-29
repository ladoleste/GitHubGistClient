package br.com.ladoleste.githubgistclient.features.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.com.ladoleste.githubgistclient.common.CustomApplication
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.BaseViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class DetailViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: GistRepository

    private val _gist = MutableLiveData<Gist>()
    private val _gistError = MutableLiveData<Throwable>()

    var gist: LiveData<Gist> = _gist
    var gistError: LiveData<Throwable> = _gistError

    init {
        CustomApplication.component.inject(this)
    }

    fun getGist(id: UUID) = cDispose.add(repo.getGist(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError({ t -> Timber.e(t) }).subscribe({

            }, {

            }))
}
