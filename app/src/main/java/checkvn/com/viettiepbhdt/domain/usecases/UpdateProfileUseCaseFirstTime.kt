package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.UpdateProfile
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface UpdateProfileUseCaseFirstTime :
    BaseUseCase<UpdateProfileUseCaseFirstTimeImpl.Param, UpdateProfile>

class UpdateProfileUseCaseFirstTimeImpl(private val repository: WarrantyRepository) :
    UpdateProfileUseCaseFirstTime {

    override suspend fun execute(param: Param): Result<UpdateProfile> {
        return repository.updateProfileFirstTime(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String,
        val FullName: String = "",
        val BirthDate: String = "",
        val Gender: String = "",
        val RoleType: String = "",
        val Agency_ID: String = ""
    )
}