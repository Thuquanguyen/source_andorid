package checkvn.com.viettiepbhdt.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import checkvn.com.viettiepbhdt.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun AppCompatActivity.addFragment(
    tag: String,
    layoutId: Int,
    allowStateLoss: Boolean = false,
    newInstance: () -> Fragment
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    val transaction = supportFragmentManager.beginTransaction()
        .replace(layoutId, fragment, tag)
    if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        transaction.commit()
    }
}

fun AppCompatActivity.addFragmentBackStack(
    tag: String,
    layoutId: Int,
    newInstance: () -> Fragment
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    if (!fragment.isAdded) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
            )
            .add(layoutId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }
}

fun AppCompatActivity.replaceFragmentBackStack(
    tag: String,
    layoutId: Int,
    newInstance: () -> Fragment
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right, R.anim.exit_to_left,
            R.anim.enter_from_left, R.anim.exit_to_right
        )
        .replace(layoutId, fragment, tag)
        .addToBackStack(tag)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.replaceFragmentBackStackBottomTop(
    tag: String,
    layoutId: Int,
    newInstance: () -> Fragment
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.slide_up, 0,
            R.anim.slide_down, 0
        )
        .replace(layoutId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}

fun AppCompatActivity.replaceFragmentWithOutBackStack(
    tag: String,
    layoutId: Int,
    newInstance: () -> Fragment
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    supportFragmentManager.beginTransaction()
        /*.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
        )*/
        .replace(layoutId, fragment, tag)
        .commit()
}

fun AppCompatActivity.replaceNewFragment(
    layoutId: Int,
    newInstance: () -> Fragment
) {
    val fragment = newInstance()
    supportFragmentManager.beginTransaction()
        /*.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
        )*/
        .replace(layoutId, fragment)
        .commit()
}

fun AppCompatActivity.toPreviousView() {
    supportFragmentManager.popBackStack()
}

fun Activity.hideKeyboard() {
    currentFocus?.run {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
            it.hideSoftInputFromWindow(windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }
}

fun Activity.showKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun Fragment.hideKeyBoardWhenClickOutSide(view: View, activity: Activity) {
    if (view !is EditText && view !is Button) {
        view.setOnTouchListener { _, _ ->
            activity.hideKeyboard()
            false
        }
    }
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            hideKeyBoardWhenClickOutSide(innerView, activity)
        }
    }
}

fun AppCompatActivity.hideKeyBoardWhenClickOutSide(view: View) {
    if (view !is EditText && view !is Button) {
        view.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            hideKeyBoardWhenClickOutSide(innerView)
        }
    }
}

fun Fragment.loadImage(uri: String?, imageView: ImageView) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .circleCrop().placeholder(R.drawable.ic_person)
        .error(R.drawable.ic_person)

    context?.run {
        Glide.with(this).load(uri)
            .apply(requestOptions).into(imageView)
    }
}

fun Activity.showToast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, resource, duration).show()

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    activity?.showToast(message, duration)
