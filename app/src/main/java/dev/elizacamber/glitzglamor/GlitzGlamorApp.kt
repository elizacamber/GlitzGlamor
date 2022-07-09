package dev.elizacamber.glitzglamor

import android.app.Application
import android.content.Context
import dev.elizacamber.glitzglamor.database.CitiesDatabase

class GlitzGlamorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Holder.provideDb(applicationContext)
    }
}

object Holder {
    lateinit var database: CitiesDatabase
        private set

    fun provideDb(context: Context) {
        database = CitiesDatabase.getInstance(context)
    }
}