package checkvn.com.viettiepbhdt.presentation.ui.productdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.RepairCategoryResult
import checkvn.com.viettiepbhdt.utils.IntegerCallBack
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import checkvn.com.viettiepbhdt.utils.StringCallBack
import kotlinx.android.synthetic.main.item_product_error.view.*

class RepairCategoryAdapter :
    RecyclerView.Adapter<RepairCategoryAdapter.ViewHolder>() {

    private var items: List<RepairCategoryResult> = ArrayList()
    private lateinit var mOnItemClickListener: OnItemClickListener
    private lateinit var itemsChecked: ArrayList<Boolean>

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setItems(items: List<RepairCategoryResult>) {
        this.items = items
        itemsChecked = ArrayList()
        for (i in items.indices) {
            itemsChecked.add(false)
        }
        notifyDataSetChanged()
    }

    fun setSelection(position: Int) {
        if (itemsChecked.size > position) {
            var positionChecked = itemsChecked[position]
            itemsChecked[position] = !positionChecked
            notifyDataSetChanged()
        }
    }

    fun getRepairCategoryIds(): String {
        if (itemsChecked != null && itemsChecked.isNotEmpty()) {
            var categoryIds = StringBuilder()
            for (i in 0 until itemsChecked.size) {
                if (itemsChecked[i]) {
                    if (!categoryIds.isNullOrEmpty() && i != 0) {
                        categoryIds.append(",")
                    }
                    categoryIds.append(items[i].QRCodeWarrantyRepairCategory_ID)
                }
            }
            return categoryIds.toString()
        }
        return ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_error, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cbError.text = items[position].Name
        holder.cbError.setOnClickListener {
            mOnItemClickListener.onItemClick(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbError: CheckBox = view.cb_error
    }
}