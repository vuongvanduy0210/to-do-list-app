package com.duyvv.android.ui.main

import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import com.duyvv.android.R
import com.duyvv.android.base.BaseBottomSheetDialog
import com.duyvv.android.databinding.BottomSheetDetailTaskBinding
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskPriority
import com.duyvv.android.domain.TaskStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTaskDialog(
    private val task: Task,
    private val onClickEdit: (Task) -> Unit,
    private val onClickDelete: (Task) -> Unit,
    private val onClickMarkCompleted: (Task) -> Unit
) : BaseBottomSheetDialog<BottomSheetDetailTaskBinding>() {

    override val layoutRes = R.layout.bottom_sheet_detail_task

    override fun init() {
    }

    override fun setUp() {
        binding.apply {
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvDateTime.text = DateFormat.format("HH:mm, dd/MM/yyyy", task.time)
            layoutMarkCompleted.visibility = if (task.status == TaskStatus.COMPLETED) View.GONE else View.VISIBLE
            tvPriority.text = task.priority.des
            tvPriority.setTextColor(
                when (task.priority) {
                    TaskPriority.LOW -> ContextCompat.getColor(requireContext(), R.color.black)
                    TaskPriority.MEDIUM -> ContextCompat.getColor(requireContext(), R.color.blue_light)
                    TaskPriority.HIGH -> ContextCompat.getColor(requireContext(), R.color.action_color)
                }
            )

            btnEdit.setOnClickListener {
                onClickEdit.invoke(task)
                dismiss()
            }

            btnDelete.setOnClickListener {
                onClickDelete.invoke(task)
                dismiss()
            }

            layoutMarkCompleted.setOnClickListener {
                onClickMarkCompleted.invoke(task)
                dismiss()
            }
        }
    }
}
