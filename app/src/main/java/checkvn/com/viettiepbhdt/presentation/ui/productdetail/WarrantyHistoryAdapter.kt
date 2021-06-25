package checkvn.com.viettiepbhdt.presentation.ui.productdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.WarrantyHistory
import checkvn.com.viettiepbhdt.domain.entities.WarrantyRepairResult
import checkvn.com.viettiepbhdt.utils.DateUtils.Companion.DATE_FORMAT_DAY_MONTH_YEAR
import checkvn.com.viettiepbhdt.utils.DateUtils.Companion.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS
import checkvn.com.viettiepbhdt.utils.formatDate
import checkvn.com.viettiepbhdt.utils.getPhone
import kotlinx.android.synthetic.main.item_warranty_history.view.*

class WarrantyHistoryAdapter(
    private var items: List<WarrantyRepairResult>,
    private val context: Context
) :
    RecyclerView.Adapter<WarrantyHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_warranty_history, parent, false)
        )
    }

    fun setData(items: List<WarrantyRepairResult>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvServiceName.text = items[position].QRCodeWarrantyRepairCategoryName.replace(",", ", ")
        if (!items[position].CreateDate.isNullOrEmpty()) {
            holder.tvWarrantyDate.text = context.getString(
                R.string.warranty_date,
                formatDate(
                    items[position].CreateDate,
                    DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS,
                    DATE_FORMAT_DAY_MONTH_YEAR
                )
            )
        }
        holder.tvStaffName.text = context.getString(R.string.staff_name, items[position].CreateByFullName)
        holder.tvStaffPhone.text = context.getString(R.string.staff_phone, items[position].CreateByPhone)
        holder.tvDescription.text =
            context.getString(R.string.warranty_description, items[position].Content)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvServiceName: TextView = view.tv_service_name
        val tvWarrantyDate: TextView = view.tv_warranty_date
        val tvStaffName: TextView = view.tv_staff_name
        val tvStaffPhone: TextView = view.tv_staff_phone
        val tvDescription: TextView = view.tv_description
    }
}
