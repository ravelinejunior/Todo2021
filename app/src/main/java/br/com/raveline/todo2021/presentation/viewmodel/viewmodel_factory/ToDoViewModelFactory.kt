package br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.todo2021.data.datasource.local_datasource.LocalDataSource
import br.com.raveline.todo2021.presentation.viewmodel.ToDoViewModel

class ToDoViewModelFactory(
    private val application: Application,
    private val localDataSource: LocalDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoViewModel(
            application, localDataSource
        ) as T
    }
}