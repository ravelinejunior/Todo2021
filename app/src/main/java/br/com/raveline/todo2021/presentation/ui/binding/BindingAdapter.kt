package br.com.raveline.todo2021.presentation.ui.binding

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.data.model.Priority
import br.com.raveline.todo2021.presentation.ui.fragment.TodoListFragmentDirections

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

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setSelection(1)
                Priority.MEDIUM -> view.setSelection(2)
                Priority.LOW -> view.setSelection(3)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardViewPriority: CardView, value: Priority) {

            when (value) {
                Priority.LOW -> {
                    cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardViewPriority.context,
                            R.color.lightGreen
                        )
                    )
                }

                Priority.MEDIUM -> {
                    cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardViewPriority.context,
                            R.color.yellow
                        )
                    )
                }

                Priority.HIGH -> {
                    cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardViewPriority.context,
                            R.color.red
                        )
                    )
                }
            }
        }

        @BindingAdapter("sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view:ConstraintLayout,currentItem:ToDoItemEntity){
            view.setOnClickListener {
                val action = TodoListFragmentDirections.actionTodoListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}