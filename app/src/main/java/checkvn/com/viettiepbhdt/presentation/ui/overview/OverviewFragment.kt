package checkvn.com.viettiepbhdt.presentation.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.OverviewItem
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.utils.Constants.OverviewItemProperties
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_overview.*

fun newInstance() = OverviewFragment()

class OverviewFragment : Fragment() {

    private lateinit var mOverviewAdapter: OverviewAdapter
    private lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { mainNavigator = MainNavigator(it as MainActivity) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setupAdapter()
        setListener()
    }

    private fun setupAdapter() {
        var items = ArrayList<OverviewItem>()
        items.add(
            OverviewItem(
                13,
                getString(R.string.validity),
                getString(R.string.des_in_warranty),
                getString(R.string.add_product),
                OverviewItemProperties.IN_WARRANTY
            )
        )
        items.add(
            OverviewItem(
                5,
                getString(R.string.expiring_soon),
                getString(R.string.des_warranty_expire),
                getString(R.string.warranty_extension),
                OverviewItemProperties.WARRANTY_EXPIRE
            )
        )
        items.add(
            OverviewItem(
                4,
                getString(R.string.has_extended),
                getString(R.string.des_extended_warranty),
                getString(R.string.view_detail),
                OverviewItemProperties.EXTENDED_WARRANTY
            )
        )
        items.add(
            OverviewItem(
                10,
                getString(R.string.saved),
                getString(R.string.des_expired_warranty),
                getString(R.string.save_product),
                OverviewItemProperties.EXPIRED_WARRANTY
            )
        )

        context?.let {
            mOverviewAdapter = OverviewAdapter(items, it)
            mOverviewAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    mainNavigator.toProducts(items[position].type)
                }
            })
            rv_overview.layoutManager = GridLayoutManager(it, 2)
            rv_overview.adapter = mOverviewAdapter
        }
    }

    private fun setListener() {
        toolbar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })


    }
}