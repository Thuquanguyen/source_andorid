package checkvn.com.viettiepbhdt.utils.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.IntegerCallBack
import kotlinx.android.synthetic.main.item_custom_selection.view.*

class CustomSelectionAdapter :
    RecyclerView.Adapter<CustomSelectionAdapter.ViewHolder>() {

    private val items: ArrayList<String> = ArrayList()
    private lateinit var callback: IntegerCallBack
    private var index = 0

    fun setOnItemClickListener(callback: IntegerCallBack) {
        this.callback = callback
    }

    fun setItems(items: ArrayList<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setSelection(position: Int) {
        if (items.size > position) {
            index = position
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_custom_selection, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ctvItem.text = items[position]
        holder.ctvItem.isChecked = index == position
        holder.ctvItem.isSelected = index == position

        holder.ctvItem.setOnClickListener {
            callback(position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ctvItem: CheckedTextView = view.ctv_item
    }
}