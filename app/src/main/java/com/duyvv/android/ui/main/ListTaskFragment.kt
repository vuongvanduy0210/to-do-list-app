package com.duyvv.android.ui.main

import android.annotation.SuppressLint
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
import com.duyvv.android.domain.TaskPriority
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.ui.main.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class ListTaskFragment : BaseFragment<FragmentListTaskBinding>() {

    override val layoutRes = R.layout.fragment_list_task

    private var activity: MainActivity? = null

    private lateinit var taskAdapter: TaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as MainActivity
        taskAdapter = TaskAdapter(
            context = requireContext(),
            onClickItemTask = {
                openDetailDialog(it)
            },
            onClickOptions = { task, view ->
                showPopupMenu(task, view)
            }
        )
    }

    private fun openDetailDialog(task: Task) {
        DetailTaskDialog(
            task,
            object : DetailTaskDialog.OnClickDetailTaskListener {
                override fun onClickEdit(task: Task) {
                    goToEditScreen(task)
                }

                override fun onClickDelete(task: Task) {
                    deleteTask(task)
                }

                override fun onClickMarkCompleted(task: Task) {
                    taskViewModel.updateTask(
                        task.apply {
                            status = TaskStatus.COMPLETED
                        }
                    )
                }
            }
        ).show(childFragmentManager, "detail_task_dialog")
    }

    private fun showPopupMenu(task: Task, view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.menu_task_actions, this.menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        goToEditScreen(task)
                        true
                    }

                    R.id.action_delete -> {
                        deleteTask(task)
                        false
                    }

                    else -> false
                }
            }
        }.show()
    }

    private fun goToEditScreen(task: Task) {
        activity?.hideKeyboard()
        navigate(ListTaskFragmentDirections.actionTaskFragmentToEditTaskFragment(task))
    }

    private fun deleteTask(task: Task) {
        activity?.hideKeyboard()
        AlertDialog.Builder(requireContext())
            .setTitle("Delete task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes") { _, _ ->
                task.id?.let { taskViewModel.deleteTask(it) }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    override fun setUp() {
        binding.rcvTask.adapter = taskAdapter
        taskViewModel.apply {
            taskList.observe(viewLifecycleOwner) {
                taskAdapter.setItems(it.sortedBy { task -> task.time.time })
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
            activity?.hideKeyboard()
            navigate(ListTaskFragmentDirections.actionTaskFragmentToAddTaskFragment())
        }

        binding.btnFilter.setOnClickListener {
            openFilterDialog()
        }
    }

    private fun openFilterDialog() {
        FilterTaskDialog(
            priority = taskViewModel.priority,
            status = taskViewModel.status,
            object : FilterTaskDialog.OnClickFilterTaskListener {
                override fun onClickApplyFilter(priority: TaskPriority?, status: TaskStatus?) {
                    val isCompleted = when (status) {
                        TaskStatus.COMPLETED -> true
                        TaskStatus.UNCOMPLETED -> false
                        null -> null
                    }
                    taskViewModel.updateFilterProperties(priority?.level, isCompleted)
                    taskViewModel.getTasks()
                }
            }
        ).show(childFragmentManager, "filter_task_dialog")
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.getTasks()
    }
}
