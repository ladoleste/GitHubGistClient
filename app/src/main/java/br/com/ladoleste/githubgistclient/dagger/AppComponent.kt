package br.com.ladoleste.githubgistclient.dagger

import br.com.ladoleste.githubgistclient.features.detail.DetailsViewModel
import br.com.ladoleste.githubgistclient.features.favorites.FavoritesViewModel
import br.com.ladoleste.githubgistclient.features.list.MainViewModel
import br.com.ladoleste.githubgistclient.repository.GistRepositoryImpl
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Anderson on 21/03/2018
 */

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(target: MainViewModel)
    fun inject(target: FavoritesViewModel)
    fun inject(target: DetailsViewModel)
    fun inject(target: GistRepositoryImpl)
}