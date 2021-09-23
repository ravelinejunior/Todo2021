package br.com.raveline.todo2021.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.databinding.FragmentTodoListBinding
import br.com.raveline.todo2021.presentation.helpers.SwipeToDelete
import br.com.raveline.todo2021.presentation.ui.adapter.ToDoItemsAdapter
import br.com.raveline.todo2021.presentation.viewmodel.ToDoViewModel
import br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory.ToDoViewModelFactory
import br.com.raveline.todo2021.utils.observeOnce
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mBinding: FragmentTodoListBinding

    @Inject
    lateinit var mFactory: ToDoViewModelFactory

    private lateinit var mViewModel: ToDoViewModel

    @Inject
    lateinit var mAdapter: ToDoItemsAdapter

    private var searchView: SearchView? = null

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

        backPressedSearchView()

    }

    private fun getItemsFromDb() {
        lifecycleScope.launchWhenStarted {
            mViewModel.toDoReadLiveData.observe(viewLifecycleOwner, { toDoItems ->
                try {
                    mAdapter.setToDoItemData(toDoItems)
                } catch (e: Exception) {
                    Log.i("TAGFRAGMENT", e.message.toString())
                }
            })
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lifecycleScope.launch {
                    val itemToDelete = mAdapter.toDoItemsList[viewHolder.absoluteAdapterPosition]
                    mViewModel.deleteData(itemToDelete)

                    view?.let {
                        Snackbar.make(it, "Tarefa deletada com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Desfazer") {
                                lifecycleScope.launch {
                                    mViewModel.insertData(itemToDelete)
                                }
                            }
                            .show()
                    }
                }

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun setupRecyclerView() {
        mBinding.recyclerViewFragmentTodo.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            setItemViewCacheSize(25)
            itemAnimator = LandingAnimator(OvershootInterpolator(1f)).apply {
                addDuration = 300
                moveDuration = 500
            }
            adapter = mAdapter
        }

        swipeToDelete(mBinding.recyclerViewFragmentTodo)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemDeleteAllMenuTodoId -> displayDialogDeleteItems()
            R.id.menuItemPriorityHighMenuTodoId -> mViewModel.sortByHighPriorityLiveData.observe(
                viewLifecycleOwner,
                { items ->
                    mAdapter.setToDoItemData(items)
                    mBinding.recyclerViewFragmentTodo.scheduleLayoutAnimation()
                })
            R.id.menuItemPriorityLowMenuTodoId -> mViewModel.sortByLowPriorityLiveData.observe(
                viewLifecycleOwner,
                { items ->
                    mAdapter.setToDoItemData(items)
                    mBinding.recyclerViewFragmentTodo.scheduleLayoutAnimation()
                })
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

        val searchMenu = menu.findItem(R.id.itemSearchMenuTodoId)
        searchView = searchMenu.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Digite ao menos 3 caracteres"
        searchView?.onActionViewCollapsed()
        searchView?.isIconified = true

        searchView?.setOnQueryTextListener(this)
    }

    private fun backPressedSearchView() {
        if (searchView != null) {
            requireActivity().onBackPressedDispatcher.addCallback(requireActivity(),
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        searchView?.onActionViewCollapsed()
                        searchView?.isIconified = true
                    }
                })
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchToDoItem(query)
            return true
        } else return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchToDoItem(newText)
        }
        return true
    }

    private fun searchToDoItem(query: String) {
        val searchQuery = "%$query%"
        mViewModel.searchToDoItem(searchQuery).observeOnce(viewLifecycleOwner, { items ->
            items?.let {
                mAdapter.setToDoItemData(items)
                mBinding.recyclerViewFragmentTodo.scheduleLayoutAnimation()
            }
        })
    }
}