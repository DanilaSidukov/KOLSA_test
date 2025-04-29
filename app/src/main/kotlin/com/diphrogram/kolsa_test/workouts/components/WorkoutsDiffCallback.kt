package com.diphrogram.kolsa_test.workouts.components

import androidx.recyclerview.widget.DiffUtil
import com.diphrogram.data.models.workouts.WorkoutsItem

class WorkoutsDiffCallback: DiffUtil.ItemCallback<WorkoutsItem>() {

    override fun areItemsTheSame(oldItem: WorkoutsItem, newItem: WorkoutsItem): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: WorkoutsItem, newItem: WorkoutsItem): Boolean {
        return newItem == oldItem
    }
}