package checkvn.com.viettiepbhdt.presentation.ui.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.ProductResult
import checkvn.com.viettiepbhdt.utils.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private var items: List<ProductResult>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var recyclerView: RecyclerView? = null
    private var isShowDay = true

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun setData(items: List<ProductResult>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getData(): List<ProductResult> {
        return items;
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        items[position].let { product ->
            viewHolder.bind(context, product)
        }
        viewHolder.itemView.setOnClickListener {
            mOnItemClickListener.onItemClick(position)
        }
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivProduct: ImageView = view.iv_product
        private val tvName: TextView = view.tv_name
        private val pbCountDay: ProgressBar = view.pb_count_day
        private val tvCountDay: TextView = view.tv_count_day

        fun bind(context: Context, product: ProductResult) {
            tvName.text = product.ProductName
            Glide.with(context).load(product.Image).into(ivProduct)

            val totalWarrantyDay = getTotalDay(product.WarrantyStartDate, product.WarrantyEndDate)
            val currentActiveDay = getTotalDay(
                product.WarrantyStartDate,
                getCurrentDateTime().toString(DateUtils.DATE_FORMAT_YEAR_MONTH_DAY)
            )
            tvCountDay.text =
                context.getString(R.string.count_day, totalWarrantyDay - currentActiveDay)
            pbCountDay.progress = (totalWarrantyDay - currentActiveDay) * 100 / totalWarrantyDay
        }
    }
}
