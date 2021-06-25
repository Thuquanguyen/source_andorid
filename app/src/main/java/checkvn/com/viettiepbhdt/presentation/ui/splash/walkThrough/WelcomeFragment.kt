package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel
import checkvn.com.viettiepbhdt.utils.setUsed
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    private val TAG = WelcomeFragment::class.java.name
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initViewListener()
        setUsed()
    }

    private fun initViewListener() {
        btnDiscover.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_splashFragmentContainer)
        }

        tvLogin.setOnClickListener {
            moveToAuthenticationActivity()
        }
    }

    private fun moveToAuthenticationActivity() {
        activity?.run {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
