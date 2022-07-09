package dev.elizacamber.glitzglamor.database

import androidx.room.*

@Entity(tableName = "cities_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "times_visited")
    val timesVisited: Int
)

@Entity(tableName = "visits_table")
data class Visit(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo
    val cityId: Long,
    @ColumnInfo
    val start_date: Long,
    @ColumnInfo
    val end_date: Long,
)

data class CityWithVisitDetails(
    @Embedded val city: City,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val visits: List<Visit>
)

val dummyCityList = listOf(
    City(0, "Chicago", "United States of America", 1),
    City(1, "NYC", "United States of America", 6),
    City(2, "Madrid", "Spain", 4),
    City(3, "Santorini", "Greece", 2),
    City(4, "London", "United Kingdom", 40)
)