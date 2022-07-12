package dev.elizacamber.glitzglamor.data

import androidx.room.*
import dev.elizacamber.glitzglamor.R


@Entity(tableName = "cities_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "country_flag")
    val flagRes: Int,
    @ColumnInfo(name = "times_visited")
    val timesVisited: Int
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
    City(1, "Chicago", "United States of America", R.drawable.us, 1),
    City(2, "Santorini", "Greece", R.drawable.gr, 2),
    City(3, "London", "United Kingdom", R.drawable.gb, 9),
    City(4, "New York", "United States of America", R.drawable.us, 4)
)

val dummyCityWithDetails = listOf(
    CityWithVisitDetails(
        City(0, "Chicago", "United States of America", R.drawable.us, 1),
        listOf(
            Visit(0, 0, 1658188800000, 1661385600000)
        )
    ),
    CityWithVisitDetails(
        City(1, "Santorini", "Greece", R.drawable.gr, 2),
        listOf(
            Visit(1, 1, 1631318400000, 1632009600000),
            Visit(2, 1, 1641254400000, 1653782400000)
        )
    ),
    CityWithVisitDetails(
        City(2, "London", "United Kingdom", R.drawable.gb, 9),
        listOf(
            Visit(3, 2, 1480032000000, 1480204800000),
            Visit(4, 2, 1488672000000, 1489017600000),
            Visit(5, 2, 1500854400000, 1500940800000),
            Visit(6, 2, 1520035200000, 1520208000000),
            Visit(7, 2, 1529020800000, 1529193600000),
            Visit(8, 2, 1543795200000, 1543968000000),
            Visit(9, 2, 1562371200000, 1562716800000),
            Visit(10, 2, 1632441600000, 1632960000000),
            Visit(11, 2, 1656547200000, 1656979200000)
        )
    ),
    CityWithVisitDetails(
        City(3, "New York", "United States of America", R.drawable.us, 4),
        listOf(
            Visit(12, 3, 1521676800000, 1521936000000),
            Visit(13, 3, 1535241600000, 1536019200000),
            Visit(14, 3, 1541721600000, 1542412800000),
            Visit(15, 3, 1534982400000, 1535414400000)
        )
    ),
)