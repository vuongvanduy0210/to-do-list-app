package com.duyvv.android.ui.main

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

    var priority: Int? = null

    var status: Boolean? = null

    fun getAllTask() {
        val tasks = mutableListOf<Task>()
        job = viewModelScope.launch(exceptionHandler) {
            val response = taskRepository.getAllTasks()
            if (response.data != null) {
                tasks.addAll(response.data)
                _taskList.value = tasks
            } else {
                handleMessage(response.message ?: AppConstants.DEFAULT_MESSAGE_ERROR, BGType.BG_TYPE_ERROR)
            }
        }
    }

    fun getTasks() {
        val tasks = mutableListOf<Task>()
        job = viewModelScope.launch(exceptionHandler) {
            val response = taskRepository.getTasks(priority, status)
            if (response.data != null) {
                tasks.addAll(response.data)
                _taskList.value = tasks
            } else {
                handleMessage(response.message ?: AppConstants.DEFAULT_MESSAGE_ERROR, BGType.BG_TYPE_ERROR)
            }
        }
    }

    fun addTask(task: Task) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            taskRepository.insertTask(task)
            getTasks()
            showLoading(false)
            handleMessage("Add task successfully!", BGType.BG_TYPE_SUCCESS)
        }
    }

    fun deleteTask(id: Int) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            taskRepository.deleteTask(id)
            getTasks()
            showLoading(false)
            handleMessage("Delete task successfully!", BGType.BG_TYPE_SUCCESS)
        }
    }

    fun updateTask(task: Task) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            taskRepository.updateTask(task)
            getTasks()
            showLoading(false)
            handleMessage("Update task successfully!", BGType.BG_TYPE_SUCCESS)
        }
    }

    fun updateFilterProperties(priority: Int?, status: Boolean?) {
        this.priority = priority
        this.status = status
    }
}
