package com.example.h_mal.candyspace

import android.app.Application
import com.example.h_mal.candyspace.data.api.ApiClass
import com.example.h_mal.candyspace.data.api.NetworkConnectionInterceptor
import com.example.h_mal.candyspace.data.api.QueryParamsInterceptor
import com.example.h_mal.candyspace.data.repositories.Repository
import com.example.h_mal.candyspace.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppClass : Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppClass))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { QueryParamsInterceptor() }
        bind() from singleton { ApiClass(instance(), instance()) }
//        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { Repository(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }

}