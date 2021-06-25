package checkvn.com.viettiepbhdt.presentation.ui.trademark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.Trademark
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_trademark.view.*

class TrademarkAdapter(private val items: List<Trademark>, private val context: Context) :
    RecyclerView.Adapter<TrademarkAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trademark, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivTrademark: ImageView = view.iv_trademark
        private val tvName: TextView = view.tv_name
        private val tvAddress: TextView = view.tv_address
        private val tvHotline: TextView = view.tv_hotline
        private val tvWebsite: TextView = view.tv_website
        val divider: View = view.divider

        fun bindData(trademark: Trademark) {
            divider.isVisible = items.indexOf(trademark) != items.size - 1
            tvName.text = trademark.name
            tvAddress.text = context.getString(R.string.trademark_address, trademark.address)
            tvHotline.text =
                context.getString(R.string.trademark_hotline, trademark.hotline)
            tvWebsite.text =
                context.getString(R.string.trademark_website, trademark.website)

            itemView.setOnClickListener {
                mOnItemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}