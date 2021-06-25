package checkvn.com.viettiepbhdt.presentation.ui.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
