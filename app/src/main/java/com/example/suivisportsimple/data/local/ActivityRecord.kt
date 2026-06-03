package com.example.suivisportsimple.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Entity representing an activity record for a specific date. The list of activities is stored
 * as a comma‑separated string to avoid requiring a separate join table. A type converter
 * translates between [LocalDate] and its string representation for Room.
 */
@Entity(tableName = "activity_records")
data class ActivityRecord(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: LocalDate,
    /** Comma‑separated list of activities performed on this date. */
    @ColumnInfo(name = "activities")
    val activities: String,
    /** Optional note associated with this record. */
    @ColumnInfo(name = "note")
    val note: String? = null,
)