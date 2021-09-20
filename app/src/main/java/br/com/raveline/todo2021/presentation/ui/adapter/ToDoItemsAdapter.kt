package br.com.raveline.todo2021.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.data.model.Priority
import br.com.raveline.todo2021.databinding.ItemAdapterTodoBinding
import br.com.raveline.todo2021.presentation.ui.fragment.TodoListFragmentDirections
import br.com.raveline.todo2021.utils.ToDoDiffUtils

class ToDoItemsAdapter : RecyclerView.Adapter<ToDoItemsAdapter.MyViewHolder>() {

    private var toDoItemsList = emptyList<ToDoItemEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mBinding =
            ItemAdapterTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoItem = toDoItemsList[position]
        holder.bind(toDoItem)

        holder.itemView.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToUpdateFragment(toDoItem)
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int = toDoItemsList.size

    inner class MyViewHolder(private val binding: ItemAdapterTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoItem: ToDoItemEntity) {
            binding.textViewTitle.text = toDoItem.title
            binding.textViewDescription.text = toDoItem.description
            getPriority(toDoItem)
        }

        private fun getPriority(toDoItem: ToDoItemEntity) {
            when (toDoItem.priority) {
                Priority.LOW -> {
                    binding.cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardViewPriority.context,
                            R.color.lightGreen
                        )
                    )
                }

                Priority.MEDIUM -> {
                    binding.cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardViewPriority.context,
                            R.color.blue
                        )
                    )
                }

                Priority.HIGH -> {
                    binding.cardViewPriority.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardViewPriority.context,
                            R.color.red
                        )
                    )
                }
            }
        }

    }

    fun setRecipeData(newData: List<ToDoItemEntity>) {
        val toDoDiffUtils = ToDoDiffUtils(toDoItemsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(toDoDiffUtils)
        toDoItemsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}