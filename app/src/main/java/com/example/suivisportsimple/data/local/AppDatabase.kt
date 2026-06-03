package com.example.suivisportsimple.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Central Room database for the application. Declares the single entity and type
 * converter required. Bump the version number if the schema changes.
 */
@Database(
    entities = [ActivityRecord::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityRecordDao(): ActivityRecordDao
}