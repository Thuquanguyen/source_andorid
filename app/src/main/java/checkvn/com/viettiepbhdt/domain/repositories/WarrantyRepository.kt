package checkvn.com.viettiepbhdt.domain.repositories

import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.domain.usecases.*

interface WarrantyRepository {
    suspend fun activateProduct(param: ActivateProductUseCaseImpl.Param): Result<ActivateResult>

    suspend fun getListProduct(param: GetListProductUseCaseImpl.Param): Result<ListProductResult>

    suspend fun requestOtp(param: RequestOtpUseCaseImpl.Param): Result<RequestOtpResult>

    suspend fun recoverPassword(param: RecoverPasswordUseCaseImpl.Param): Result<RecoverPassword>

    suspend fun verifyCode(param: VerifyCodeUseCaseImpl.Param): Result<VerifyCode>

    suspend fun createPassword(param: CreatePasswordUseCaseImpl.Param): Result<CreatePassword>

    suspend fun login(param: LoginUseCaseImpl.Param): Result<LoginResult>

    suspend fun logout(param: LogoutUseCaseImpl.Param): Result<LogoutResult>

    suspend fun changePassword(param: ChangePasswordUseCaseImpl.Param): Result<ChangePassword>

    suspend fun getUserProfile(param: GetUserProfileUseCaseImpl.Param): Result<Profile>

    suspend fun updateProfile(param: UpdateProfileUseCaseImpl.Param): Result<UpdateProfile>

    suspend fun updateProfileFirstTime(param: UpdateProfileUseCaseFirstTimeImpl.Param): Result<UpdateProfile>

    suspend fun getProductDetail(param: GetProductDetailUseCaseImpl.Param): Result<ProductResult>

    suspend fun sendFeedback(param: SendFeedbackUseCaseImpl.Param): Result<FeedbackResult>

    suspend fun sendFcmToken(param: SendFcmTokenUseCaseImpl.Param): Result<CommonResult>

    suspend fun getListNotification(param: GetListNotificationUseCaseImpl.Param): Result<ListNotificationResult>

    suspend fun registerAgency(param: RegisterAgencyUseCaseImpl.Param): Result<CommonResult>

    suspend fun getListAgency(param: GetListAgencyUseCaseImpl.Param): Result<ListAgencyResult>

    suspend fun getListProvince(param: GetListProvinceUseCaseImpl.Param): Result<ListLocationResult>

    suspend fun getListDistrict(param: GetListDistrictUseCaseImpl.Param): Result<ListLocationResult>

    suspend fun getListWard(param: GetListWardUseCaseImpl.Param): Result<ListLocationResult>

    suspend fun getAgencyInfo(param: GetAgencyInfoUseCaseImpl.Param): Result<AgencyResult>

    suspend fun getListRepairCategory(param: GetListRepairCategoryUseCaseImpl.Param): Result<ListRepairCategoryResult>

    suspend fun addWarrantyRepair(param: AddWarrantyRepairUseCaseImpl.Param): Result<CommonResult>

    suspend fun getListWarrantyRepair(param: GetListWarrantyRepairUseCaseImpl.Param): Result<ListWarrantyRepairResult>

    suspend fun deleteAccount(param: DeleteAccountUseCaseImpl.Param): Result<CommonResult>

    suspend fun checkProductStatus(param: CheckProductStatusUseCaseImpl.Param): Result<ProductStatusResult>
}
