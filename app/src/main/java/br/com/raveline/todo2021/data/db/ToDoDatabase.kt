package br.com.raveline.todo2021.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.raveline.todo2021.data.db.converter.Converter
import br.com.raveline.todo2021.data.db.dao.ToDoListDao
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity

@Database(
    entities = [ToDoItemEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
}