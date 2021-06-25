package checkvn.com.viettiepbhdt.di.module

import checkvn.com.viettiepbhdt.BuildConfig
import checkvn.com.viettiepbhdt.data.repository.WarrantyRepositoryImpl
import checkvn.com.viettiepbhdt.data.source.remote.ApiService
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CipherSuite.Companion.TLS_DHE_DSS_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_RC4_128_SHA
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { createMoshiConverterFactory() }

    single { createMoshi() }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val connectionSpec = createConnectionSpec()
    return OkHttpClient.Builder()
        .connectionSpecs(listOf(connectionSpec))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create()).build()
}

fun createConnectionSpec(): ConnectionSpec {
    return ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        .cipherSuites(
            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
            TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
            TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
            TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
            TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
            TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
            TLS_ECDHE_RSA_WITH_RC4_128_SHA,
            TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
            TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
            TLS_DHE_RSA_WITH_AES_256_CBC_SHA
        )
        .build()
}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(MoshiConverterFactory.create())
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun createMoshiConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createWarrantyRepository(apiService: ApiService): WarrantyRepository {
    return WarrantyRepositoryImpl(apiService)
}

fun createActivateProductUseCase(
    warrantyRepository: WarrantyRepository
): ActivateProductUseCase {
    return ActivateProductUseCaseImpl(warrantyRepository)
}

fun createGetListProductUseCase(warrantyRepository: WarrantyRepository): GetListProductUseCase {
    return GetListProductUseCaseImpl(warrantyRepository)
}

fun createRequestOtpUseCase(warrantyRepository: WarrantyRepository): RequestOtpUseCase {
    return RequestOtpUseCaseImpl(warrantyRepository)
}

fun createVerifyCode(warrantyRepository: WarrantyRepository): VerifyCodeUseCase {
    return VerifyCodeUseCaseImpl(warrantyRepository)
}

fun createPasswordUseCase(warrantyRepository: WarrantyRepository): CreatePasswordUseCase {
    return CreatePasswordUseCaseImpl(warrantyRepository)
}

fun createLoginUseCase(warrantyRepository: WarrantyRepository): LoginUseCase {
    return LoginUseCaseImpl(warrantyRepository)
}

fun createGetProductDetailUseCase(warrantyRepository: WarrantyRepository): GetProductDetailUseCase {
    return GetProductDetailUseCaseImpl(warrantyRepository)
}

fun createLogoutUseCase(warrantyRepository: WarrantyRepository): LogoutUseCase {
    return LogoutUseCaseImpl(warrantyRepository)
}

fun createChangePasswordUseCase(warrantyRepository: WarrantyRepository): ChangePasswordUseCase {
    return ChangePasswordUseCaseImpl(warrantyRepository)
}

fun createGetUserProfileUseCase(warrantyRepository: WarrantyRepository): GetUserProfileUseCase {
    return GetUserProfileUseCaseImpl(warrantyRepository)
}

fun createUpdateProfileUseCase(warrantyRepository: WarrantyRepository): UpdateProfileUseCase {
    return UpdateProfileUseCaseImpl(warrantyRepository)
}

fun createUpdateProfileUseCaseFirstTime(warrantyRepository: WarrantyRepository): UpdateProfileUseCaseFirstTime {
    return UpdateProfileUseCaseFirstTimeImpl(warrantyRepository)
}

fun createRecoverPassUseCase(warrantyRepository: WarrantyRepository): RecoverPasswordUseCase {
    return RecoverPasswordUseCaseImpl(warrantyRepository)
}

fun createSendFeedbackUseCase(warrantyRepository: WarrantyRepository): SendFeedbackUseCase {
    return SendFeedbackUseCaseImpl(warrantyRepository)
}

fun createSendFcmTokenUseCase(warrantyRepository: WarrantyRepository): SendFcmTokenUseCase {
    return SendFcmTokenUseCaseImpl(warrantyRepository)
}

fun createGetListNotificationUseCase(warrantyRepository: WarrantyRepository): GetListNotificationUseCase {
    return GetListNotificationUseCaseImpl(warrantyRepository)
}

fun createRegisterAgencyUseCase(warrantyRepository: WarrantyRepository): RegisterAgencyUseCase {
    return RegisterAgencyUseCaseImpl(warrantyRepository)
}

fun createGetListAgencyUseCase(warrantyRepository: WarrantyRepository): GetListAgencyUseCase {
    return GetListAgencyUseCaseImpl(warrantyRepository)
}

fun createGetListDistrictUseCase(warrantyRepository: WarrantyRepository): GetListDistrictUseCase {
    return GetListDistrictUseCaseImpl(warrantyRepository)
}

fun createGetListProvinceUseCase(warrantyRepository: WarrantyRepository): GetListProvinceUseCase {
    return GetListProvinceUseCaseImpl(warrantyRepository)
}

fun createGetListWardUseCase(warrantyRepository: WarrantyRepository): GetListWardUseCase {
    return GetListWardUseCaseImpl(warrantyRepository)
}

fun createGetAgencyInfoUseCase(warrantyRepository: WarrantyRepository): GetAgencyInfoUseCase {
    return GetAgencyInfoUseCaseImpl(warrantyRepository)
}

fun createAddWarrantyRepairUseCase(warrantyRepository: WarrantyRepository): AddWarrantyRepairUseCase {
    return AddWarrantyRepairUseCaseImpl(warrantyRepository)
}

fun createGetListRepairCategoryUseCase(warrantyRepository: WarrantyRepository): GetListRepairCategoryUseCase {
    return GetListRepairCategoryUseCaseImpl(warrantyRepository)
}

fun createGetListWarrantyRepairUseCase(warrantyRepository: WarrantyRepository): GetListWarrantyRepairUseCase {
    return GetListWarrantyRepairUseCaseImpl(warrantyRepository)
}

fun createDeleteAccountUseCase(warrantyRepository: WarrantyRepository): DeleteAccountUseCase {
    return DeleteAccountUseCaseImpl(warrantyRepository)
}

fun createCheckProductStatusUseCase(warrantyRepository: WarrantyRepository): CheckProductStatusUseCase {
    return CheckProductStatusUseCaseImpl(warrantyRepository)
}