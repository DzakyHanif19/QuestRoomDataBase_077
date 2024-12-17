package com.example.project9

import android.app.Application
import com.example.project9.depedenciesInjection.ContainerApp
import com.example.project9.depedenciesInjection.InterfaceContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp =ContainerApp(this)
    }
}