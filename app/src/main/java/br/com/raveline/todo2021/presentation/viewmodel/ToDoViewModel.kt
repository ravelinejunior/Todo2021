package br.com.raveline.todo2021.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.com.raveline.todo2021.data.datasource.local_datasource.LocalDataSource
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ToDoViewModel @Inject constructor(
    application: Application,
    private val localDataSource: LocalDataSource
) :
    AndroidViewModel(application) {

    private val toDoMutableLiveData: MutableLiveData<List<ToDoItemEntity>> = MutableLiveData()
    val toDoLiveData: LiveData<List<ToDoItemEntity>> = toDoMutableLiveData

    val toDoReadLiveData = localDataSource.readToDoListData().asLiveData()

    suspend fun insertData(toDoItemData: ToDoItemEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            localDataSource.insertToDoItem(toDoItemData)
        }

    suspend fun updateData(entity: ToDoItemEntity) = viewModelScope.launch(Dispatchers.IO) {
        localDataSource.updateToDoItem(entity)
    }

    suspend fun deleteData(toDoItemData: ToDoItemEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            localDataSource.deleteToDoItem(toDoItemData)
        }

    suspend fun deleteAllData() =
        viewModelScope.launch(Dispatchers.IO) {
            localDataSource.deleteAllItemsFromDatabase()
        }


}