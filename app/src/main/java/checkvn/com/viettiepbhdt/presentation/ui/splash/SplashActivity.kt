package checkvn.com.viettiepbhdt.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.products.ProductsViewModel
import checkvn.com.viettiepbhdt.utils.getUserId
import checkvn.com.viettiepbhdt.utils.isUsed
import checkvn.com.viettiepbhdt.utils.navigateTo
import com.google.firebase.iid.FirebaseInstanceId
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfFirstTimeOpen()
        sendFcmTokenToServer()
    }

    private fun checkIfFirstTimeOpen() {
        if (isUsed()) checkIfAlreadyLogin()
        else {
            setTheme(R.style.AppTheme)
            setContentView(R.layout.activity_splash)
        }
    }

    private fun checkIfAlreadyLogin() {
        if (getUserId().isNullOrEmpty()) navigateTo(AuthenticationActivity::class.java)
        else navigateTo(MainActivity::class.java)
    }

    private fun sendFcmTokenToServer() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            viewModel.sendFcmToken(token)
        }
    }
}
