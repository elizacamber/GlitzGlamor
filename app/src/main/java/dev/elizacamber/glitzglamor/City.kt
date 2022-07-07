package dev.elizacamber.glitzglamor

data class City(val name: String, val country: String, val timesVisited: Int)

val dummyCityList = listOf(
    City("Chicago", "United States of America", 1),
    City("NYC", "United States of America", 6),
    City("Madrid", "Spain", 4),
    City("Santorini", "Greece", 2),
    City("London", "United Kingdom", 40)
)