package com.diphrogram.kolsa_test.workouts.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.common.BaseBottomSheetDialogFragment
import com.diphrogram.kolsa_test.databinding.DialogBottomSheetFilterBinding
import com.diphrogram.kolsa_test.workouts.getWorkoutTypeValue
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

interface OnFilterClickListener {

    fun onFilterItemClick(selectedFilter: String)
}

class FilterBottomSheetDialog : BaseBottomSheetDialogFragment<DialogBottomSheetFilterBinding>(
    DialogBottomSheetFilterBinding::inflate
) {

    private var listener: OnFilterClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = LayoutInflater.from(requireContext())
        val container = binding.bottomSheetLayout
        val items = getItems()
        items.forEach { item ->
            val itemView = inflater.inflate(R.layout.filter_item, container, false)
            val textView = itemView.findViewById<TextView>(R.id.filter_text)
            textView.text = item

            itemView.setOnClickListener {
                onItemClick(item)
            }

            container.addView(itemView)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { dialogInterface ->
            val bottomDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            bottomSheet?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    fun setOnFilterSelectedListener(listener: OnFilterClickListener) {
        this.listener = listener
    }

    private fun onItemClick(selectedFilter: String) {
        listener?.onFilterItemClick(selectedFilter)
        dismiss()
    }

    private fun getItems(): List<String> {
        val list = enumValues<WorkoutType>().map { type ->
            getWorkoutTypeValue(requireContext(), type)
        }
        return list
    }

    companion object {

        const val TAG = "FilterBottomSheetDialog"
    }
}