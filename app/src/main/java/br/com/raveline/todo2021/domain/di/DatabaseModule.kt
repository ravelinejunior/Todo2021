package br.com.raveline.todo2021.domain.di

import android.app.Application
import androidx.room.Room
import br.com.raveline.todo2021.data.db.ToDoDatabase
import br.com.raveline.todo2021.data.db.dao.ToDoListDao
import br.com.raveline.todo2021.utils.Constants.TODO_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomInstance(application: Application): ToDoDatabase {
        return Room.databaseBuilder(
            application,
            ToDoDatabase::class.java,
            TODO_DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideToDoListDao(toDoDatabase: ToDoDatabase): ToDoListDao {
        return toDoDatabase.toDoListDao()
    }

}