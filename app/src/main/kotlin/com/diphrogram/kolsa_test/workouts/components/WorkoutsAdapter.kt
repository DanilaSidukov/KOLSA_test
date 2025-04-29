package com.diphrogram.kolsa_test.workouts.components

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutType.Another
import com.diphrogram.data.models.workouts.WorkoutType.ExerciseSet
import com.diphrogram.data.models.workouts.WorkoutType.Live
import com.diphrogram.data.models.workouts.WorkoutType.Workout
import com.diphrogram.data.models.workouts.WorkoutsItem
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.databinding.WorkoutsItemBinding

interface WorkoutClickListener {

    fun onWorkoutClick(id: Int)
}

class WorkoutsAdapter(
    private val listener: WorkoutClickListener
): ListAdapter<WorkoutsItem, WorkoutsAdapter.ViewHolder>(WorkoutsDiffCallback()) {

    private lateinit var context: Context

    inner class ViewHolder(private val binding: WorkoutsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutsItem) {
            val workoutType = getTypeData(item.type)
            with(binding) {
                name.text = context.getString(R.string.workout_name, item.title)
                type.text = context.getString(R.string.workout_type, workoutType)
                duration.text = context.getString(R.string.duration, item.duration)
                description.text = item.description

                description.isVisible = item.description != null

                root.setOnClickListener {
                    listener.onWorkoutClick(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorkoutsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    private fun getTypeData(workoutType: WorkoutType): String {
        return with(context) {
            when (workoutType) {
                Workout -> getString(R.string.workout)
                Live -> getString(R.string.live)
                ExerciseSet -> getString(R.string.set)
                Another -> getString(R.string.another)
            }
        }
    }
}