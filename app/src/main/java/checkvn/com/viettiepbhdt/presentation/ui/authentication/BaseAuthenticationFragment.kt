package checkvn.com.viettiepbhdt.presentation.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.LoginResultType
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.SocialLoginMethod.*
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.navigateTo
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.bottom_social_authentication.*

abstract class BaseAuthenticationFragment : Fragment(), View.OnClickListener {

    val authViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseViewListener()
        initViewListener()
        observeDataChange()
        observeData()
    }

    private fun loginWithFacebook() {
        authViewModel.socialLoginMethod.value = FACEBOOK
    }

    private fun loginWithGoogle() {
        authViewModel.socialLoginMethod.value = GOOGLE
    }

    private fun loginWithZalo() {
        authViewModel.socialLoginMethod.value = ZALO
    }

    private fun observeDataChange() {
        authViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginResultType.OnError -> {
                    showToast(R.string.error_on_login)
                }
                is LoginResultType.OnSuccess -> {
                    navigateTo(MainActivity::class.java)
                }
                is LoginResultType.OnCancel -> {
                    showToast(R.string.cancel_on_login)
                }
            }
        })
    }

    fun popAllFragment() {
        findNavController().popBackStack(R.id.loginFragment, false)
    }

    private fun initBaseViewListener() {
        ic_facebook?.setOnClickListener(this)
        ic_google?.setOnClickListener(this)
        ic_zalo?.setOnClickListener(this)
    }

    fun showLoading() {
        authViewModel.isLoading.postValue(true)
    }

    fun hideLoading() {
        authViewModel.isLoading.postValue(false)
    }

    fun showErroNetwork() {
        showToast(R.string.no_internet_connection)
    }

    override fun onClick(view: View?) {
        if (authViewModel.isLoading.value == true)
            return
        else
            when (view?.id) {
                R.id.ic_facebook -> loginWithFacebook()
                R.id.ic_google -> loginWithGoogle()
                R.id.ic_zalo -> loginWithZalo()
                R.id.imgClose -> popAllFragment()
            }
    }

    abstract var layoutId: Int
    abstract fun initViewListener()
    abstract fun observeData()
}
