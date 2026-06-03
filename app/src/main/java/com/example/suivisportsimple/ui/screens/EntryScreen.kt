package com.example.suivisportsimple.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.suivisportsimple.R
import com.example.suivisportsimple.data.ActivityRepository
import com.example.suivisportsimple.ui.viewmodels.EntryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Additional imports for layout primitives
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width

/**
 * Screen allowing the user to validate a day, select one or more activities and add a
 * short note. The current date is displayed in the top app bar. When the save button
 * is pressed the data is persisted and the screen pops back to the previous destination.
 */
@Composable
fun EntryScreen(navController: NavController, repository: ActivityRepository, date: LocalDate) {
    val viewModel: EntryViewModel = viewModel(
        factory = EntryViewModel.provideFactory(repository, date)
    )
    val selectedActivities = viewModel.selectedActivities.collectAsState().value
    val note = viewModel.note.collectAsState().value

    val availableActivities = listOf("Course", "Marche", "Vélo", "Natation", "Musculation")
    val dateLabel = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = dateLabel) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = stringResource(id = R.string.activities_label))
            Spacer(modifier = Modifier.height(8.dp))
            availableActivities.forEach { activity ->
                RowActivityCheckbox(
                    activity = activity,
                    checked = selectedActivities.contains(activity),
                    onCheckedChange = { viewModel.toggleActivity(activity) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { viewModel.updateNote(it) },
                label = { Text(stringResource(id = R.string.note_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.saveRecord()
                navController.popBackStack()
            }) {
                Text(text = stringResource(id = R.string.save_button))
            }
        }
    }
}

@Composable
private fun RowActivityCheckbox(
    activity: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = activity)
    }
}