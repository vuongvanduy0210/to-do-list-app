package com.duyvv.android.ui.main

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
import com.duyvv.android.ui.main.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListTaskFragment : BaseFragment<FragmentListTaskBinding>() {

    override val layoutRes = R.layout.fragment_list_task

    private var activity: MainActivity? = null

    private lateinit var taskAdapter: TaskAdapter

    private val taskViewModel: TaskViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as MainActivity
        taskAdapter = TaskAdapter(requireContext()) { task, view ->
            showPopupMenu(task, view)
        }
    }

    private fun showPopupMenu(task: Task, view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.menu_task_actions, this.menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        activity?.hideKeyboard()
                        navigate(ListTaskFragmentDirections.actionTaskFragmentToEditTaskFragment(task))
                        true
                    }

                    R.id.action_delete -> {
                        activity?.hideKeyboard()
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
            activity?.hideKeyboard()
            navigate(ListTaskFragmentDirections.actionTaskFragmentToAddTaskFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.getAllTask()
    }
}
