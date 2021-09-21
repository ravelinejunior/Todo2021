package br.com.raveline.todo2021.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.databinding.FragmentTodoListBinding
import br.com.raveline.todo2021.presentation.ui.adapter.ToDoItemsAdapter
import br.com.raveline.todo2021.presentation.viewmodel.ToDoViewModel
import br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory.ToDoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var mBinding: FragmentTodoListBinding

    @Inject
    lateinit var mFactory: ToDoViewModelFactory

    private lateinit var mViewModel: ToDoViewModel

    @Inject
    lateinit var mAdapter: ToDoItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this, mFactory)[ToDoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentTodoListBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.mToDoViewModel = mViewModel

        //Set menu
        setHasOptionsMenu(true)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getItemsFromDb()

        mBinding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_addFragment)
        }

    }

    private fun getItemsFromDb() {
        lifecycleScope.launchWhenStarted {
            mViewModel.toDoReadLiveData.observe(viewLifecycleOwner, { toDoItems ->
                try {
                    mAdapter.setRecipeData(toDoItems)

                    /* if (toDoItems.isNotEmpty()) {
                         mBinding.apply {
                             textViewFragmentTodoNoData.visibility = GONE
                             imageViewFragmentTodoNoData.visibility = GONE
                             recyclerViewFragmentTodo.visibility = VISIBLE
                         }

                         mAdapter.setRecipeData(toDoItems)

                     } else {
                         mBinding.apply {
                             textViewFragmentTodoNoData.visibility = VISIBLE
                             imageViewFragmentTodoNoData.visibility = VISIBLE
                             mAdapter.setRecipeData(toDoItems)
                         }
                     }*/
                } catch (e: Exception) {
                    Log.i("TAGFRAGMENT", e.message.toString())
                }
            })
        }
    }

    private fun setupRecyclerView() {
        mBinding.recyclerViewFragmentTodo.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            setItemViewCacheSize(25)
            adapter = mAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemDeleteAllMenuTodoId) {
            displayDialogDeleteItems()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun displayDialogDeleteItems() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setCancelable(false)
            .setTitle("Deletar Tarefas")
            .setMessage("Deseja deletar todas as tarefas cadastradas?")
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
                    mViewModel.deleteAllData()
                    view?.let {
                        Snackbar.make(it, "Tarefas deletadas com sucesso!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        val alertDialog = dialog.create()
        alertDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_todo_list, menu)
    }

}