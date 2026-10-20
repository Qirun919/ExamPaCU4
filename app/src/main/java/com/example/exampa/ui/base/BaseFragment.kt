package com.example.exampa.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiComponents(view)
        setupViewModelObserver(view)
    }

    protected open fun setupViewModelObserver(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.error.collect { message ->
                        showSnackbar(message, view)
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        onLoadingChanged(isLoading)
                    }
                }
            }
        }
    }

    protected open fun setupUiComponents(view: View) {
    }

    protected open fun onLoadingChanged(isLoading: Boolean) {
        // Override this in Fragment if loading UI is needed
    }

    protected fun showSnackbar(message: String, view: View) {
        Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}