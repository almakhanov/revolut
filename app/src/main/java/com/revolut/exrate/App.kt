package com.revolut.exrate

import android.app.Application
import com.revolut.exrate.data.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

open class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this,listOf(appModule))
        Timber.plant(Timber.DebugTree())
    }
}