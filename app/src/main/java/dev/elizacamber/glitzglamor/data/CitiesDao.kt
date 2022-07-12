package dev.elizacamber.glitzglamor.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(visit: Visit): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(city: City): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(visit: Visit): Int

    @Query("SELECT * FROM cities_table")
    fun getAllCities(): Flow<List<City>>

    @Transaction
    @Query("SELECT * FROM cities_table")
    fun getAllCitiesWithVisitDetails(): Flow<List<CityWithVisitDetails>>

    @Transaction
    @Query("SELECT * FROM cities_table WHERE cityId = :key")
    fun getCityWithVisitDetails(key: Long): Flow<CityWithVisitDetails>

    @Delete
    fun deleteVisit(visit: Visit): Int

    @Delete
    fun deleteCity(city: City): Int
}