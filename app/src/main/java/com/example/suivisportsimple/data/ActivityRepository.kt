package com.example.suivisportsimple.data

import com.example.suivisportsimple.data.local.ActivityRecord
import com.example.suivisportsimple.data.local.ActivityRecordDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository layer that abstracts access to the [ActivityRecordDao]. It exposes
 * functions returning reactive [Flow]s for use in the UI layer and provides
 * simple methods to upsert activity records. Activities are stored internally
 * as a comma‑separated string; callers use [List] representations instead.
 */
class ActivityRepository(private val dao: ActivityRecordDao) {
    fun getAllRecords(): Flow<List<ActivityRecord>> = dao.getAllRecords()

    fun getRecord(date: LocalDate): Flow<ActivityRecord?> = dao.getRecord(date)

    suspend fun saveRecord(date: LocalDate, activities: List<String>, note: String?) {
        val record = ActivityRecord(
            date = date,
            activities = activities.joinToString(separator = ","),
            note = note
        )
        dao.insert(record)
    }
}