package checkvn.com.viettiepbhdt.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import checkvn.com.viettiepbhdt.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

fun AppCompatActivity.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Fragment.showToast(@StringRes stringId: Int, isLong: Boolean = false) {
    context?.run {
        val duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(context, stringId, duration).show()
    }
}

fun AppCompatActivity.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags
        window.statusBarColor = Color.WHITE
    }
}

fun AppCompatActivity.clearLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        flags =
            flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags
        window.statusBarColor = getColor(R.color.colorPrimary)
    }
}

fun Fragment.ui(block: suspend () -> Unit) {
    if (activity != null && view != null && context != null) {
        CoroutineScope(Dispatchers.Main).launch {
            block()
        }
    }
}

fun AppCompatActivity.ui(block: () -> Unit) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
        block()
    } else {
        CoroutineScope(Dispatchers.Main).launch {
            block()
        }
    }
}


fun AppCompatActivity.navigateTo(cls: Class<*>, isFinishCurrentActivity: Boolean = true) {
    val intent = Intent(this, cls)
    startActivity(intent)
    if (isFinishCurrentActivity) finish()
}

fun Fragment.navigateTo(
    cls: Class<*>,
    isFinishCurrentActivity: Boolean = true,
    bundle: Bundle? = null
) {
    activity?.run {
        val intent = Intent(this, cls)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        if (isFinishCurrentActivity) finish()
    }
}

fun printLog(message: String?) {
    Timber.d(message)
}

fun printError(error: Exception?) {
    Timber.e(error)
}

fun delayer(delayTime: Long = 1000, block: () -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        delay(delayTime)
        block()
    }
}

fun Fragment.vibratePhone(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}
