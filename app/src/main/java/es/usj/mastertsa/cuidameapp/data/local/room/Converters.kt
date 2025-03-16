package es.usj.mastertsa.cuidameapp.data.local.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    @TypeConverter
    fun fromStartDay(value: String): Date? {
        return dateFormat.parse(value)
    }

    @TypeConverter
    fun fromStartHour(value: String): Date? {
        return timeFormat.parse(value)
    }

    @TypeConverter
    fun dateToStartDay(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun dateToStartHour(date: Date?): String? {
        return date?.let { timeFormat.format(it) }
    }
}