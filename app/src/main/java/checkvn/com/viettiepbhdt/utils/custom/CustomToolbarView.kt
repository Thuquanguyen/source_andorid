package checkvn.com.viettiepbhdt.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class CustomToolbarView : RelativeLayout {

    companion object {
        const val LOGO = 0
        const val TITLE = 1
        const val SEARCH_EXPAND = 2
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        initView(context)
        initParams(context, attrs)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initView(context)
        initParams(context, attrs)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
    }

    private fun initParams(context: Context, @Nullable attrs: AttributeSet) {
        val attrArr =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolbarView, 0, 0)
        val showIconBack =
            attrArr.getBoolean(R.styleable.CustomToolbarView_ctv_show_icon_back, true)
        val showIconMenu =
            attrArr.getBoolean(R.styleable.CustomToolbarView_ctv_show_icon_menu, true)
        val showIconSearch =
            attrArr.getBoolean(R.styleable.CustomToolbarView_ctv_show_icon_search, true)
        val title = attrArr.getString(R.styleable.CustomToolbarView_ctv_title)
        val searchHint = attrArr.getString(R.styleable.CustomToolbarView_ctv_search_hint)

        when (attrArr.getInt(R.styleable.CustomToolbarView_ctv_type, LOGO)) {
            LOGO -> displayLogo()
            TITLE -> displayTitle()
            SEARCH_EXPAND -> displaySearchExpand()
        }

        if (!title.isNullOrEmpty()) {
            setToolbarTitle(title)
        }
        if (!searchHint.isNullOrEmpty()) {
            setHintSearchView(searchHint)
        }
        showIconBack(showIconBack)
        showIconMenu(showIconMenu)
        showIconSearch(showIconSearch)
    }

    fun setHintSearchView(hintText: String?) {
        et_search.hint = hintText
    }

    fun setToolbarTitle(title: String) {
        tv_title.text = title
    }

    fun showIconSearch(isShown: Boolean) {
        iv_search.isVisible = isShown
    }

    fun showIconMenu(isShown: Boolean) {
        iv_menu.isVisible = isShown
    }

    fun showIconBack(isShown: Boolean) {
        iv_back.isVisible = isShown
    }

    fun displayLogo() {
        rl_logo.visibility = VISIBLE
        iv_search.visibility = VISIBLE
        iv_back.visibility = GONE
        tv_title.visibility = GONE
        rl_search.visibility = GONE
    }

    fun displayTitle() {
        rl_logo.visibility = GONE
        rl_search.visibility = GONE
        iv_search.visibility = GONE
        iv_back.visibility = VISIBLE
        tv_title.visibility = VISIBLE
    }

    fun displaySearchExpand() {
        rl_logo.visibility = GONE
        tv_title.visibility = GONE
        iv_search.visibility = GONE
        iv_back.visibility = VISIBLE
        rl_search.visibility = VISIBLE
    }

    fun setOnClickBack(listener: OnClickListener): CustomToolbarView {
        iv_back.setOnClickListener(listener)
        return this
    }

    fun setOnClickMenu(listener: OnClickListener): CustomToolbarView {
        iv_menu.setOnClickListener(listener)
        return this
    }

}