package br.com.raveline.todo2021.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.db.entity.ToDoItemEntity
import br.com.raveline.todo2021.databinding.FragmentAddBinding
import br.com.raveline.todo2021.presentation.viewmodel.SharedViewModel
import br.com.raveline.todo2021.presentation.viewmodel.SharedViewModel.Companion.hideKeyboard
import br.com.raveline.todo2021.presentation.viewmodel.ToDoViewModel
import br.com.raveline.todo2021.presentation.viewmodel.viewmodel_factory.ToDoViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val mSharedViewModel: SharedViewModel by viewModels()

    private lateinit var addBinding: FragmentAddBinding

    private lateinit var toDoViewModel: ToDoViewModel

    @Inject
    lateinit var toDoModelFactory: ToDoViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoViewModel =
            ViewModelProvider(requireActivity(), toDoModelFactory)[ToDoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addBinding = FragmentAddBinding.inflate(inflater, container, false)
        return addBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mSpinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priorities,
            R.layout.custom_spinner_text
        )
        addBinding.spinnerFragmentAddPriority.adapter = mSpinnerAdapter
        addBinding.spinnerFragmentAddPriority.onItemSelectedListener = mSharedViewModel.listener

        addBinding.fabFragmentAdd.setOnClickListener {
            lifecycleScope.launch {
                insertDataToDb()
            }
        }
    }

    private suspend fun insertDataToDb() {
        val mTitle = addBinding.editTextFragmentAddTitle.text.toString()
        val mPriority = addBinding.spinnerFragmentAddPriority.selectedItem.toString()
        val mDescription = addBinding.editTextTextMultiLineFragmentAddDescription.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = ToDoItemEntity(
                0,
                mTitle,
                mSharedViewModel.parseStringInPriority(mPriority),
                mDescription
            )
            toDoViewModel.insertData(newData)
            hideKeyboard()
            view?.let {
                Snackbar.make(it, "Tarefa salva com sucesso!", Snackbar.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.action_addFragment_to_todoListFragment)
        } else {
            view?.let {
                Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show()
            }
        }


    }


}