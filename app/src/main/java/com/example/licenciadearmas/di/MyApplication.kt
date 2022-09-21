package com.example.licenciadearmas.di

import android.app.Application
import com.example.licenciadearmas.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}