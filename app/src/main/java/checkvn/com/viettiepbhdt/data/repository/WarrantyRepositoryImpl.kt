package checkvn.com.viettiepbhdt.data.repository

import checkvn.com.viettiepbhdt.data.mappers.*
import checkvn.com.viettiepbhdt.data.source.remote.ApiService
import checkvn.com.viettiepbhdt.data.utils.resultWrapper
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.*

class WarrantyRepositoryImpl(
    private val apiService: ApiService
) : WarrantyRepository {

    override suspend fun activateProduct(param: ActivateProductUseCaseImpl.Param): Result<ActivateResult> {
        return resultWrapper {
            val result = apiService.activateProduct(param)
            ActivateResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListProduct(param: GetListProductUseCaseImpl.Param): Result<ListProductResult> {
        return resultWrapper {
            val result = apiService.getListProduct(param)
            ListProductResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun requestOtp(param: RequestOtpUseCaseImpl.Param): Result<RequestOtpResult> {
        return resultWrapper {
            val result = apiService.requestOtp(param)
            OtpResultMapper.mapToDomains(result)
        }
    }

    override suspend fun recoverPassword(param: RecoverPasswordUseCaseImpl.Param): Result<RecoverPassword> {
        return resultWrapper {
            apiService.recoverPassword(param).mapToDomain()
        }
    }

    override suspend fun verifyCode(param: VerifyCodeUseCaseImpl.Param): Result<VerifyCode> {
        return resultWrapper {
            val result = apiService.verifyCode(param)
            VerifyCodeMapper.mapToDomains(result)
        }
    }

    override suspend fun createPassword(param: CreatePasswordUseCaseImpl.Param): Result<CreatePassword> {
        return resultWrapper {
            val result = apiService.createPassword(param)
            CreatePasswordMapper.mapToDomains(result)
        }
    }

    override suspend fun login(param: LoginUseCaseImpl.Param): Result<LoginResult> {
        return resultWrapper {
            val result = apiService.login(param)
            LoginResultMapper.mapToDomains(result)
        }
    }

    override suspend fun logout(param: LogoutUseCaseImpl.Param): Result<LogoutResult> {
        return resultWrapper {
            val result = apiService.logout(param)
            LogoutResultMapper.mapToDomains(result)
        }
    }

    override suspend fun changePassword(param: ChangePasswordUseCaseImpl.Param): Result<ChangePassword> {
        return resultWrapper {
            val result = apiService.changePassword(param)
            ChangePasswordMapper.mapToDomains(result)
        }
    }

    override suspend fun getUserProfile(param: GetUserProfileUseCaseImpl.Param): Result<Profile> {
        return resultWrapper {
            apiService.getUserProfile(param).mapToDomains()
        }
    }

    override suspend fun updateProfile(param: UpdateProfileUseCaseImpl.Param): Result<UpdateProfile> {
        return resultWrapper {
            apiService.updateProfile(param).mapToDomain()
        }
    }

    override suspend fun updateProfileFirstTime(param: UpdateProfileUseCaseFirstTimeImpl.Param): Result<UpdateProfile> {
        return resultWrapper {
            apiService.updateProfileFirstTime(param).mapToDomain()
        }
    }

    override suspend fun getProductDetail(param: GetProductDetailUseCaseImpl.Param): Result<ProductResult> {
        return resultWrapper {
            val result = apiService.getProductDetail(param)
            ProductResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun sendFeedback(param: SendFeedbackUseCaseImpl.Param): Result<FeedbackResult> {
        return resultWrapper {
            val result = apiService.sendFeedback(param)
            FeedbackResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun sendFcmToken(param: SendFcmTokenUseCaseImpl.Param): Result<CommonResult> {
        return resultWrapper {
            val result = apiService.sendFcmToken(param)
            CommonResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListNotification(param: GetListNotificationUseCaseImpl.Param): Result<ListNotificationResult> {
        return resultWrapper {
            val result = apiService.getListNotification(param)
            ListNotificationResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun registerAgency(param: RegisterAgencyUseCaseImpl.Param): Result<CommonResult> {
        return resultWrapper {
            val result = apiService.registerAgency(param)
            CommonResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListAgency(param: GetListAgencyUseCaseImpl.Param): Result<ListAgencyResult> {
        return resultWrapper {
            val result = apiService.getListAgency(param)
            ListAgencyResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListProvince(param: GetListProvinceUseCaseImpl.Param): Result<ListLocationResult> {
        return resultWrapper {
            val result = apiService.getListProvince(param)
            ListLocationResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListDistrict(param: GetListDistrictUseCaseImpl.Param): Result<ListLocationResult> {
        return resultWrapper {
            val result = apiService.getListDistrict(param)
            ListLocationResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListWard(param: GetListWardUseCaseImpl.Param): Result<ListLocationResult> {
        return resultWrapper {
            val result = apiService.getListWard(param)
            ListLocationResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getAgencyInfo(param: GetAgencyInfoUseCaseImpl.Param): Result<AgencyResult> {
        return resultWrapper {
            val result = apiService.getAgencyInfo(param)
            AgencyResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListRepairCategory(param: GetListRepairCategoryUseCaseImpl.Param): Result<ListRepairCategoryResult> {
        return resultWrapper {
            val result = apiService.getListRepairCategory(param)
            ListRepairCategoryResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun addWarrantyRepair(param: AddWarrantyRepairUseCaseImpl.Param): Result<CommonResult> {
        return resultWrapper {
            val result = apiService.addWarrantyRepair(param)
            CommonResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun getListWarrantyRepair(param: GetListWarrantyRepairUseCaseImpl.Param): Result<ListWarrantyRepairResult> {
        return resultWrapper {
            val result = apiService.getListWarrantyRepair(param)
            ListWarrantyRepairResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun deleteAccount(param: DeleteAccountUseCaseImpl.Param): Result<CommonResult> {
        return resultWrapper {
            val result = apiService.deleteAccount(param)
            CommonResultEntityMapper.mapToDomains(result)
        }
    }

    override suspend fun checkProductStatus(param: CheckProductStatusUseCaseImpl.Param): Result<ProductStatusResult> {
        return resultWrapper {
            val result = apiService.checkProductStatus(param)
            ProductStatusResultEntityMapper.mapToDomains(result)
        }
    }
}
