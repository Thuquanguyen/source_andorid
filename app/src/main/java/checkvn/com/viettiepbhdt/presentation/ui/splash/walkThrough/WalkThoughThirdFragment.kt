package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.showToast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_walk_though_third.*


class WalkThoughThirdFragment : BaseWalkThoughFragment() {

    private lateinit var rxPermission: RxPermissions
    override var layoutId: Int = R.layout.fragment_walk_though_third

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermission = RxPermissions(this)
    }

    override fun initViewListener() {
        btnContinue.setOnClickListener {
            requestLocationPermission()
        }
    }

    override fun moveToNextFragment() {
        viewModel.screenIndex.value = 2
    }

    private fun requestLocationPermission() {
        rxPermission.request(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION).subscribe { grant ->
            if (grant) {
                showToast(R.string.request_location_permission_success)
            } else {
                showToast(R.string.have_no_location_permission)
            }
            moveToNextFragment()
        }
    }
}
