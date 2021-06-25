package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.showToast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_camera_permission.*

class WalkThroughFirstFragment : BaseWalkThoughFragment() {

    private lateinit var rxPermission: RxPermissions
    override var layoutId: Int = R.layout.fragment_camera_permission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermission = RxPermissions(this)
    }

    override fun initViewListener() {
        btnTurnOnCamera.setOnClickListener {
            requestCameraPermission()
        }
    }

    override fun moveToNextFragment() {
        viewModel.screenIndex.value = 0
    }

    @SuppressLint("CheckResult")
    private fun requestCameraPermission() {
        rxPermission.request(Manifest.permission.CAMERA).subscribe { grant ->
            if (grant) {
                showToast(R.string.request_camera_permission_success)
            } else {
                showToast(R.string.have_no_camera_permission)
            }
            moveToNextFragment()
        }
    }

}
