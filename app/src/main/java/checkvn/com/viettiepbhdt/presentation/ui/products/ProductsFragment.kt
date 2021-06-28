package checkvn.com.viettiepbhdt.presentation.ui.products

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.ListProductResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseFragment
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.UserRole.AGENCY
import checkvn.com.viettiepbhdt.utils.custom.AwesomeDialog
import checkvn.com.viettiepbhdt.utils.custom.TYPE_SELECTION
import com.google.firebase.auth.FirebaseAuth
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.android.viewmodel.ext.android.viewModel

internal const val TAG_PRODUCTS_FRAGMENT = "ProductsFragment"
const val BUNDLE_PRODUCT_TYPE = "BUNDLE_PRODUCT_TYPE"

fun newInstance(type: String?): Fragment = ProductsFragment().apply {
    arguments = Bundle(1).apply {
        putString(BUNDLE_PRODUCT_TYPE, type)
    }
}

fun newInstance() = ProductsFragment()

class ProductsFragment : BaseFragment() {
    val viewModel: ProductsViewModel by viewModel()
    private lateinit var mainNavigator: MainNavigator
    private lateinit var mProductAdapter: ProductAdapter
    private lateinit var orderByDialog: AwesomeDialog
    private var productType: String? = null
    private var filters: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            mainNavigator = MainNavigator(it as MainActivity)
        }
        arguments?.run {
            productType = getString(BUNDLE_PRODUCT_TYPE)
        }
    }

    override var layoutId: Int = R.layout.fragment_products

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setupUI()
        setupAdapter()
        setListener()
        initFilter()
    }

    override fun observeData() {
        viewModel.getListProductResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            showResult(it)
        })
    }

    private fun showResult(it: Result<ListProductResult>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    it.data.let {
                        cl_empty_product.isVisible = it.data.isEmpty()
                        rv_product.isVisible = it.data.isNotEmpty()
                        mProductAdapter.setData(it.data)
                    }
                } else if (it.data.ErrCode == 99) {
                    // User has been delete from server
                    showToast(it.data.ErrMessage)
                    FirebaseAuth.getInstance().signOut()
                    viewModel.clearData()
                    navigateTo(AuthenticationActivity::class.java)
                }
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun initFilter() {
        filters = getFilters()
        if (!filters.isNullOrEmpty()) {
            tv_filter.text = filters[0]
        }
    }

    private fun getFilters(): ArrayList<String> {
        val filters = ArrayList<String>()
        for (productFilter in enumValues<Constants.OrderBy>()) {
            filters.add(productFilter.title)
        }
        return filters
    }

    private fun setupUI() {
        if (getRoleType() == AGENCY){
            toolbar.setToolbarTitle(getString(R.string.agency_active))
        } else {
            toolbar.setToolbarTitle(getString(R.string.menu_list))
        }
//        productType?.let { toolbar.setToolbarTitle(it) }
        swipe_refresh.setColorSchemeResources(
            R.color.colorRed,
            R.color.colorRed,
            R.color.colorRed
        )
    }

    private fun setupAdapter() {
        context?.let {
            mProductAdapter = ProductAdapter(ArrayList(), it)
            mProductAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    mainNavigator.toProductDetail(
                        productType,
                        mProductAdapter.getData()[position].QRCodeSecretContent,
                        false
                    )
                }
            })
            rv_product.layoutManager = GridLayoutManager(it, 2)
            rv_product.adapter = mProductAdapter
        }

        getListProduct()
    }

    private fun getListProduct() {
        setLoading()
        viewModel.getListProduct()
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

        rl_filter.setOnClickListener {
            showOrderByDialog()
        }

        swipe_refresh.setOnRefreshListener {
            getListProduct()
            swipe_refresh.isRefreshing = false
        }

        tv_active_warranty.setOnClickListener {
            mainNavigator.toActive()
            (activity as MainActivity).requestUpdateLocation()
        }
    }

    private fun showOrderByDialog() {
        if (!::orderByDialog.isInitialized) {
            orderByDialog = AwesomeDialog(requireContext())
                .showDialog()
                .setDialogTitle(R.string.sort_title)
                .setDialogType(TYPE_SELECTION)
                .setItems(filters)
                .setOnItemSelectedListener { position ->
                    tv_filter.text = Constants.OrderBy.values()[position].title
                    viewModel.orderBy = Constants.OrderBy.values()[position].value
                    getListProduct()
                }
        } else {
            orderByDialog.showDialog()
        }
    }
}
