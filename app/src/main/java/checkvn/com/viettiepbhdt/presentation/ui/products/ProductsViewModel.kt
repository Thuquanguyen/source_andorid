package checkvn.com.viettiepbhdt.presentation.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.ListProductResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.usecases.GetListProductUseCase
import checkvn.com.viettiepbhdt.domain.usecases.GetListProductUseCaseImpl
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.*
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getListProductUseCase: GetListProductUseCase
) : BaseViewModel() {
    val getListProductResult = MutableLiveData<Result<ListProductResult>>()
    var userId = getUserId()
    var orderBy = Constants.OrderBy.WARRANTY_START_DATE_DESC.value
    var pageSize = "999";
    var currentPage = "1"

    fun getListProduct() {
        val param = GetListProductUseCaseImpl.Param(
            User_ID = userId,
            OrderBy = orderBy,
            PageSize = pageSize,
            CurrentPage = currentPage
        )
        viewModelScope.launch {
            ensureActive()
            getListProductResult.value = getListProductUseCase.execute(param)
        }
    }

    fun clearData() {
        Prefs.clear()
    }
}