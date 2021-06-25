package checkvn.com.viettiepbhdt.presentation.ui.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.domain.usecases.*
import checkvn.com.viettiepbhdt.domain.usecases.LogoutUseCaseImpl.Param
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.getEmail
import checkvn.com.viettiepbhdt.utils.getFullName
import checkvn.com.viettiepbhdt.utils.getPhone
import com.google.firebase.iid.FirebaseInstanceId
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class PersonalViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val sendFeedbackUseCase: SendFeedbackUseCase,
    private val registerAgencyUseCase: RegisterAgencyUseCase,
    private val getListAgencyUseCase: GetListAgencyUseCase,
    private val getAgencyInfoUseCase: GetAgencyInfoUseCase,
    private val updateProfileUseCaseFirstTime: UpdateProfileUseCaseFirstTime,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel() {

    val logoutResult = MutableLiveData<Result<LogoutResult>>()
    val changePasswordResult = MutableLiveData<Result<ChangePassword>>()
    val sendFeedbackResult = MutableLiveData<Result<FeedbackResult>>()
    val registerAgencyResult = MutableLiveData<Result<CommonResult>>()
    val listAgencyResult = MutableLiveData<Result<ListAgencyResult>>()
    val getAgencyInfoResult = MutableLiveData<Result<AgencyResult>>()
    val deleteAccountResult = MutableLiveData<Result<CommonResult>>()
    val profileResult = MutableLiveData<Result<Profile>>()

    fun changePassword(userName: String, currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            val result = changePasswordUseCase.execute(
                ChangePasswordUseCaseImpl.Param(
                    OldPassword = currentPassword, Password = newPassword, UserName = userName
                )
            )
            changePasswordResult.value = result
        }
    }

    fun logout() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            callApi(token)
        }
    }

    fun updateProfile(
        userId: String,
        fullName: String,
        birthDate: String,
        gender: String,
        phoneNumber: String,
        agencyId: String
    ): LiveData<Result<UpdateProfile>> {
        return liveData {
            val result = updateProfileUseCase.execute(
                UpdateProfileUseCaseImpl.Param(
                    User_ID = userId,
                    FullName = fullName,
                    BirthDate = birthDate,
                    Gender = gender,
                    Phone = phoneNumber,
                    Agency_ID = agencyId
                )
            )
            emit(result)
        }
    }

    fun updateProfileFirstTime(
        userId: String,
        fullName: String,
        birthDate: String,
        gender: String,
        roleType: String,
        agencyId: String
    ): LiveData<Result<UpdateProfile>> {
        return liveData {
            val result = updateProfileUseCaseFirstTime.execute(
                UpdateProfileUseCaseFirstTimeImpl.Param(
                    User_ID = userId,
                    FullName = fullName,
                    BirthDate = birthDate,
                    Gender = gender,
                    RoleType = roleType,
                    Agency_ID = agencyId
                )
            )
            emit(result)
        }
    }

    fun getProfile() {
        val userId = Prefs.getString(Constants.USER_ID, "")
        val param = GetUserProfileUseCaseImpl.Param(
            User_ID = userId
        )
        viewModelScope.launch {
            profileResult.value = getUserProfileUseCase.execute(param)
        }
    }

    fun getListAgency() {
        val param = GetListAgencyUseCaseImpl.Param(
            Keyword = ""
        )
        viewModelScope.launch {
            listAgencyResult.value = getListAgencyUseCase.execute(param)
        }
    }

    fun getAgencyByCode(agencyCode: String) {
        viewModelScope.launch {
            getAgencyInfoResult.value =
                getAgencyInfoUseCase.execute(GetAgencyInfoUseCaseImpl.Param(Code = agencyCode))
        }
    }

    fun registerAgency(userId: String) {
        viewModelScope.launch {
            registerAgencyResult.value =
                registerAgencyUseCase.execute(RegisterAgencyUseCaseImpl.Param(User_ID = userId))
        }
    }

    private fun callApi(fcmToken: String) {
        val userId = Prefs.getString(Constants.USER_ID, "")
        viewModelScope.launch {
            val result = logoutUseCase.execute(Param(FireBaseToken = fcmToken, User_ID = userId))
            logoutResult.value = result
        }
    }

    fun clearData() {
        Prefs.clear()
    }

    fun sendFeedback(content: String) {
        val param = SendFeedbackUseCaseImpl.Param(
            FullName = getFullName(),
            Phone = getPhone(),
            Email = getEmail(),
            Content = content
        )
        viewModelScope.launch {
            ensureActive()
            sendFeedbackResult.value = sendFeedbackUseCase.execute(param)
        }
    }

    fun deleteAccount(password: String, fcmToken: String) {
        val param = DeleteAccountUseCaseImpl.Param(
            UserName = getPhone(),
            Password = password,
            FireBaseToken = fcmToken
        )
        viewModelScope.launch {
            ensureActive()
            deleteAccountResult.value = deleteAccountUseCase.execute(param)
        }
    }
}