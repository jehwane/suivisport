package com.example.suivisportsimple.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Data access object for [ActivityRecord] entities. Provides reactive flows for
 * retrieving all records or a single record by date. The dates are stored as
 * [LocalDate] and converted to strings via [Converters].
 */
@Dao
interface ActivityRecordDao {

    @Query("SELECT * FROM activity_records ORDER BY date ASC")
    fun getAllRecords(): Flow<List<ActivityRecord>>

    @Query("SELECT * FROM activity_records WHERE date = :date LIMIT 1")
    fun getRecord(date: LocalDate): Flow<ActivityRecord?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ActivityRecord)
}