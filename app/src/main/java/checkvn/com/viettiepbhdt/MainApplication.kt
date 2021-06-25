package checkvn.com.viettiepbhdt

import android.app.Application
import android.content.ContextWrapper
import androidx.multidex.MultiDex
import checkvn.com.viettiepbhdt.di.module.AppModule
import checkvn.com.viettiepbhdt.di.module.NetworkModule
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.pixplicity.easyprefs.library.Prefs
import com.zing.zalo.zalosdk.oauth.ZaloSDKApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(NetworkModule, AppModule))
        }

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
        ZaloSDKApplication.wrap(this)
    }
}
