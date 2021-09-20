package br.com.raveline.todo2021.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.raveline.todo2021.data.model.Priority
import br.com.raveline.todo2021.utils.Constants.TODO_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TODO_TABLE_NAME)
data class ToDoItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    var title:String,
    var priority:Priority,
    var description:String,
) : Parcelable