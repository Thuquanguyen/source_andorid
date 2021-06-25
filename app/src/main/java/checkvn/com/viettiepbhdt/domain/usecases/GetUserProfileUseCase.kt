package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.Profile
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetUserProfileUseCase :
    BaseUseCase<GetUserProfileUseCaseImpl.Param, Profile>

class GetUserProfileUseCaseImpl(private val repository: WarrantyRepository) :
    GetUserProfileUseCase {

    override suspend fun execute(param: Param): Result<Profile> {
        return repository.getUserProfile(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String
    )
}
