package com.example.suivisportsimple.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.suivisportsimple.R
import java.time.LocalDate

/**
 * Displays a traditional calendar widget using [CalendarView]. When the user selects a date the
 * app navigates to the entry screen for that date. A button in the top app bar opens the
 * dashboard screen.
 */
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.main_title)) },
                actions = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(
                            imageVector = Icons.Filled.BarChart,
                            contentDescription = stringResource(id = R.string.dashboard_title)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Use Android's CalendarView since Compose has no built‑in calendar component.
        AndroidView(
            modifier = Modifier.padding(paddingValues).padding(horizontal = 8.dp),
            factory = { context ->
                CalendarView(context).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        // month is zero‑based in CalendarView
                        val date = LocalDate.of(year, month + 1, dayOfMonth)
                        navController.navigate("entry/${date}")
                    }
                }
            }
        )
    }
}