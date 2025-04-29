package com.diphrogram.kolsa_test.workouts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(ItemDecoration())
            recyclerView.adapter = workoutsAdapter
        }
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                changeLoaderVisibility(state.screenState)
                when(state.screenState) {
                    ScreenState.LoadingComplete -> {
                        println("data here: ${state.workoutsList}")
                        workoutsAdapter.submitList(state.workoutsList)
                        setTextInfo(state.error)
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

    private fun setTextInfo(error: String) {
        with(binding) {
            textInfo.isVisible = error.isNotEmpty()
            if (error.isNotEmpty()) {
                textInfo.text = requireContext().getString(R.string.error, error)
            }
        }
    }

    override fun onWorkoutClick(id: Int) {
        println("Click")
    }
}