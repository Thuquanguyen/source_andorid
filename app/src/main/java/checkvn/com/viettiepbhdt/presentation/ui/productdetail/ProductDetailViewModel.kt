package checkvn.com.viettiepbhdt.presentation.ui.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.BuildConfig
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.domain.usecases.*
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.getUserId
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addWarrantyRepairUseCase: AddWarrantyRepairUseCase,
    private val getListRepairCategoryUseCase: GetListRepairCategoryUseCase,
    private val getListWarrantyRepairUseCase: GetListWarrantyRepairUseCase
) : BaseViewModel() {
    val getProductResult = MutableLiveData<Result<ProductResult>>()
    val addWarrantyRepairResult = MutableLiveData<Result<CommonResult>>()
    val getListRepairCategoryResult = MutableLiveData<Result<ListRepairCategoryResult>>()
    val getListWarrantyRepairResult = MutableLiveData<Result<ListWarrantyRepairResult>>()
    var QRCodeContent = "";

    fun getProductDetail() {
        val param = GetProductDetailUseCaseImpl.Param(
            QRCodeContent = BuildConfig.BASE_URL + "/t2/" + QRCodeContent,
            User_ID = getUserId()
        )
        viewModelScope.launch {
            ensureActive()
            getProductResult.value = getProductDetailUseCase.execute(param)
        }
    }

    fun addWarrantyRepair(
        content: String,
        repairCategoryIDs: String
    ) {
        val param = AddWarrantyRepairUseCaseImpl.Param(
            Content = content,
            QRCodeWarrantyRepairCategory_IDs = repairCategoryIDs,
            QRCodeContent = BuildConfig.BASE_URL + "/t2/" + QRCodeContent,
            User_ID = getUserId()
        )
        viewModelScope.launch {
            ensureActive()
            addWarrantyRepairResult.value = addWarrantyRepairUseCase.execute(param)
        }
    }

    fun getListRepairCategory() {
        val param = GetListRepairCategoryUseCaseImpl.Param()
        viewModelScope.launch {
            ensureActive()
            getListRepairCategoryResult.value = getListRepairCategoryUseCase.execute(param)
        }
    }

    fun getListWarrantyRepair() {
        val param = GetListWarrantyRepairUseCaseImpl.Param(
            QRCodeContent = BuildConfig.BASE_URL + "/t2/" + QRCodeContent
        )
        viewModelScope.launch {
            ensureActive()
            getListWarrantyRepairResult.value = getListWarrantyRepairUseCase.execute(param)
        }
    }
}