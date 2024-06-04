package com.duyvv.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    var job: Job? = null

    var exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // show message
        /*handleMessage(
            message = throwable.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
            bgType = BGType.BG_TYPE_ERROR
        )*/
    }

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading: SharedFlow<Boolean> = _isLoading

    fun showLoading(isShow: Boolean) {
        viewModelScope.launch {
            _isLoading.emit(isShow)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}
