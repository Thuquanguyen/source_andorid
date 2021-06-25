package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.LoginResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.savePrefs
import checkvn.com.viettiepbhdt.utils.setUsed
import checkvn.com.viettiepbhdt.utils.showToast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_walk_though_fifth.*
import org.koin.android.viewmodel.ext.android.viewModel

class WalkThoughFifthFragment : BaseWalkThoughFragment() {

    override var layoutId: Int = R.layout.fragment_walk_though_fifth
    private val authViewModel: AuthenticationViewModel by viewModel()

    override fun initViewListener() {
        btnStart.setOnClickListener {
            moveToNextFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    override fun moveToNextFragment() {
        viewModel.screenIndex.value = 5
    }

    private fun observeData() {

    }
}
