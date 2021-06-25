package checkvn.com.viettiepbhdt.presentation.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.data.model.Product
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_history.*

internal const val TAG_HISTORY_FRAGMENT = "HistoryFragment"

fun newInstance(): Fragment = HistoryFragment()

class HistoryFragment : Fragment() {
    private lateinit var mHistoryAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
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
        val products = ArrayList<Product>()
        products.add(Product("Khóa vân tay Việt Tiệp", null))
        products.add(Product("Điều hòa Kungfu", null))
        products.add(Product("Gương xe HIPAS", null))
        products.add(Product("Khóa vân tay Việt Tiệp", null))
        products.add(Product("Điều hòa Kungfu", null))
        products.add(Product("Gương xe HIPAS", null))

        context?.let {
            mHistoryAdapter = HistoryAdapter(products, it)
            mHistoryAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            })
            rv_history.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            rv_history.adapter = mHistoryAdapter
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