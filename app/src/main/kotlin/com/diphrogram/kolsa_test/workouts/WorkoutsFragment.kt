package com.diphrogram.kolsa_test.workouts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.common.BaseFragment
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.kolsa_test.databinding.FragmentWorkoutsBinding
import com.diphrogram.kolsa_test.workouts.components.ItemDecoration
import com.diphrogram.kolsa_test.workouts.components.WorkoutClickListener
import com.diphrogram.kolsa_test.workouts.components.WorkoutsAdapter
import com.diphrogram.utils.EMPTY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutsFragment: BaseFragment<FragmentWorkoutsBinding>(
    FragmentWorkoutsBinding::inflate
), WorkoutClickListener {

    private val viewModel: WorkoutsViewModel by viewModels()
    private val workoutsAdapter = WorkoutsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observeData()
    }

    private fun bind() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(ItemDecoration())
        recyclerView.adapter = workoutsAdapter

        if (searchField.isVisible) {
            clearIcon.setOnClickListener {
                searchField.text.clear()
            }
            searchField.doOnTextChanged { text, start, before, count ->
                val input = text ?: String.EMPTY
                println("input: $input")
                viewModel.filterListByTitle(input.toString())
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                changeLoaderVisibility(state.screenState)
                when(state.screenState) {
                    ScreenState.LoadingComplete -> {
                        workoutsAdapter.submitList(state.filteredList)
                        setUIsVisibility(state.error)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun changeLoaderVisibility(screenState: ScreenState) {
        binding.loader.visibility = when(screenState) {
            ScreenState.LoadingProcess -> View.VISIBLE
            ScreenState.LoadingComplete -> View.GONE
        }
    }

    private fun setUIsVisibility(error: String) {
        val uiVisible = error.isEmpty()
        with(binding) {
            textInfo.isVisible = !uiVisible
            searchLayout.isVisible = uiVisible
            filter.isVisible = uiVisible
            if (!uiVisible) {
                textInfo.text = requireContext().getString(R.string.error, error)
                searchField.visibility = View.GONE
                filter.visibility = View.GONE
            }
        }
    }

    override fun onWorkoutClick(id: Int) {
        println("Click")
    }
}