package br.com.raveline.todo2021.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private var updateBinding: FragmentUpdateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        updateBinding = FragmentUpdateBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)

        return updateBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_delete_update,menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateBinding = null
    }

}