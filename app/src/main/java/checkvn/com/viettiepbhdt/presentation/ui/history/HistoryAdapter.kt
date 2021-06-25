package checkvn.com.viettiepbhdt.presentation.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.Product
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(private val items: List<Product>, private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.iv_product
        val tvName: TextView = view.tv_name
        val tvTime: TextView = view.tv_time
        val tvAccount: TextView = view.tv_account
        val divider: View = view.divider

        fun bindData(product: Product) {
            divider.isVisible = items.indexOf(product) != items.size - 1
            tvName.text = product.name
            tvTime.text = context.getString(R.string.time_label, "20:11, 20/11/2019")
            tvAccount.text = context.getString(R.string.account_label, "+84363513888")

            itemView.setOnClickListener {
                mOnItemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}