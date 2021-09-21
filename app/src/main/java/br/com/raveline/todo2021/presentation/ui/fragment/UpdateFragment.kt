package br.com.raveline.todo2021.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.databinding.FragmentUpdateBinding
import br.com.raveline.todo2021.presentation.viewmodel.SharedViewModel
import br.com.raveline.todo2021.presentation.viewmodel.SharedViewModel.Companion.hideKeyboard
import br.com.raveline.todo2021.presentation.viewmodel.ToDoViewModel
import br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory.ToDoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private var updateBinding: FragmentUpdateBinding? = null
    private val args by navArgs<UpdateFragmentArgs>()

    @Inject
    lateinit var factory: ToDoViewModelFactory

    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var toDoViewModel: ToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoViewModel = ViewModelProvider(this, factory)[ToDoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        updateBinding = FragmentUpdateBinding.inflate(inflater, container, false)
        updateUI(args.toDoItem)

        updateBinding!!.fabFragmentUpdate.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                updateToDoItem(args.toDoItem)
            }
        }

        setHasOptionsMenu(true)

        return updateBinding!!.root
    }

    private suspend fun updateToDoItem(toDoItem: ToDoItemEntity) {
        val nTitle = updateBinding?.editTextFragmentUpdateTitle?.text.toString()
        val nDescription =
            updateBinding?.editTextTextMultiLineFragmentUpdateDescription?.text.toString()
        val nPriority = updateBinding?.spinnerFragmentUpdatePriority?.selectedItem.toString()

        val validation = sharedViewModel.verifyDataFromUser(nTitle, nDescription)

        if (validation) {
            val updatedItem = ToDoItemEntity(
                toDoItem.id,
                nTitle,
                sharedViewModel.parseStringInPriority(nPriority),
                nDescription
            )

            toDoViewModel.updateData(updatedItem)
            hideKeyboard()

            view?.let {
                Snackbar.make(it, "Tarefa atualizada com sucesso!", Snackbar.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.action_updateFragment_to_todoListFragment)
        } else {
            view?.let {
                Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

    }

    private fun updateUI(toDoItem: ToDoItemEntity) {
        updateBinding.apply {
            this!!.editTextFragmentUpdateTitle.setText(toDoItem.title)
            this.editTextTextMultiLineFragmentUpdateDescription.setText(toDoItem.description)
            this.spinnerFragmentUpdatePriority.setSelection(
                sharedViewModel.parsePriorityToInt(
                    toDoItem.priority
                )
            )
            this.spinnerFragmentUpdatePriority.onItemSelectedListener = sharedViewModel.listener
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.deleteItemMenuDeleteId) {
            displayDialogDeleteItems()
        }

        return super.onOptionsItemSelected(item)
    }



    private fun displayDialogDeleteItems() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setCancelable(false)
            .setTitle("Deletar Tarefa")
            .setMessage("Deseja deletar a tarefa cadastrada?")
            .setIcon(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_coronavirus_24
                )
            )
            .setNegativeButton(
                "Não"
            ) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Sim") { _, _ ->
                lifecycleScope.launch {
                    toDoViewModel.deleteData(args.toDoItem)
                    view?.let {
                        Snackbar.make(it, "Tarefa deletada com sucesso!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    hideKeyboard()
                    findNavController().navigate(R.id.action_updateFragment_to_todoListFragment)
                }
            }

        val alertDialog = dialog.create()
        alertDialog.show()

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