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

data class NewVisit(
    val start_date: Long?,
    val end_date: Long?,
)

val NewVisitListComparator = Comparator<NewVisit> { left, right ->
    left.start_date!!.compareTo(right.start_date!!)
}

val dummyVisitsList = listOf(
    Visit(0, 1, 1658188800000, 1661385600000),
    Visit(1, 2, 1631318400000, 1632009600000),
    Visit(2, 2, 1641254400000, 1653782400000),
    Visit(3, 3, 1480032000000, 1480204800000),
    Visit(4, 3, 1488672000000, 1489017600000),
    Visit(5, 3, 1500854400000, 1500940800000),
    Visit(6, 3, 1520035200000, 1520208000000),
    Visit(7, 3, 1529020800000, 1529193600000),
    Visit(8, 3, 1543795200000, 1543968000000),
    Visit(9, 3, 1562371200000, 1562716800000),
    Visit(10, 3, 1632441600000, 1632960000000),
    Visit(11, 3, 1656547200000, 1656979200000),
    Visit(12, 4, 1521676800000, 1521936000000),
    Visit(13, 4, 1535241600000, 1536019200000),
    Visit(14, 4, 1541721600000, 1542412800000),
    Visit(15, 4, 1534982400000, 1535414400000)
)

fun Long.epochToReadableDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}