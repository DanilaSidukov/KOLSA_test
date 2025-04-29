package com.diphrogram.kolsa_test.workouts.components

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.diphrogram.data.models.workouts.WorkoutItem
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.databinding.WorkoutsItemBinding
import com.diphrogram.kolsa_test.workouts.getWorkoutTypeValue

interface WorkoutClickListener {

    fun onWorkoutClick(id: Int)
}

class WorkoutsAdapter(
    private val listener: WorkoutClickListener
) : RecyclerView.Adapter<WorkoutsAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val workoutsList = mutableListOf<WorkoutItem>()

    inner class ViewHolder(private val binding: WorkoutsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutItem) {
            val workoutType = getWorkoutTypeValue(context, item.type)
            with(binding) {
                title.text = context.getString(R.string.workout_name, item.title)
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
        context = parent.context
        val binding = WorkoutsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = workoutsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = workoutsList.size

    fun submitList(newList: List<WorkoutItem>) {
        workoutsList.clear()
        workoutsList.addAll(newList)
        notifyDataSetChanged()
    }
}