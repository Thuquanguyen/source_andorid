package checkvn.com.viettiepbhdt.utils.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.extensions.value
import kotlinx.android.synthetic.main.custom_menu_item.view.*

class CustomMenuItem : RelativeLayout {

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        initView(context)
        initParams(context, attrs)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initView(context)
        initParams(context, attrs)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.custom_menu_item, this, true)
    }

    private fun initParams(context: Context, @Nullable attrs: AttributeSet) {
        val attrArr =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomMenuItem, 0, 0)
        val underlineVisible =
            attrArr.getBoolean(R.styleable.CustomMenuItem_underline_visible, false)
        val textStart = attrArr.getString(R.styleable.CustomMenuItem_text_start) ?: ""
        val textEnd = attrArr.getString(R.styleable.CustomMenuItem_text_end) ?: ""
        val drawableStart = attrArr.getDrawable(R.styleable.CustomMenuItem_drawable_start)
        val drawableEnd = attrArr.getDrawable(R.styleable.CustomMenuItem_drawable_end)
        val drawableStartVisible =
            attrArr.getBoolean(R.styleable.CustomMenuItem_drawable_start_visible, false)
        val drawableEndVisible =
            attrArr.getBoolean(R.styleable.CustomMenuItem_drawable_end_visible, true)
        val isFirst = attrArr.getBoolean(R.styleable.CustomMenuItem_is_first, false)
        val isLast = attrArr.getBoolean(R.styleable.CustomMenuItem_is_last, false)
        val isSwitchVisible =
            attrArr.getBoolean(R.styleable.CustomMenuItem_switch_end_visible, false)
        val endTextEditable =
            attrArr.getBoolean(R.styleable.CustomMenuItem_end_text_editable, false)

        underlineVisible(underlineVisible)
        setTextStart(textStart)
        setTextEnd(textEnd)
        drawableStartVisible(drawableStartVisible)
        drawableEndVisible(drawableEndVisible)
        isFirst(isFirst)
        isLast(isLast)
        switchVisible(isSwitchVisible)
        drawableStart(drawableStart)
        drawableEnd(drawableEnd)
        textContent(endTextEditable)
    }

    private fun drawableStartVisible(isVisible: Boolean) {
        drawableStart.isVisible = isVisible
    }

    private fun drawableEndVisible(isVisible: Boolean) {
        drawableEnd.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun switchVisible(isVisible: Boolean) {
        switchEnd.isVisible = isVisible
    }

    private fun underlineVisible(isVisible: Boolean) {
        underline.isVisible = isVisible
    }

    fun textStartVisible(isVisible: Boolean) {
        tvStart.isVisible = isVisible
    }

    fun textEndVisible(isVisible: Boolean) {
        tvEnd.isVisible = isVisible
    }

    fun setTextStart(text: String) {
        tvStart.text = text
    }

    fun setTextEnd(text: String) {
        if (tvEnd.isVisible) tvEnd.text = text
        if (editText.isVisible) editText.setText(text)
    }

    private fun isLast(isLast: Boolean) {
        if (isLast) cardView.setBackgroundResource(R.drawable.background_round_bottom)
    }

    private fun isFirst(isFirst: Boolean) {
        if (isFirst) cardView.setBackgroundResource(R.drawable.background_round_top)
    }

    private fun drawableStart(res: Drawable?) {
        if (res == null) return
        drawableStart.isVisible = true
        drawableStart.setImageDrawable(res)
    }

    private fun drawableEnd(res: Drawable?) {
        if (res == null) return
        drawableEnd.isVisible = true
        drawableEnd.setImageDrawable(res)
    }

    private fun textContent(isShown: Boolean) {
        editText.isVisible = isShown
        tvEnd.isVisible = !isShown
    }

    fun getTextEnd(): String {
        return if (editText.isVisible) editText.value
        else tvEnd.text.toString()
    }
}