package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.UpdateProfile
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface UpdateProfileUseCase :
    BaseUseCase<UpdateProfileUseCaseImpl.Param, UpdateProfile>

class UpdateProfileUseCaseImpl(private val repository: WarrantyRepository) :
    UpdateProfileUseCase {

    override suspend fun execute(param: Param): Result<UpdateProfile> {
        return repository.updateProfile(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String,
        val FullName: String = "",
        val BirthDate: String = "",
        val Gender: String = "",
        val Phone: String = "",
        val Agency_ID: String = ""
    )
}
