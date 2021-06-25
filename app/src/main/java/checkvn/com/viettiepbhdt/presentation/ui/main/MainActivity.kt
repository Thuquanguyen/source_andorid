package checkvn.com.viettiepbhdt.presentation.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.Location
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.databinding.ActivityMainBinding
import checkvn.com.viettiepbhdt.domain.entities.LogoutResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.presentation.ui.active.ActiveFragment
import checkvn.com.viettiepbhdt.presentation.ui.active.TAG_ACTIVE_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.home.HomeFragment
import checkvn.com.viettiepbhdt.presentation.ui.home.TAG_HOME_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.productdetail.TAG_PRODUCT_DETAIL_FRAGMENT
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.ABOUT_US_URL
import checkvn.com.viettiepbhdt.utils.Constants.FAQ_URL
import checkvn.com.viettiepbhdt.utils.Constants.POLICY_URL
import checkvn.com.viettiepbhdt.utils.custom.AwesomeDialog
import checkvn.com.viettiepbhdt.utils.custom.TYPE_SELECTION
import checkvn.com.viettiepbhdt.utils.location.GpsUtils
import checkvn.com.viettiepbhdt.utils.receiver.NetworkChangeReceiver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pixplicity.easyprefs.library.Prefs
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), NetworkChangeReceiver.ConnectivityReceiverListener,
    View.OnClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainNavigator: MainNavigator
    private lateinit var rxPermission: RxPermissions
    private val viewModel: MainViewModel by viewModel()
    private val receiverNetwork = NetworkChangeReceiver()
    private var isOpenActive = true
    private var doubleBackPressed = false
    private var languageDialog: AwesomeDialog? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainNavigator = MainNavigator(this@MainActivity)
        rxPermission = RxPermissions(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initUI()
        initObserver()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(receiverNetwork, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        NetworkChangeReceiver.connectivityReceiverListener = this
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            setActionImage(true)
            super.onBackPressed()
            var activeFragment =
                supportFragmentManager?.findFragmentByTag(TAG_ACTIVE_FRAGMENT)
            if (activeFragment != null && activeFragment.isVisible) {
                (activeFragment as ActiveFragment).resumeCameraView()
            }
        } else {
            if (isHomeSelected()) {
                if (doubleBackPressed) {
                    finish()
                    return
                }
                this.doubleBackPressed = true
                showToast(R.string.confirm_exit_app)
                delayer(2000) {
                    doubleBackPressed = false
                }
            } else {
                setSelectedNavigation(0)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiverNetwork)
    }

    private fun isHomeSelected(): Boolean {
        return bnv_home.menu.findItem(R.id.menu_list).isChecked
    }

    private fun initUI() {
        mainNavigator.toHome()
        setLightStatusBar()
        setupDrawer()
        setListener()
    }

    private fun setupDrawer() {
        if (getUserId().isNullOrEmpty()) {
            visibleViews(tv_register, tv_sign_in)
            goneViews(tv_sign_out)
        } else {
            goneViews(tv_register, tv_sign_in)
            visibleViews(tv_sign_out)
        }
    }

    private fun initObserver() {
        viewModel.isLoading.observe(this, Observer {
            view_loading.isVisible = it
        })

        viewModel.logoutResult.observe(this, Observer {
            hideLoading()
            handleLogoutResult(it)
        })
    }

    private fun handleLogoutResult(it: Result<LogoutResult>) {
        Prefs.clear()
        navigateTo(AuthenticationActivity::class.java)
    }

    private fun setListener() {
        bnv_home.setOnNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.menu_home -> setCurrentItemViewPager(0)
                R.id.menu_list -> setCurrentItemViewPager(0)
                R.id.menu_website -> setCurrentItemViewPager(1)
                R.id.menu_notification -> setCurrentItemViewPager(2)
                R.id.menu_personal -> setCurrentItemViewPager(3)
            }
            true
        }

        fab_action.setOnClickListener {
            if (isOpenActive) {
                mainNavigator.toActive()
                setActionImage(false)
                requestUpdateLocation()
            } else {
                var detailFragment =
                    supportFragmentManager.findFragmentByTag(TAG_PRODUCT_DETAIL_FRAGMENT)
                if (detailFragment != null && detailFragment.isVisible) {
                    onBackPressed()
                }
            }
            supportFragmentManager.addOnBackStackChangedListener {
                if (supportFragmentManager.backStackEntryCount == 1)
                    setActionImage(true)
            }
        }

        coordinator.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            coordinator.getWindowVisibleDisplayFrame(r)
            val screenHeight = coordinator.rootView.height
            val keypadHeight = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) {
                goneViews(fab_action, bab_home)
            } else {
                visibleViews(fab_action, bab_home)
            }
        }

        tv_about.setOnClickListener {
            closeDrawer()
            mainNavigator.toWebsite(ABOUT_US_URL)
        }
        tv_policy.setOnClickListener {
            closeDrawer()
            mainNavigator.toWebsite(POLICY_URL)
        }
        tv_fanpage.setOnClickListener {
            startActivity(newFacebookIntent(packageManager, Constants.FACEBOOK_LINK))
        }
        tv_youtube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_LINK)))
        }
        tv_qa.setOnClickListener {
            closeDrawer()
            mainNavigator.toWebsite(FAQ_URL)
        }

        iv_close_drawer.setOnClickListener(this)
        ll_language.setOnClickListener(this)
        ll_hotline.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_sign_in.setOnClickListener(this)
        tv_sign_out.setOnClickListener(this)
    }

    private fun showDialogLanguage() {
        val filters =
            resources.getStringArray(R.array.app_language).toCollection(ArrayList())
        if (languageDialog == null) {
            languageDialog = AwesomeDialog(this)
                .showDialog()
                .setDialogTitle(R.string.language)
                .setDialogType(TYPE_SELECTION)
                .setItems(filters)
                .setOnItemSelectedListener {
                    tv_language.text = filters[it]
                }
        } else {
            languageDialog?.showDialog()
        }
    }

    private fun showPhoneCall() {
        try {
            val callIntent = Intent(Intent.ACTION_DIAL)
            val phone = getString(R.string.hotline)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("No SIM Found")
        }
    }

    private fun newFacebookIntent(pm: PackageManager, url: String): Intent? {
        var uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }

    fun showDrawer() {
        dl_menu.openDrawer(GravityCompat.END)
    }

    private fun closeDrawer() {
        dl_menu.closeDrawer(GravityCompat.END)
    }

    fun setSelectedNavigation(position: Int) {
        when (position) {
//            0 -> bnv_home.selectedItemId = R.id.menu_home
            0 -> bnv_home.selectedItemId = R.id.menu_list
            1 -> bnv_home.selectedItemId = R.id.menu_website
            2 -> bnv_home.selectedItemId = R.id.menu_notification
            3 -> bnv_home.selectedItemId = R.id.menu_personal
        }
    }

    private fun setCurrentItemViewPager(position: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT)
        if (fragment is HomeFragment) {
            fragment.setCurrentItemViewPager(position)
        }
        supportFragmentManager.popBackStack(TAG_HOME_FRAGMENT, 0)
    }

    fun setActionImage(isOpenActive: Boolean) {
        if (isOpenActive) {
            fab_action.setImageResource(R.drawable.ic_add_white_24dp)
        } else {
            fab_action.setImageResource(R.drawable.ic_scanner)
        }
        this.isOpenActive = isOpenActive
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {

    }

    private fun toAuthentication() {
        navigateTo(AuthenticationActivity::class.java, true)
    }

    private fun signOut() {
        showLoading()
        viewModel.logout()
    }

    private fun showLoading() {
        viewModel.isLoading.postValue(true)
    }

    private fun hideLoading() {
        viewModel.isLoading.postValue(false)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            tv_register.id -> toAuthentication()
            tv_sign_in.id -> toAuthentication()
            tv_sign_out.id -> signOut()
            ll_hotline.id -> showPhoneCall()
            ll_language.id -> showDialogLanguage()
            iv_close_drawer.id -> dl_menu.closeDrawer(GravityCompat.END)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.GPS_REQUEST) {
                updateLocation()
            }
        }
    }

    fun requestUpdateLocation() {
        setLastLocation("")
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        rxPermission.request(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe { grant ->
            if (grant) {
                checkStatusGps()
            } else {
                showToast(R.string.have_no_location_permission)
            }
        }
    }

    private fun checkStatusGps() {
        GpsUtils(this).turnGPSOn { isGPSEnable ->
            if (isGPSEnable) {
                updateLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d("onLocationChanged: ", "${location.latitude}:${location.longitude}")
                setLastLocation("${location.latitude}:${location.longitude}")
            } else {
                updateLocation()
            }
        }
    }
}
