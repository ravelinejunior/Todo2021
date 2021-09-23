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

    @Query("SELECT * FROM TODO_TABLE ORDER BY id DESC")
    fun readToDoTable(): Flow<List<ToDoItemEntity>>

    @Query("SELECT * FROM TODO_TABLE WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchToDoItem(searchQuery: String): Flow<List<ToDoItemEntity>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<ToDoItemEntity>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<ToDoItemEntity>>

    @Delete()
    suspend fun deleteToDoItem(toDoItemEntity: ToDoItemEntity)

    @Query("DELETE FROM TODO_TABLE")
    suspend fun deleteToDoTable()

}