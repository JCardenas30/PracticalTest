package com.sunwise

import android.app.Application
import android.content.Context

class PokemonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }
    companion object{
        lateinit var context: Context
    }
}