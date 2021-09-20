package br.com.raveline.todo2021.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.data.model.Priority
import br.com.raveline.todo2021.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private var updateBinding: FragmentUpdateBinding? = null
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        updateBinding = FragmentUpdateBinding.inflate(inflater, container, false)
        updateUI(args.toDoItem)


        setHasOptionsMenu(true)

        return updateBinding!!.root
    }

    private fun updateUI(toDoItem: ToDoItemEntity) {
        updateBinding.apply {
            this!!.editTextFragmentUpdateTitle.setText(toDoItem.title)
            this.editTextTextMultiLineFragmentUpdateDescription.setText(toDoItem.description)
            this.spinnerFragmentUpdatePriority.setSelection(parsePriority(toDoItem.priority))
        }
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 1
            Priority.MEDIUM -> 2
            Priority.LOW -> 3
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_delete_update, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateBinding = null
    }

}