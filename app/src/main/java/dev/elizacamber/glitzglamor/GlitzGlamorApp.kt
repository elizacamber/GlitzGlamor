package dev.elizacamber.glitzglamor

import android.app.Application
import android.content.Context
import dev.elizacamber.glitzglamor.data.CitiesDatabase
import dev.elizacamber.glitzglamor.data.createCountryList

class GlitzGlamorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Holder.provideDb(applicationContext)
        createCountryList()
    }
}

object Holder {
    lateinit var database: CitiesDatabase
        private set

    fun provideDb(context: Context) {
        database = CitiesDatabase.getInstance(context)
    }
}