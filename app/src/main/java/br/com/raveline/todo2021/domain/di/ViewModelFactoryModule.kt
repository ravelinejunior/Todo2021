package br.com.raveline.todo2021.domain.di

import android.app.Application
import br.com.raveline.todo2021.data.datasource.local_datasource.LocalDataSource
import br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory.ToDoViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideToDoViewModelFactory(
        application: Application,
        localDataSource: LocalDataSource
    ): ToDoViewModelFactory {
        return ToDoViewModelFactory(application, localDataSource)
    }
}