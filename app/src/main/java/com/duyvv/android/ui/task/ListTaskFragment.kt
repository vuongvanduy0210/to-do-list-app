package com.duyvv.android.ui.task

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duyvv.android.MainActivity
import com.duyvv.android.R
import com.duyvv.android.base.BaseFragment
import com.duyvv.android.databinding.FragmentListTaskBinding
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.ui.task.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class ListTaskFragment : BaseFragment<FragmentListTaskBinding>() {

    override val layoutRes = R.layout.fragment_list_task

    private var activity: MainActivity? = null

    private lateinit var taskAdapter: TaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as MainActivity
        taskAdapter = TaskAdapter { task, view ->
            showPopupMenu(task, view)
        }
    }

    private fun showPopupMenu(task: Task, view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.menu_task_actions, this.menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        true
                    }

                    R.id.action_delete -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Delete task")
                            .setMessage("Are you sure you want to delete this task?")
                            .setPositiveButton("Yes") { _, _ ->
                                task.id?.let { taskViewModel.deleteTask(it) }
                            }
                            .setNegativeButton("Cancel") { _, _ -> }
                            .show()
                        false
                    }

                    else -> false
                }
            }
        }.show()
    }

    override fun setUp() {
        binding.rcvTask.adapter = taskAdapter
        taskViewModel.apply {
            taskList.observe(viewLifecycleOwner) {
                taskAdapter.setItems(it.sortedByDescending { task -> task.time.time })
            }

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

        binding.btnAddTask.setOnClickListener {
            taskViewModel.addTask(
                Task(
                    title = "Task new",
                    description = "Day la mo ta task new",
                    status = TaskStatus.UPCOMING,
                    time = Calendar.getInstance()
                )
            )
        }
    }
}
