package com.duyvv.android.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.duyvv.android.MainActivity
import com.duyvv.android.R
import com.duyvv.android.base.BGType
import com.duyvv.android.base.BaseFragment
import com.duyvv.android.databinding.FragmentEditTaskBinding
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.ui.main.TaskViewModel
import com.duyvv.android.util.app.FormatUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class EditTaskFragment : BaseFragment<FragmentEditTaskBinding>() {

    override val layoutRes = R.layout.fragment_edit_task

    private var activity: MainActivity? = null

    private val taskViewModel: TaskViewModel by viewModels()

    private var currentTask: Task? = null

    private val args: EditTaskFragmentArgs by navArgs()

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {
        currentTask = args.task

        taskViewModel.apply {
            lifecycleScope.launch {
                isLoading.collect {
                    activity?.showLoading(it)
                }
            }

            lifecycleScope.launch {
                responseMessage.collect {
                    activity?.showMessage(requireContext(), it.message, it.bgType)
                }
            }
        }

        binding.apply {
            btnChangeTask.setOnClickListener {
                onClickChangeTask()
            }

            layoutDate.setOnClickListener {
                openPickDate {
                    tvDate.text = it
                }
            }

            layoutTime.setOnClickListener {
                openPickTime {
                    tvTime.text = it
                }
            }

            edtTitle.setText(currentTask?.title)
            edtDescription.setText(currentTask?.description)
            tvDate.text = FormatUtils.convertCalendarToDMY(currentTask?.time ?: Calendar.getInstance())
            tvTime.text = FormatUtils.convertDateToHourAndMinute(currentTask?.time ?: Calendar.getInstance())
            cbCompleted.isChecked = currentTask?.status == TaskStatus.COMPLETED
        }
    }

    private fun openPickDate(onPicked: (String) -> Unit) {
        activity?.hideKeyboard()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), { _, y, monthOfYear, dayOfMonth ->
            val d = if (dayOfMonth >= 10) dayOfMonth.toString() else "0$dayOfMonth"
            val m = if (monthOfYear + 1 >= 10) (monthOfYear + 1).toString() else "0${monthOfYear + 1}"
            onPicked.invoke("$d/$m/$y")
        }, year, month, day).apply {
            datePicker.minDate = c.timeInMillis
        }.show()
    }

    private fun openPickTime(onPicked: (String) -> Unit) {
        activity?.hideKeyboard()
        val currentDateTime = Calendar.getInstance()
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), { _, hour, minute ->
            val h = if (hour < 10) "0$hour" else hour
            val m = if (minute < 10) "0$minute" else minute
            onPicked("$h:$m")
        }, startHour, startMinute, false).show()
    }

    private fun onClickChangeTask() {
        activity?.hideKeyboard()
        val title = binding.edtTitle.text?.trim().toString()
        val des = binding.edtDescription.text?.trim().toString()
        val date = binding.tvDate.text.trim().toString()
        val time = binding.tvTime.text.trim().toString()
        val isCompleted = binding.cbCompleted.isChecked
        if (title.isEmpty()) {
            activity?.showMessage(requireContext(), "Title can't be blank", BGType.BG_TYPE_ERROR)
            return
        }
        if (des.isEmpty()) {
            activity?.showMessage(requireContext(), "Description can't be blank", BGType.BG_TYPE_ERROR)
            return
        }
        if (date.isEmpty()) {
            activity?.showMessage(requireContext(), "Date can't be blank", BGType.BG_TYPE_ERROR)
            return
        }
        if (time.isEmpty()) {
            activity?.showMessage(requireContext(), "Time can't be blank", BGType.BG_TYPE_ERROR)
            return
        }

        currentTask?.let {
            taskViewModel.updateTask(
                Task(
                    id = it.id,
                    title = title,
                    description = des,
                    time = FormatUtils.convertStringToCalendar("$date $time:00"),
                    status = if (isCompleted) TaskStatus.COMPLETED else TaskStatus.UNCOMPLETED
                )
            )
            findNavController().popBackStack()
        }
    }
}
