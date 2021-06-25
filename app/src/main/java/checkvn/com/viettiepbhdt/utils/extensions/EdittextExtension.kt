package checkvn.com.viettiepbhdt.utils.extensions

import android.graphics.PorterDuff
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import checkvn.com.viettiepbhdt.R

fun EditText.changeBackgroundColor(isValid: Boolean = true) {
    val color = if (isValid) R.color.editTextUnderLineColor else R.color.colorTheme
    background.mutate().setColorFilter(resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
}

val EditText.value: String
    get() = text.toString().trim()

fun EditText.onTextChangeListener(onTextChange: (String) -> Unit) = addTextChangedListener {
    onTextChange(it.toString())
}