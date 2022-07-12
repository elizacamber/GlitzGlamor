package dev.elizacamber.glitzglamor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

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

val dummyVisitsList = listOf(
    Visit(0, 1, 1658188800, 1661385600),
    Visit(1, 2, 1631318400, 1632009600),
    Visit(2, 2, 1641254400, 1653782400),
    Visit(3, 3, 1480032000, 1480204800),
    Visit(4, 3, 1488672000, 1489017600),
    Visit(5, 3, 1500854400, 1500940800),
    Visit(6, 3, 1520035200, 1520208000),
    Visit(7, 3, 1529020800, 1529193600),
    Visit(8, 3, 1543795200, 1543968000),
    Visit(9, 3, 1562371200, 1562716800),
    Visit(10, 3, 1632441600, 1632960000),
    Visit(11, 3, 1656547200, 1656979200),
    Visit(12, 4, 1521676800, 1521936000),
    Visit(13, 4, 1535241600, 1536019200),
    Visit(14, 4, 1541721600, 1542412800),
    Visit(15, 4, 1534982400, 1535414400)
)

fun Long.epochToReadableDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}