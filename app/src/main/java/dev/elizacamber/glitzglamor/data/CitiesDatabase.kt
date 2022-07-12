package dev.elizacamber.glitzglamor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class, Visit::class], version = 1, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {

    abstract val citiesDao: CitiesDao

    companion object {

        @Volatile
        private var INSTANCE: CitiesDatabase? = null

        fun getInstance(context: Context): CitiesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CitiesDatabase::class.java,
                        "cities_visit_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}