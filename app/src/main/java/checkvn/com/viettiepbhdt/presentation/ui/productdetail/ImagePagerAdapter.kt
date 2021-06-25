package checkvn.com.viettiepbhdt.presentation.ui.productdetail

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import checkvn.com.viettiepbhdt.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class ImagePagerAdapter(
    private val context: Context,
    private val images: ArrayList<String>
) : PagerAdapter() {

    fun setImages(images: ArrayList<String>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, null)
        Glide.with(context).load(images[position]).into(view.iv_item)
        container.addView(view, 0)
        return view
    }

    override fun isViewFromObject(view: View, item: Any): Boolean {
        return view == item
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        container.removeView(item as View)
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {

    }

    override fun saveState(): Parcelable? {
        return null
    }
}