package com.example.h_mal.candyspace

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class AppClass : Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppClass))

    }

}