package com.duyvv.android.ui.main

import com.duyvv.android.R
import com.duyvv.android.base.BaseBottomSheetDialog
import com.duyvv.android.databinding.BottomSheetTaskFilterBinding
import com.duyvv.android.domain.TaskPriority
import com.duyvv.android.domain.TaskStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterTaskDialog(
    private val priority: Int?,
    private val status: Boolean?,
    private val listener: OnClickFilterTaskListener
) : BaseBottomSheetDialog<BottomSheetTaskFilterBinding>() {

    override val layoutRes = R.layout.bottom_sheet_task_filter

    override fun init() {
        when (priority) {
            TaskPriority.LOW.level -> binding.rbLow.isChecked = true
            TaskPriority.MEDIUM.level -> binding.rbMedium.isChecked = true
            TaskPriority.HIGH.level -> binding.rbHigh.isChecked = true
        }
        when (status) {
            true -> binding.rbCompleted.isChecked = true
            false -> binding.rbUncompleted.isChecked = true
            else -> {}
        }
    }

    override fun setUp() {
        binding.apply {
            btnClear.setOnClickListener {
                clearUI()
            }

            btnReset.setOnClickListener {
                clear()
            }

            btnApply.setOnClickListener {
                val priority = if (binding.rbLow.isChecked) {
                    TaskPriority.LOW
                } else if (binding.rbMedium.isChecked) {
                    TaskPriority.MEDIUM
                } else if (binding.rbHigh.isChecked) {
                    TaskPriority.HIGH
                } else {
                    null
                }
                val status = if (binding.rbCompleted.isChecked) {
                    TaskStatus.COMPLETED
                } else if (binding.rbUncompleted.isChecked) {
                    TaskStatus.UNCOMPLETED
                } else {
                    null
                }
                listener.onClickApplyFilter(priority, status)
                dismiss()
            }
        }
    }

    private fun clear() {
        clearUI()
        listener.onClickApplyFilter(null, null)
        dismiss()
    }

    private fun clearUI() {
        binding.rgStatus.clearCheck()
        binding.rgPriority.clearCheck()
    }

    interface OnClickFilterTaskListener {
        fun onClickApplyFilter(priority: TaskPriority?, status: TaskStatus?)
    }
}
