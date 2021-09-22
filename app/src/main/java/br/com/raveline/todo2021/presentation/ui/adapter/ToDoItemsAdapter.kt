package br.com.raveline.todo2021.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.databinding.ItemAdapterTodoBinding
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
    }

    override fun getItemCount(): Int = toDoItemsList.size

    inner class MyViewHolder(private val binding: ItemAdapterTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoItem: ToDoItemEntity) {
            binding.toDoData = toDoItem
            binding.executePendingBindings()

        }

    }

    fun setRecipeData(newData: List<ToDoItemEntity>) {
        val toDoDiffUtils = ToDoDiffUtils(toDoItemsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(toDoDiffUtils)
        toDoItemsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}