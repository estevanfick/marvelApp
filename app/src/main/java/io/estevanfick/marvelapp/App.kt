package io.estevanfick.marvelapp

import android.app.Application
import io.estevanfick.marvelapp.data.api.ApiManager
import io.estevanfick.marvelapp.di.AppComponent
import io.estevanfick.marvelapp.di.DaggerAppComponent


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .apiManager(ApiManager())
                .build()
    }
}