package br.com.raveline.todo2021.data.db.converter

import androidx.room.TypeConverter
import br.com.raveline.todo2021.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}