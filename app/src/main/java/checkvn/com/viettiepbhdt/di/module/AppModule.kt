package checkvn.com.viettiepbhdt.di.module

import checkvn.com.viettiepbhdt.presentation.ui.active.ActiveViewModel
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel
import checkvn.com.viettiepbhdt.presentation.ui.main.MainViewModel
import checkvn.com.viettiepbhdt.presentation.ui.notification.NotificationViewModel
import checkvn.com.viettiepbhdt.presentation.ui.personal.PersonalViewModel
import checkvn.com.viettiepbhdt.presentation.ui.productdetail.ProductDetailViewModel
import checkvn.com.viettiepbhdt.presentation.ui.products.ProductsViewModel
import checkvn.com.viettiepbhdt.presentation.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single { createWarrantyRepository(get()) }
    single { createActivateProductUseCase(get()) }
    single { createGetListProductUseCase(get()) }
    single { createRequestOtpUseCase(get()) }
    single { createVerifyCode(get()) }
    single { createPasswordUseCase(get()) }
    single { createLoginUseCase(get()) }
    single { createGetProductDetailUseCase(get()) }
    single { createLogoutUseCase(get()) }
    single { createChangePasswordUseCase(get()) }
    single { createGetUserProfileUseCase(get()) }
    single { createUpdateProfileUseCase(get()) }
    single { createUpdateProfileUseCaseFirstTime(get()) }
    single { createRecoverPassUseCase(get()) }
    single { createSendFeedbackUseCase(get()) }
    single { createSendFcmTokenUseCase(get()) }
    single { createGetListNotificationUseCase(get()) }
    single { createRegisterAgencyUseCase(get()) }
    single { createGetListAgencyUseCase(get()) }
    single { createGetListDistrictUseCase(get()) }
    single { createGetListProvinceUseCase(get()) }
    single { createGetListWardUseCase(get()) }
    single { createGetAgencyInfoUseCase(get()) }
    single { createAddWarrantyRepairUseCase(get()) }
    single { createGetListRepairCategoryUseCase(get()) }
    single { createGetListWarrantyRepairUseCase(get()) }
    single { createDeleteAccountUseCase(get()) }
    single { createCheckProductStatusUseCase(get()) }

    viewModel { ActiveViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ProductsViewModel(get()) }
    viewModel { AuthenticationViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProductDetailViewModel(get(), get(), get(), get()) }
    viewModel {
        PersonalViewModel(
            get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get()
        )
    }
    viewModel { NotificationViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}
