package checkvn.com.viettiepbhdt.utils.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.RepairCategoryResult
import checkvn.com.viettiepbhdt.presentation.ui.productdetail.RepairCategoryAdapter
import checkvn.com.viettiepbhdt.utils.IntegerCallBack
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import checkvn.com.viettiepbhdt.utils.StringCallBack
import kotlinx.android.synthetic.main.awesome_dialog.*
import kotlinx.android.synthetic.main.warranty_repair_dialog.*
import kotlinx.android.synthetic.main.warranty_repair_dialog.dialog_negative_button
import kotlinx.android.synthetic.main.warranty_repair_dialog.dialog_positive_button
import kotlinx.android.synthetic.main.warranty_repair_dialog.dialog_title
import kotlinx.android.synthetic.main.warranty_repair_dialog.inputText

class WarrantyRepairDialog constructor(context: Context) : Dialog(context) {

    private var adapter: RepairCategoryAdapter? = null
    private lateinit var integerCallBack: IntegerCallBack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.warranty_repair_dialog)
        window?.let {
            val metrics = context.resources.displayMetrics
            val width = metrics.widthPixels
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout((4 * width) / 5, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        initAdapter()
        setCanceledOnTouchOutside(true)
        setOnNegativePressed {
            this.dismiss()
        }
    }

    private fun initAdapter() {
        adapter = RepairCategoryAdapter()
        adapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                adapter?.setSelection(position)
            }
        })
        rv_repair_category.adapter = adapter
    }

    fun setHint(@StringRes resId: Int): WarrantyRepairDialog {
        inputText.setHint(resId)
        return this
    }

    fun setHint(hint: String): WarrantyRepairDialog {
        inputText.hint = hint
        return this
    }

    fun getInputText(): String {
        return inputText.text.toString()
    }

    fun setItems(items: List<RepairCategoryResult>): WarrantyRepairDialog {
        adapter?.setItems(items)
        return this
    }

    fun getRepairCategoryIds(): String? {
        return adapter?.getRepairCategoryIds()
    }

    fun setOnItemSelectedListener(callback: IntegerCallBack): WarrantyRepairDialog {
        this.integerCallBack = callback
        return this
    }

    fun setOnPositivePressed(onPositivePressed: (WarrantyRepairDialog) -> Unit): WarrantyRepairDialog {
        dialog_positive_button.setOnClickListener {
            onPositivePressed(this)
        }
        return this
    }

    fun setOnNegativePressed(onNegativePressed: (WarrantyRepairDialog) -> Unit): WarrantyRepairDialog {
        dialog_negative_button.setOnClickListener {
            onNegativePressed(this)
        }
        return this
    }

    fun negativeButtonVisibility(visibility: Boolean = true): WarrantyRepairDialog {
        dialog_negative_button?.isGone = !visibility
        return this
    }

    fun setTextNegativeButton(@StringRes textId: Int): WarrantyRepairDialog {
        dialog_negative_button.text = context.resources.getString(textId)
        return this
    }

    fun setTextPositiveButton(@StringRes textId: Int): WarrantyRepairDialog {
        dialog_positive_button.text = context.resources.getString(textId)
        return this
    }

    fun setDialogTitle(res: Int): WarrantyRepairDialog {
        dialog_title?.text = context.resources.getString(res)
        dialog_title?.isVisible = true
        return this
    }

    fun setDialogTitle(title: String): WarrantyRepairDialog {
        dialog_title?.text = title
        return this
    }

    fun showDialogTitle(isShow: Boolean): WarrantyRepairDialog {
        dialog_title?.isGone = !isShow
        return this
    }

    fun setPositiveButtonColor(colorRes: Int): WarrantyRepairDialog {
        dialog_positive_button.setTextColor(colorRes)
        return this
    }

    fun setPositiveButtonAllCap(isAllCaps: Boolean): WarrantyRepairDialog {
        dialog_positive_button.isAllCaps = isAllCaps
        return this
    }

    fun setNegativeButtonAllCap(isAllCaps: Boolean): WarrantyRepairDialog {
        dialog_negative_button.isAllCaps = isAllCaps
        return this
    }

    fun setNagativeButtonColor(colorRes: Int): WarrantyRepairDialog {
        dialog_negative_button.setTextColor(colorRes)
        return this
    }

    fun showDialog(): WarrantyRepairDialog {
        super.show()
        return this
    }

    fun dismissWhenClickOutside(isDismiss: Boolean = true): WarrantyRepairDialog {
        setCanceledOnTouchOutside(isDismiss)
        return this
    }
}
