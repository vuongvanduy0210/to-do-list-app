package com.duyvv.android.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.duyvv.android.base.BGType
import com.duyvv.android.base.BaseViewModel
import com.duyvv.android.domain.Task
import com.duyvv.android.repository.TaskRepository
import com.duyvv.android.util.app.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : BaseViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> get() = _taskList

    init {
        getAllTask()
    }

    private fun getAllTask() {
        val tasks = mutableListOf<Task>()
        showLoading(true)
        job = viewModelScope.launch(exceptionHandler) {
            val response = taskRepository.getAllTasks()
            if (response.data != null) {
                tasks.addAll(response.data)
                _taskList.value = tasks
            } else {
                handleMessage(response.message ?: AppConstants.DEFAULT_MESSAGE_ERROR, BGType.BG_TYPE_ERROR)
            }
            showLoading(false)
        }
    }

    fun addTask(task: Task) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            taskRepository.insertTask(task)
            getAllTask()
            showLoading(false)
            handleMessage("Add task successfully!", BGType.BG_TYPE_SUCCESS)
        }
    }

    fun deleteTask(id: Int) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            taskRepository.deleteTask(id)
            getAllTask()
            showLoading(false)
            handleMessage("Delete task successfully!", BGType.BG_TYPE_SUCCESS)
        }
    }
}
