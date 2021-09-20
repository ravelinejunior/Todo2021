package br.com.raveline.todo2021.domain.di

import br.com.raveline.todo2021.presentation.ui.adapter.ToDoItemsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideItemAdapter(): ToDoItemsAdapter {
        return ToDoItemsAdapter()
    }
}