package ph.mart.base

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel @ViewModelInject() constructor() : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()

    val errorLiveData = MutableLiveData<Event<Throwable>>()

    val snackBarLiveData = MutableLiveData<Event<Int>>()

    // Experimental code :D
    val baseEventLiveData = MutableLiveData<Event<BaseEvent>>()

    protected fun launch(
        baseEvent: BaseEvent? = null,
        allowMultipleRequest: Boolean = true,
        function: suspend () -> Unit
    ) {
        if (loadingLiveData.value == true && allowMultipleRequest.not()) return

        viewModelScope.launch {
            loadingLiveData.value = true
            kotlin.runCatching {
                function()
                loadingLiveData.value = false

                if (baseEvent != null) {
                    baseEventLiveData.value = Event(baseEvent)
                }
            }.onFailure {
                Timber.e(it)
                errorLiveData.value = Event(it)
                loadingLiveData.value = false
            }
        }
    }

    open class BaseEvent
}