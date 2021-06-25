package checkvn.com.viettiepbhdt.presentation.ui.overview

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.OverviewItem
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_overview.view.*

class OverviewAdapter(private val items: List<OverviewItem>, private val context: Context) :
    RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_overview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivIcon.setImageResource(items[position].properties.icon)
        holder.tvCount.text = items[position].count.toString()
        holder.tvDescription.text = items[position].description
        holder.tvAction.text = items[position].action

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.tvCount.setTextColor(context.getColor(items[position].properties.textColor))
            holder.tvAction.setTextColor(context.getColor(items[position].properties.textColor))
            holder.tvAction.setBackgroundColor(context.getColor(items[position].properties.backgroundColor))
        }

        holder.cvContainer.setOnClickListener {
            mOnItemClickListener.onItemClick(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvContainer: CardView = view.cv_container
        val ivIcon: ImageView = view.iv_icon
        val tvCount: TextView = view.tv_count
        val tvDescription: TextView = view.tv_description
        val tvAction: TextView = view.tv_action
    }
}