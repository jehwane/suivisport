package com.example.suivisportsimple.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.suivisportsimple.R
import com.example.suivisportsimple.data.ActivityRepository
import com.example.suivisportsimple.ui.viewmodels.DashboardViewModel

/**
 * Displays aggregated statistics about the user's activity history. Shows the number of
 * days with activities as well as a breakdown per activity. If no data is available a
 * friendly message is shown. A back arrow allows returning to the previous screen.
 */
@Composable
fun DashboardScreen(navController: NavController, repository: ActivityRepository) {
    val viewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModel.provideFactory(repository)
    )
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.dashboard_title)) },
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
        if (uiState.totalDays == 0) {
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(text = stringResource(id = R.string.stats_no_data))
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(text = "Nombre de jours actifs : ${uiState.totalDays}")
                Spacer(modifier = Modifier.height(8.dp))
                uiState.activityCounts.forEach { (activity, count) ->
                    Text(text = "$activity : $count")
                }
            }
        }
    }
}