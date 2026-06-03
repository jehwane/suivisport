package com.example.suivisportsimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.room.Room
import com.example.suivisportsimple.data.ActivityRepository
import com.example.suivisportsimple.data.local.AppDatabase
import com.example.suivisportsimple.navigation.AppNavHost
import com.example.suivisportsimple.ui.theme.SuiviSportSimpleTheme

/**
 * Entry point for the application. Creates the Room database and repository once and
 * passes them down to the composables. The UI content is defined in [AppNavHost].
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lazily build the database and repository. They will survive recompositions and
        // are tied to the Activity lifecycle.
        val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "activity_db"
            ).build()
        }
        val repository by lazy { ActivityRepository(database.activityRecordDao()) }

        setContent {
            SuiviSportSimpleTheme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Provide repository to the navigation graph.
                    AppNavHost(repository = repository)
                }
            }
        }
    }
}