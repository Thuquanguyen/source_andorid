package checkvn.com.viettiepbhdt.presentation.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.NotificationResult
import checkvn.com.viettiepbhdt.utils.DateUtils
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import checkvn.com.viettiepbhdt.utils.formatDate
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(
    private var items: List<NotificationResult>,
    private val context: Context
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setData(items: List<NotificationResult>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getData(): List<NotificationResult> {
        return items;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.divider.isVisible = position != 0
        holder.tvType.text = context.getString(R.string.active_warranty)
        holder.tvDate.text = formatDate(
            items[position].data.CreateDate,
            DateUtils.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND,
            DateUtils.DATE_FORMAT_DAY_MONTH_YEAR
        )
        holder.tvContent.text = items[position].notification.title
        holder.ivMore.setOnClickListener {
            mOnItemClickListener.onItemClick(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val divider: View = view.divider
        val ivType: ImageView = view.iv_type
        val ivMore: ImageView = view.iv_more
        val tvType: TextView = view.tv_type
        val tvDate: TextView = view.tv_date
        val tvContent: TextView = view.tv_content
    }
}