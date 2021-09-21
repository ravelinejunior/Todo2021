package br.com.raveline.todo2021.data.db.dao

import androidx.room.*
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(toDoItemEntity: ToDoItemEntity)

    @Update
    suspend fun updateItemData(toDoItem: ToDoItemEntity)

    @Query("SELECT * FROM TODO_TABLE ORDER BY id")
    fun readToDoTable(): Flow<List<ToDoItemEntity>>

    @Delete()
    suspend fun deleteToDoItem(toDoItemEntity: ToDoItemEntity)

    @Query("DELETE FROM TODO_TABLE")
    suspend fun deleteToDoTable()

}