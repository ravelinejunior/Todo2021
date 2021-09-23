package br.com.raveline.todo2021.data.datasource.local_datasource

import br.com.raveline.todo2021.data.db.dao.ToDoListDao
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val toDoListDao: ToDoListDao) {
    fun readToDoListData(): Flow<List<ToDoItemEntity>> = toDoListDao.readToDoTable()

    fun searchToDoItem(searchQuery: String): Flow<List<ToDoItemEntity>> =
        toDoListDao.searchToDoItem(searchQuery)

    suspend fun insertToDoItem(entity: ToDoItemEntity) {
        toDoListDao.insertItem(entity)
    }

    suspend fun updateToDoItem(entity: ToDoItemEntity) {
        toDoListDao.updateItemData(entity)
    }

    suspend fun deleteToDoItem(entity: ToDoItemEntity) {
        toDoListDao.deleteToDoItem(entity)
    }

    suspend fun deleteAllItemsFromDatabase() {
        toDoListDao.deleteToDoTable()
    }

    fun sortByHighPriority(): Flow<List<ToDoItemEntity>> {
        return toDoListDao.sortByHighPriority()
    }

    fun sortByLowPriority(): Flow<List<ToDoItemEntity>> {
        return toDoListDao.sortByLowPriority()
    }


}