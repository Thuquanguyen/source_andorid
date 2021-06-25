package checkvn.com.viettiepbhdt.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun goneViews(vararg views: View) {
    views.forEach {
        it.gone()
    }
}

fun visibleViews(vararg views: View) {
    views.forEach {
        it.visible()
    }
}

fun View.enabled() {
    isEnabled = true
}

fun View.disabled() {
    isEnabled = false
}

fun getSizeScreen(context: Context): DisplayMetrics = context.resources.displayMetrics
