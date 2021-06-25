package checkvn.com.viettiepbhdt.presentation.ui.authentication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.SocialLoginMethod
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.SocialLoginMethod.*
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.SCREENNAME.CHANGEPASSWORD
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.zing.zalo.zalosdk.oauth.LoginVia
import com.zing.zalo.zalosdk.oauth.OAuthCompleteListener
import com.zing.zalo.zalosdk.oauth.OauthResponse
import com.zing.zalo.zalosdk.oauth.ZaloSDK
import kotlinx.android.synthetic.main.activity_authentication.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AuthenticationActivity : AppCompatActivity() {

    private val GOOGLE_REQUEST_CODE: Int = 1000

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private lateinit var auth: FirebaseAuth
    val viewModel: AuthenticationViewModel by viewModel()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_authentication)
        initObserver()
        determineAction()
//        getHashKey()
    }

    private fun getHashKey() {
        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("HashKey: ", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    private fun determineAction() {
        val bundle = intent.extras
        bundle?.run {
            val actionName = getString(Constants.ACTION_NAME) ?: return
            when (Constants.SCREENNAME.valueOf(actionName)) {
                CHANGEPASSWORD -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.changePasswordFragment)
                }
                else -> throw Exception("Action is not defined")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun initObserver() {
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) showLoading()
            else hideLoading()
        })

        viewModel.socialLoginMethod.observe(this, Observer {
            when (it) {
                GOOGLE -> loginWithSocialNetwork(GOOGLE)
                ZALO -> loginWithSocialNetwork(ZALO)
                FACEBOOK -> loginWithSocialNetwork(FACEBOOK)
            }
        })

        viewModel.isTokenValid.observe(this, Observer { isValid ->
            if (isValid) {
                navigateTo(MainActivity::class.java)
            } else {
                removeToken()
                printLog("Invalid token...")
            }
        })
    }

    private fun showLoading() {
        loadingView.isVisible = true
    }

    private fun hideLoading() {
        loadingView.isVisible = false
    }

    private fun loginWithSocialNetwork(network: SocialLoginMethod) {
        showLoading()
        when (network) {
            GOOGLE -> loginWithGoogle()
            FACEBOOK -> loginWithFacebook()
            ZALO -> loginWithZalo()
        }
    }

    private fun loginWithFacebook() {
        val permissions = arrayListOf("email", "public_profile")

        LoginManager.getInstance()
            .logInWithReadPermissions(this@AuthenticationActivity, permissions)

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onError(error: FacebookException?) {
                    printError(error)
                    hideLoading()
                }

                override fun onCancel() {
                    print("Facebook login onCancel")
                }

                override fun onSuccess(result: LoginResult) {
                    result.accessToken?.token?.run { viewModel.saveToken(this, FACEBOOK) }
                    handleFacebookAccessToken(result.accessToken)
                }
            })
    }

    private fun loginWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().requestId().requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val googleSignInIntent = googleSignInClient.signInIntent
        startActivityForResult(googleSignInIntent, GOOGLE_REQUEST_CODE)
    }

    private fun loginWithZalo() {
        ZaloSDK.Instance.authenticate(this, LoginVia.APP_OR_WEB, object : OAuthCompleteListener() {
            override fun onAuthenError(errorCode: Int, message: String?) {
                printLog(errorCode.toString() + message)
                hideLoading()
            }

            override fun onGetOAuthComplete(response: OauthResponse?) {
                val code = response?.oauthCode
                code?.let {
                    viewModel.saveToken(it, ZALO)
                    handleZaloData()
                }
            }
        })
    }

    private fun handleZaloData() {
        val fields = arrayOf("name", "picture", "gender", "id", "birthday")
        ZaloSDK.Instance.getProfile(this, {
            viewModel.saveZaloData(it)
            savePrefs(Constants.BIRTH, it.getString("birthday"))
            savePrefs(Constants.USER_ID, it.getString("id"))
            val genders = resources.getStringArray(R.array.genders).toCollection(ArrayList())
            val gender: String = when (it.getString("gender")) {
                "male" -> genders[0]
                "female" -> genders[1]
                else -> genders.last()
            }
            savePrefs(Constants.GENDER, gender)
        }, fields)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        handleGoogleSignInResult(data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        ZaloSDK.Instance.onActivityResult(this, requestCode, resultCode, data)
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { viewModel.saveToken(it, GOOGLE) }
            account?.photoUrl.toString().let { viewModel.savePersonalInfo(avatarUri = it) }
            firebaseAuthWithGoogle(account!!)
        } catch (exception: ApiException) {
            printError(exception)
            hideLoading()
        }
    }

    private fun firebaseAuthWithGoogle(googleAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        signInWithCredentials(credential)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        signInWithCredentials(credential)
    }

    private fun signInWithCredentials(credential: AuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                savePrefs(Constants.USER_ID, user?.uid)
            } else {
                showToast(R.string.error_on_login)
                hideLoading()
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) return
        viewModel.savePersonalInfo(
            currentUser.displayName,
            currentUser.email,
            currentUser.photoUrl.toString()
        )
        navigateTo(MainActivity::class.java)
    }
}
