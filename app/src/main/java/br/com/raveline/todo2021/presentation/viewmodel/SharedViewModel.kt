package br.com.raveline.todo2021.presentation.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import br.com.raveline.todo2021.R
import br.com.raveline.todo2021.data.model.Priority

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    //variavel para alterar a cor do texto no spinner
    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                1 -> {
                    (parent?.getChildAt(0) as TextView?)?.setTextColor(
                        ContextCompat.getColor(
                            application,
                            R.color.red
                        )
                    )

                }

                2 -> (parent?.getChildAt(0) as TextView?)?.setTextColor(
                    ContextCompat.getColor(
                        application,
                        R.color.blue
                    )
                )

                3 -> (parent?.getChildAt(0) as TextView?)?.setTextColor(
                    ContextCompat.getColor(
                        application,
                        R.color.lightGreen
                    )
                )
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }

    fun verifyDataFromUser(title: String, description: String): Boolean {

        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else (title.isNotEmpty() && description.isNotEmpty())

    }

    fun parseStringInPriority(priority: String): Priority {

        return when (priority) {
            "Alta" -> Priority.HIGH
            "Média" -> Priority.MEDIUM
            "Baixa" -> Priority.LOW
            else -> Priority.LOW
        }

    }

    companion object {
        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}