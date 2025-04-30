package com.diphrogram.kolsa_test.presentation.workouts

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
import com.diphrogram.kolsa_test.presentation.video.VideoFragment
import com.diphrogram.kolsa_test.presentation.workouts.components.FilterBottomSheetDialog
import com.diphrogram.kolsa_test.presentation.workouts.components.ItemDecoration
import com.diphrogram.kolsa_test.presentation.workouts.components.OnFilterClickListener
import com.diphrogram.kolsa_test.presentation.workouts.components.WorkoutClickListener
import com.diphrogram.kolsa_test.presentation.workouts.components.WorkoutsAdapter
import com.diphrogram.utils.EMPTY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutsFragment: BaseFragment<FragmentWorkoutsBinding>(
    FragmentWorkoutsBinding::inflate
), WorkoutClickListener, OnFilterClickListener {

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
            searchField.doOnTextChanged { text, _, _, _ ->
                val input = text ?: String.EMPTY
                println("input: $input")
                viewModel.filterListByTitle(input.toString())
            }
            filter.text = requireContext().getString(R.string.filter, requireContext().getString(R.string.all))
            filter.setOnClickListener {
                val modal = FilterBottomSheetDialog()
                modal.setOnFilterSelectedListener(this@WorkoutsFragment)
                parentFragmentManager.let { manager ->
                    modal.show(manager, FilterBottomSheetDialog.TAG)
                }
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
                        setUIsVisibility(state)
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

    private fun setUIsVisibility(state: WorkoutsModelState) {
        val uiVisible = state.error.isEmpty()
        with(binding) {
            textInfo.isVisible = !uiVisible
            searchLayout.isVisible = uiVisible
            filter.isVisible = uiVisible
            if (!uiVisible) {
                textInfo.text = requireContext().getString(R.string.error, state.error)
                searchField.visibility = View.GONE
                filter.visibility = View.GONE
            } else {
                val type = getWorkoutTypeValue(requireContext(), state.currentFilterType)
                filter.text = requireContext().getString(R.string.filter, type)
            }
        }
    }

    override fun onWorkoutClick(id: Int, title: String, description: String?) {
        val videoFragment = VideoFragment.newInstance(id, title, description)
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, videoFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onFilterItemClick(selectedFilter: String) {
        val workoutType = getWorkoutType(requireContext(), selectedFilter)
        viewModel.filterListByType(workoutType)
    }
}