package checkvn.com.viettiepbhdt.data.source.remote

import checkvn.com.viettiepbhdt.data.entities.*
import checkvn.com.viettiepbhdt.domain.usecases.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/apiw/Warranty/WarrantyActive")
    suspend fun activateProduct(@Body param: ActivateProductUseCaseImpl.Param): ActivateResultEntity

    @POST("/apiw/Warranty/ProductWarranty_Search")
    suspend fun getListProduct(@Body param: GetListProductUseCaseImpl.Param): ListProductResultEntity

    @POST("/apiw/Warranty/RegisterByPhone")
    suspend fun requestOtp(@Body param: RequestOtpUseCaseImpl.Param): RequestOtpResultEntity

    @POST("/apiw/Warranty/RecoverPassword")
    suspend fun recoverPassword(@Body param: RecoverPasswordUseCaseImpl.Param): RecoverPasswordEntity

    @POST("/apiw/Warranty/VerifyCodeByPhone")
    suspend fun verifyCode(@Body param: VerifyCodeUseCaseImpl.Param): VerifyCodeEntity

    @POST("/apiw/Warranty/SetPasswordByPhone")
    suspend fun createPassword(@Body param: CreatePasswordUseCaseImpl.Param): CreatePasswordEntity

    @POST("/apiw/Warranty/LoginByPassword")
    suspend fun login(@Body param: LoginUseCaseImpl.Param): LoginResultEntity

    @POST("/apiw/Warranty/WarrantyGetInfo")
    suspend fun getProductDetail(@Body param: GetProductDetailUseCaseImpl.Param): ProductResultEntity

    @POST("/apiw/Warranty/Logout")
    suspend fun logout(@Body param: LogoutUseCaseImpl.Param): LogoutResultEntity

    @POST("/apiw/Warranty/GetProfile")
    suspend fun getUserProfile(@Body param: GetUserProfileUseCaseImpl.Param): ProfileEntity

    @POST("/apiw/Warranty/UpdateProfile")
    suspend fun updateProfile(@Body param: UpdateProfileUseCaseImpl.Param): UpdateProfileEntity

    @POST("/apiw/Warranty/UpdateProfileFirstTime")
    suspend fun updateProfileFirstTime(@Body param: UpdateProfileUseCaseFirstTimeImpl.Param): UpdateProfileEntity

    @POST("/apiw/Warranty/ChangePassword")
    suspend fun changePassword(@Body param: ChangePasswordUseCaseImpl.Param): ChangePasswordEntity

    @POST("/apiw/Warranty/Feedback")
    suspend fun sendFeedback(@Body param: SendFeedbackUseCaseImpl.Param): FeedbackResultEntity

    @POST("/apiw/Warranty/FCMClientInstall")
    suspend fun sendFcmToken(@Body param: SendFcmTokenUseCaseImpl.Param): CommonResultEntity

    @POST("/apiw/Warranty/FCMMessageList")
    suspend fun getListNotification(@Body param: GetListNotificationUseCaseImpl.Param): ListNotificationResultEntity

    @POST("/apiw/Warranty/AgencyReg")
    suspend fun registerAgency(@Body param: RegisterAgencyUseCaseImpl.Param): CommonResultEntity

    @POST("/apiw/Warranty/Agency_Search")
    suspend fun getListAgency(@Body param: GetListAgencyUseCaseImpl.Param): ListAgencyResultEntity

    @POST("/apiw/Warranty/Location_Search")
    suspend fun getListProvince(@Body param: GetListProvinceUseCaseImpl.Param): ListLocationResultEntity

    @POST("/apiw/Warranty/District_Search")
    suspend fun getListDistrict(@Body param: GetListDistrictUseCaseImpl.Param): ListLocationResultEntity

    @POST("/apiw/Warranty/Ward_Search")
    suspend fun getListWard(@Body param: GetListWardUseCaseImpl.Param): ListLocationResultEntity

    @POST("/apiw/Warranty/GetAgencyByCode")
    suspend fun getAgencyInfo(@Body param: GetAgencyInfoUseCaseImpl.Param): AgencyResultEntity

    @POST("/apiw/Warranty/WarrantyRepairCategory_Search")
    suspend fun getListRepairCategory(@Body param: GetListRepairCategoryUseCaseImpl.Param): ListRepairCategoryResultEntity

    @POST("/apiw/Warranty/WarrantyRepair")
    suspend fun addWarrantyRepair(@Body param: AddWarrantyRepairUseCaseImpl.Param): CommonResultEntity

    @POST("/apiw/Warranty/WarrantyRepair_Search")
    suspend fun getListWarrantyRepair(@Body param: GetListWarrantyRepairUseCaseImpl.Param): ListWarrantyRepairResultEntity

    @POST("/apiw/Warranty/AccountDelete")
    suspend fun deleteAccount(@Body param: DeleteAccountUseCaseImpl.Param): CommonResultEntity

    @POST("/apiw/Warranty/WarrantyCheckStatus")
    suspend fun checkProductStatus(@Body param: CheckProductStatusUseCaseImpl.Param): ProductStatusResultEntity

}
