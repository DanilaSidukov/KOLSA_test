package com.diphrogram.kolsa_test.presentation.workouts.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.diphrogram.utils.ZERO

class ItemDecoration(
    private val spaceSize: Int = 16
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != Int.ZERO) {
                top = spaceSize
            }
            if (parent.getChildAdapterPosition(view) != parent.childCount - 1) {
                bottom = spaceSize
            }
        }
    }
}