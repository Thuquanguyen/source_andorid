package checkvn.com.viettiepbhdt.presentation.ui.trademark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.Trademark
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_trademark.*

internal const val TAG_LIST_BRAND_FRAGMENT = "TAG_LIST_BRAND_FRAGMENT"

fun newInstance(): Fragment = ListBrandFragment()

class ListBrandFragment : Fragment() {

    private lateinit var mTrademarkAdapter: TrademarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trademark, container, false)
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
        var trademarks = ArrayList<Trademark>()
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )
        trademarks.add(
            Trademark(
                "KHÓA VIỆT - TIỆP",
                "Thị trấn Đông Anh - Huyện Đông Anh - Hà Nội",
                "024.3883.2442",
                "www.khoaviettiep.com.vn"
            )
        )

        context?.let {
            mTrademarkAdapter = TrademarkAdapter(trademarks, it)
            mTrademarkAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            })
            rv_trademark.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            rv_trademark.adapter = mTrademarkAdapter
        }
    }

    private fun setListener() {
        toolbar.setOnClickBack(View.OnClickListener {
            activity?.onBackPressed()
        })
        toolbar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })
    }
}