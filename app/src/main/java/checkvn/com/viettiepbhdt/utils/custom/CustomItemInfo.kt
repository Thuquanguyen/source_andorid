package checkvn.com.viettiepbhdt.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import kotlinx.android.synthetic.main.custom_item_info.view.*

class CustomItemInfo : LinearLayout {

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
        LayoutInflater.from(context).inflate(R.layout.custom_item_info, this, true)
    }

    private fun initParams(context: Context, @Nullable attrs: AttributeSet) {
        val attrArr = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomItemInfo, 0, 0)
        val labelVisible = attrArr.getBoolean(R.styleable.CustomItemInfo_cii_label_visible, true)
        val infoVisible = attrArr.getBoolean(R.styleable.CustomItemInfo_cii_info_visible, true)
        val textLabel = attrArr.getString(R.styleable.CustomItemInfo_cii_text_label) ?: ""
        val textInfo = attrArr.getString(R.styleable.CustomItemInfo_cii_text_info) ?: ""

        setTextLabel(textLabel)
        setLabelVisible(labelVisible)
        setTextInfo(textInfo)
        setInfoVisible(infoVisible)
    }

    fun setTextLabel(text: String) {
        tv_label.text = text
    }

    fun setLabelVisible(isVisible: Boolean) {
        tv_label.isVisible = isVisible
    }

    fun setTextInfo(text: String) {
        tv_info.text = text
    }

    fun getTextInfo(): String {
        return tv_info.text.toString()
    }

    fun setInfoVisible(isVisible: Boolean) {
        tv_info.isVisible = isVisible
    }
}