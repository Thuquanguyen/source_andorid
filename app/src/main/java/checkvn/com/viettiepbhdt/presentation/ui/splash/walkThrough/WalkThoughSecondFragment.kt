package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.fragment_tracking_warrrenty.*

class WalkThoughSecondFragment : BaseWalkThoughFragment() {

    override var layoutId: Int = R.layout.fragment_tracking_warrrenty

    override fun initViewListener() {
        btnTurnOnNotification.setOnClickListener {
            showToast(R.string.request_notification_permission_success)
            moveToNextFragment()
        }
    }

    override fun moveToNextFragment() {
        viewModel.screenIndex.value = 1
    }

}
