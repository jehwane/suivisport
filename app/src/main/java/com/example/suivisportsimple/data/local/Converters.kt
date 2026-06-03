package com.example.suivisportsimple.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * Room type converters for data types not natively supported. Converts [LocalDate]
 * to and from its ISO string representation. Using ISO format ensures ordering
 * and parsing consistency across locales and time zones.
 */
class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)
}