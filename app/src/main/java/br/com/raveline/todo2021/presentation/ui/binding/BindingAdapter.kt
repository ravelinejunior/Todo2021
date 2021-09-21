package br.com.raveline.todo2021.presentation.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity

class BindingAdapter {
    companion object {
        @BindingAdapter("android:emptyDataBase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: LiveData<List<ToDoItemEntity>>) {

            when (emptyDatabase.value?.isEmpty()) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.GONE
            }
        }
    }
}