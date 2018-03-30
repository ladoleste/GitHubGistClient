package br.com.ladoleste.githubgistclient.common

import android.app.Application
import br.com.ladoleste.githubgistclient.BuildConfig
import br.com.ladoleste.githubgistclient.dagger.AppComponent
import br.com.ladoleste.githubgistclient.dagger.AppModule
import br.com.ladoleste.githubgistclient.dagger.DaggerAppComponent
import com.facebook.stetho.Stetho
import timber.log.Timber

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Timber.plant(if (BuildConfig.DEBUG) DebugLog() else ReleaseLog())
        instance = this
        component = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
    }

    companion object {

        lateinit var instance: Application
            private set

        lateinit var component: AppComponent
            private set
    }
}