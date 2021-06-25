package checkvn.com.viettiepbhdt.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.IntegerCallBack
import kotlinx.android.synthetic.main.custom_input_field.view.*

class CustomInputField : LinearLayout {

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
        LayoutInflater.from(context).inflate(R.layout.custom_input_field, this, true)
    }

    private fun initParams(context: Context, @Nullable attrs: AttributeSet) {
        val attrArr =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomInputField, 0, 0)
        val labelVisible =
            attrArr.getBoolean(R.styleable.CustomInputField_cif_label_visible, true)
        val inputVisible =
            attrArr.getBoolean(R.styleable.CustomInputField_cif_input_visible, true)
        val textLabel = attrArr.getString(R.styleable.CustomInputField_cif_text_label) ?: ""
        val textInput = attrArr.getString(R.styleable.CustomInputField_cif_text_input) ?: ""
        val hintInput = attrArr.getString(R.styleable.CustomInputField_cif_hint_input) ?: ""

        setTextLabel(textLabel)
        setLabelVisible(labelVisible)
        setTextInput(textInput)
        setInputVisible(inputVisible)
        setHintInput(hintInput)
    }

    fun setTextLabel(text: String) {
        textLabel.text = text
    }

    fun setLabelVisible(isVisible: Boolean) {
        textLabel.isVisible = isVisible
    }

    fun setTextInput(text: String) {
        inputText.setText(text)
    }

    fun getTextInput(): String {
        return inputText.text.toString()
    }

    fun setInputVisible(isVisible: Boolean) {
        inputText.isVisible = isVisible
    }

    fun setHintInput(text: String) {
        inputText.hint = text
    }
}