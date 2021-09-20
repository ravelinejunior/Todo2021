package br.com.raveline.todo2021.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raveline.todo2021.data.datasource.local_datasource.LocalDataSource
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.data.model.Priority
import br.com.raveline.todo2021.utils.LiveDataTestUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import kotlin.jvm.Throws

@RunWith(MockitoJUnitRunner::class)
class ToDoViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var initRule: MockitoRule = MockitoJUnit.rule()

    private val application = Mockito.mock(Application::class.java)

    private val toDoFakeRepository: LocalDataSource = Mockito.mock(LocalDataSource::class.java)

    private var toDoViewModel: ToDoViewModel? = null

    private lateinit var toDoItems: ArrayList<ToDoItemEntity>

    @Before
    fun setUp() {
        toDoItems.add(ToDoItemEntity(0, "Teste 1", Priority.MEDIUM, "Description 1"))
        toDoItems.add(ToDoItemEntity(1, "Teste 2", Priority.HIGH, "Description 2"))
        toDoItems.add(ToDoItemEntity(2, "Teste 3", Priority.LOW, "Description 3"))
        toDoItems.add(ToDoItemEntity(3, "Teste 4", Priority.MEDIUM, "Description 4"))

        Mockito.doReturn(toDoItems).`when`(toDoFakeRepository).readToDoListData()

        toDoViewModel = ToDoViewModel(application,toDoFakeRepository)
    }

    @Test
    @Throws(InterruptedException::class)
    suspend fun getToDoReadLiveDataIfExistsData() {
        //Given
        toDoViewModel?.toDoReadLiveData!!.value
        toDoViewModel?.insertData(toDoItems[0])

        //When
        val value = toDoViewModel?.let {

        }

        //Then
    }

}