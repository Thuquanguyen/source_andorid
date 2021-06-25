package checkvn.com.viettiepbhdt.utils.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.IntegerCallBack
import checkvn.com.viettiepbhdt.utils.goneViews
import checkvn.com.viettiepbhdt.utils.visibleViews
import kotlinx.android.synthetic.main.awesome_dialog.*

internal const val TYPE_NOTIFY_WITHOUT_IMAGE = 1
internal const val TYPE_NOTIFY_WITH_IMAGE = 2
internal const val TYPE_SELECTION = 3
internal const val TYPE_TEXT_INPUT = 4
internal const val TYPE_CONFIRM = 5
internal const val TYPE_CONFIRM_WITH_INPUT = 6

class AwesomeDialog constructor(context: Context) : Dialog(context) {

    private var adapter: CustomSelectionAdapter? = null
    private lateinit var integerCallBack: IntegerCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.awesome_dialog)
        window?.let {
            val metrics = context.resources.displayMetrics
            val width = metrics.widthPixels
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout((4 * width) / 5, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        setDialogType(TYPE_NOTIFY_WITHOUT_IMAGE)
        setCanceledOnTouchOutside(true)
        setOnNegativePressed {
            this.dismiss()
        }
    }

    fun setDialogType(type: Int): AwesomeDialog {
        when (type) {
            TYPE_NOTIFY_WITHOUT_IMAGE -> {
                visibleViews(dialog_content, dialog_action)
                goneViews(
                    view_transparent, dialog_image, view_top, checkbox_not_show_again,
                    dialog_title, dialog_selection, dialog_confirm_button
                )
            }
            TYPE_NOTIFY_WITH_IMAGE -> {
                visibleViews(
                    view_transparent, dialog_image, view_top, dialog_title,
                    dialog_content, dialog_action, dialog_confirm_button, checkbox_not_show_again
                )
                goneViews(
                    dialog_selection, dialog_positive_button, dialog_negative_button
                )
            }
            TYPE_SELECTION -> {
                visibleViews(dialog_title, dialog_selection)
                goneViews(
                    view_transparent, dialog_image, view_top,
                    dialog_content, dialog_action, checkbox_not_show_again
                )
                adapter = CustomSelectionAdapter()

                adapter?.setOnItemClickListener {
                    adapter?.setSelection(it)
                    if (::integerCallBack.isInitialized) integerCallBack(it)
                    dismiss()
                }

                dialog_selection.adapter = adapter
            }
            TYPE_TEXT_INPUT -> {
                visibleViews(inputText, dialog_negative_button, dialog_action)
                goneViews(
                    view_transparent, dialog_image, view_top,
                    dialog_content, checkbox_not_show_again
                )
                dismissWhenClickOutside(false)
            }
            TYPE_CONFIRM -> {
                visibleViews(
                    view_transparent, dialog_image, view_top, dialog_title, checkbox_not_show_again,
                    dialog_content, dialog_action, dialog_negative_button, dialog_confirm_button
                )
                goneViews(
                    dialog_selection, dialog_positive_button
                )
                dismissWhenClickOutside(false)
            }
            TYPE_CONFIRM_WITH_INPUT -> {
                visibleViews(inputText, dialog_negative_button, dialog_action, dialog_content)
                goneViews(
                    view_transparent, dialog_image, view_top,
                    checkbox_not_show_again
                )
                dismissWhenClickOutside(false)
            }
            else -> throw Exception("Dialog type is not defined.")
        }
        return this
    }

    fun setHint(@StringRes resId: Int): AwesomeDialog {
        inputText.setHint(resId)
        return this
    }

    fun setHint(hint: String): AwesomeDialog {
        inputText.hint = hint
        return this
    }

    fun setInputType(type: Int): AwesomeDialog {
        inputText.inputType = type
        return this
    }

    fun setInputText(message: String): AwesomeDialog {
        inputText.setText(message)
        return this
    }

    fun getInputText(): String {
        return inputText.text.toString()
    }

    fun setItems(items: ArrayList<String>): AwesomeDialog {
        adapter?.setItems(items)
        return this
    }

    fun setOnItemSelectedListener(callback: IntegerCallBack): AwesomeDialog {
        this.integerCallBack = callback
        return this
    }

    fun setItemSelection(position: Int): AwesomeDialog {
        adapter?.setSelection(position)
        return this
    }

    fun setOnPositivePressed(onPositivePressed: (AwesomeDialog) -> Unit): AwesomeDialog {
        dialog_positive_button.setOnClickListener {
            onPositivePressed(this)
        }
        return this
    }

    fun setOnNegativePressed(onNegativePressed: (AwesomeDialog) -> Unit): AwesomeDialog {
        dialog_negative_button.setOnClickListener {
            onNegativePressed(this)
        }
        return this
    }

    fun setOnConfirmPressed(onConfirmPressed: (AwesomeDialog) -> Unit): AwesomeDialog {
        dialog_confirm_button.setOnClickListener {
            onConfirmPressed(this)
        }
        return this
    }

    fun negativeButtonVisibility(visibility: Boolean = true): AwesomeDialog {
        dialog_negative_button?.isGone = !visibility
        return this
    }

    fun setTextNegativeButton(@StringRes textId: Int): AwesomeDialog {
        dialog_negative_button.text = context.resources.getString(textId)
        return this
    }

    fun setTextPositiveButton(@StringRes textId: Int): AwesomeDialog {
        dialog_positive_button.text = context.resources.getString(textId)
        return this
    }

    fun setDialogTitle(res: Int): AwesomeDialog {
        dialog_title?.text = context.resources.getString(res)
        dialog_title?.isVisible = true
        return this
    }

    fun setDialogTitle(title: String): AwesomeDialog {
        dialog_title?.text = title
        return this
    }

    fun showDialogTitle(isShow: Boolean): AwesomeDialog {
        dialog_title?.isGone = !isShow
        return this
    }

    fun setContent(vararg res: Int): AwesomeDialog {
        var content = ""
        for (i in res.indices) {
            content += context.resources.getString(res[i])
            if (i < res.size - 1) content += "\n"
        }
        dialog_content?.text = content
        return this
    }

    fun setContent(vararg contents: String): AwesomeDialog {
        var content = ""
        for (i in contents.indices) {
            content += contents[i]
            if (i < contents.size - 1) content += "\n"
        }
        dialog_content?.text = content
        return this
    }

    fun setPositiveButtonColor(colorRes: Int): AwesomeDialog {
        dialog_positive_button.setTextColor(colorRes)
        return this
    }

    fun setPositiveButtonAllCap(isAllCaps: Boolean): AwesomeDialog {
        dialog_positive_button.isAllCaps = isAllCaps
        return this
    }

    fun setNegativeButtonAllCap(isAllCaps: Boolean): AwesomeDialog {
        dialog_negative_button.isAllCaps = isAllCaps
        return this
    }

    fun setNagativeButtonColor(colorRes: Int): AwesomeDialog {
        dialog_negative_button.setTextColor(colorRes)
        return this
    }

    fun setImage(imgRes: Int): AwesomeDialog {
        dialog_image.setImageResource(imgRes)
        return this
    }

    fun showDialog(): AwesomeDialog {
        super.show()
        return this
    }

    fun dismissWhenClickOutside(isDismiss: Boolean = true): AwesomeDialog {
        setCanceledOnTouchOutside(isDismiss)
        return this
    }

    fun setButtonColor(color: Int, context: Context): AwesomeDialog {
        dialog_confirm_button.backgroundTintList = ContextCompat.getColorStateList(context, color);
        return this
    }

    fun showCheckBox(isVisible: Boolean) {
        checkbox_not_show_again.isVisible = isVisible
    }

    fun isCheckedNotShowAgain(): Boolean {
        return checkbox_not_show_again.isChecked
    }
}
