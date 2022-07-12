package dev.elizacamber.glitzglamor

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.elizacamber.glitzglamor.data.NewVisit
import dev.elizacamber.glitzglamor.data.epochToReadableDate
import dev.elizacamber.glitzglamor.data.getCountryList
import java.util.*


@Composable
fun AddNew(navController: NavController) {
    var cityName by remember { mutableStateOf("") }
    val visits = remember { mutableStateListOf<NewVisit>() }

    Column(Modifier.fillMaxSize()) {
        AddNewBanner()
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text(text = "Name") })
        Spacer(modifier = Modifier.height(16.dp))
        CountryPicker()
        Spacer(modifier = Modifier.height(32.dp))
        VisitsTitle()
        Spacer(modifier = Modifier.height(16.dp))
        VisitsContainer(visits)
    }
}

@Composable
fun AddNewBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add new city",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun CountryPicker() {
    var expanded by remember { mutableStateOf(false) }
    val countryList = getCountryList()
    var selectedCountry by remember { mutableStateOf(countryList.find { it.name == "United States of America" }) }

    OutlinedTextField(
        value = selectedCountry?.name ?: "Select country",
        onValueChange = {},
        Modifier.clickable { expanded = !expanded },
        readOnly = true,
        enabled = false,
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = LocalContentColor.current.copy(alpha = 0.8f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.background
        )
    )
    if (expanded) {
        Dialog(onDismissRequest = { expanded = !expanded }) {
            Surface(modifier = Modifier.padding(16.dp), shape = RoundedCornerShape(16.dp)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = getCountryList(), itemContent = { country ->
                        Row(Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = country.flag),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = country.name, Modifier.clickable {
                                selectedCountry = country
                                expanded = false
                            })
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun VisitsTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Visits".uppercase(),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun VisitsContainer(visits: MutableList<NewVisit>) {
    var visit by remember { mutableStateOf<NewVisit?>(null) }
    val now = Calendar.getInstance()

    val startDatePicker = DatePickerDialog(
        LocalContext.current,
        { _, year, month, day ->
            val epoch = GregorianCalendar(year, month, day).timeInMillis
            visit = if (visit == null) NewVisit(epoch, null) else {
                visit!!.copy(start_date = epoch)
            }
        },
        now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH]
    )
    val endDatePicker = DatePickerDialog(
        LocalContext.current,
        { view, year, month, day ->
            val epoch = GregorianCalendar(year, month, day).timeInMillis
            visit = if (visit == null) NewVisit(null, epoch) else {
                visit!!.copy(end_date = epoch)
            }
        },
        now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH]
    )

    Column {
        if (visits.isNotEmpty()) {
            visits.forEachIndexed { index, visit ->
                NewVisitRow(visit = visit, index = index) {
                    visits.removeAt(index)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Start date: ${visit?.start_date?.epochToReadableDate() ?: ""}",
                Modifier.clickable {
                    startDatePicker.show()
                })
            Text(
                text = "End date: ${visit?.end_date?.epochToReadableDate() ?: ""}",
                Modifier.clickable {
                    endDatePicker.show()
                })
        }
        Spacer(modifier = Modifier.height(34.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            IconButton(onClick = {
                if (visit != null && visit!!.start_date != null && visit!!.end_date != null) {
                    visits.add(visit!!)
                    visit = null
                }
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add new visit",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun NewVisitRow(visit: NewVisit, index: Int, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = "${index + 1}. ${visit.start_date!!.epochToReadableDate()} - ${visit.end_date!!.epochToReadableDate()}"
            )
            IconButton(onClick = { onClick.invoke() }) {
                Icon(Icons.Default.Delete, "Delete row")
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .width(0.5.dp)
        )
    }
}

@Composable
@Preview
fun AddNewPreview() {
    AddNew(navController = rememberNavController())
}