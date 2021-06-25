package checkvn.com.viettiepbhdt.presentation.ui.productdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseFragment
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.UserRole.WARRANTY_STAFF
import checkvn.com.viettiepbhdt.utils.DateUtils.Companion.DATE_FORMAT_DAY_MONTH_YEAR
import checkvn.com.viettiepbhdt.utils.DateUtils.Companion.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS
import checkvn.com.viettiepbhdt.utils.custom.WarrantyRepairDialog
import checkvn.com.viettiepbhdt.utils.extensions.getInfoValue
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.layout_progress_count_days.*
import org.koin.android.viewmodel.ext.android.viewModel

internal const val TAG_PRODUCT_DETAIL_FRAGMENT = "ProductDetailFragment"
const val BUNDLE_PRODUCT_TYPE = "BUNDLE_PRODUCT_TYPE"
const val BUNDLE_QR_CODE = "BUNDLE_QR_CODE"
const val BUNDLE_SHOW_PROTECT = "BUNDLE_SHOW_PROTECT"

fun newInstance(type: String?, qrCodeContent: String): Fragment = ProductDetailFragment().apply {
    arguments = Bundle(2).apply {
        putString(BUNDLE_PRODUCT_TYPE, type)
        putString(BUNDLE_QR_CODE, qrCodeContent)
    }
}

fun newInstance(type: String?, qrCodeContent: String, isShowProtect: Boolean): Fragment =
    ProductDetailFragment().apply {
        arguments = Bundle(3).apply {
            putString(BUNDLE_PRODUCT_TYPE, type)
            putString(BUNDLE_QR_CODE, qrCodeContent)
            putBoolean(BUNDLE_SHOW_PROTECT, isShowProtect)
        }
    }

class ProductDetailFragment : BaseFragment() {
    val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var mainNavigator: MainNavigator
    private lateinit var imagePagerAdapter: ImagePagerAdapter
    private lateinit var warrantyHistoryAdapter: WarrantyHistoryAdapter
    private var productType: String? = null
    private var qrCodeContent: String? = null
    private var isShowProtect: Boolean = false
    private var listWarrantyRepair: List<RepairCategoryResult> = ArrayList()
    private lateinit var warrantyRepairDialog: WarrantyRepairDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { mainNavigator = MainNavigator(it as MainActivity) }
        arguments?.run {
            productType = getString(BUNDLE_PRODUCT_TYPE)
            qrCodeContent = getString(BUNDLE_QR_CODE)
            isShowProtect = getBoolean(BUNDLE_SHOW_PROTECT, false)
        }
    }

    override var layoutId: Int = R.layout.fragment_product_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        loadData()
    }

    private fun initUI() {
        setupToolbar()
        setupImagePager()
        setupAdapter()
        setListener()
        checkRoleUser()
    }

    private fun checkRoleUser() {
        cl_warranty_history.isVisible = getRoleType() == WARRANTY_STAFF
        ll_add_warranty.isVisible = getRoleType() == WARRANTY_STAFF
    }

    private fun loadData() {
        viewModel.QRCodeContent = qrCodeContent.toString()
        setLoading()
        viewModel.getProductDetail()
        if (getRoleType() == WARRANTY_STAFF) {
            delayer(300) {
                viewModel.getListRepairCategory()
                viewModel.getListWarrantyRepair()
            }
        }
    }

    private fun setupToolbar() {
        productType?.let { toolbar.setToolbarTitle(it) }
    }

    private fun setupImagePager() {
        context?.let { imagePagerAdapter = ImagePagerAdapter(it, ArrayList()) }
        vp_images.adapter = imagePagerAdapter
    }

    private fun setupAdapter() {
        val items = ArrayList<WarrantyRepairResult>()
        val context = requireContext()
        warrantyHistoryAdapter = WarrantyHistoryAdapter(items, context)
        rv_warranty_history.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_warranty_history.adapter = warrantyHistoryAdapter
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
        tab_product.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showLayoutGuarantee()
                    1 -> showLayoutProduct()
                    2 -> showLayoutTrademark()
                }
            }
        })
        vp_images.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                val current = position + 1
                tv_indicator.text = "$current/" + imagePagerAdapter.count
            }
        })
        ll_add_warranty.setOnClickListener { v ->
            showWarrantyRepairDialog(listWarrantyRepair)
        }
    }

    override fun observeData() {
        viewModel.getProductResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleResult(it)
        })
        viewModel.getListRepairCategoryResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleListRepairCategoryResult(it)
        })
        viewModel.getListWarrantyRepairResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleGetListWarrantyRepairResult(it)
        })
        viewModel.addWarrantyRepairResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleAddWarrantyRepairResult(it)
        })
    }

    private fun handleResult(it: Result<ProductResult>) {
        when (it) {
            is Result.Success -> {
                showResult(it.data)
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun handleListRepairCategoryResult(it: Result<ListRepairCategoryResult>) {
        when (it) {
            is Result.Success -> {
                listWarrantyRepair = it.data.data
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun handleGetListWarrantyRepairResult(it: Result<ListWarrantyRepairResult>) {
        when (it) {
            is Result.Success -> {
                if (it.data.data != null) {
                    warrantyHistoryAdapter?.setData(it.data.data)
                }
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun handleAddWarrantyRepairResult(it: Result<CommonResult>) {
        when (it) {
            is Result.Success -> {
                showToast(R.string.add_warranty_repair_success)
                viewModel.getListWarrantyRepair()
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun showResult(product: ProductResult) {
        cl_container.isVisible = true
        llProtect.isVisible = isShowProtect
        context?.let { Glide.with(it).load(R.drawable.logo_thv).into(iv_logo_thv) }
        tv_active_product.text =
            getString(R.string.you_have_activated_protect, product.ProductName.toLowerCase())
        tv_name.text = product.ProductName
        val images = listOf(product.Image)
        tv_indicator.text = "1/${images.size}"
        imagePagerAdapter.setImages(ArrayList(images))

        if (!product.WarrantyStartDate.isNullOrEmpty() && !product.WarrantyEndDate.isNullOrEmpty()) {
            val totalWarrantyDay = getTotalDay(product.WarrantyStartDate, product.WarrantyEndDate)
            val currentActiveDay = getTotalDay(
                product.WarrantyStartDate,
                getCurrentDateTime().toString(DateUtils.DATE_FORMAT_YEAR_MONTH_DAY)
            )
            tv_count_day.text = getString(R.string.count_day, totalWarrantyDay - currentActiveDay)
            pb_count_day.progress = (totalWarrantyDay - currentActiveDay) * 100 / totalWarrantyDay

            tv_warranty_activation_date.text = formatDate(
                product.WarrantyStartDate,
                DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS,
                DATE_FORMAT_DAY_MONTH_YEAR
            )
            tv_warranty_expiry_date.text = formatDate(
                product.WarrantyEndDate,
                DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS,
                DATE_FORMAT_DAY_MONTH_YEAR
            )
        }

        tv_product_code.text = product.Product_ID.toString()
        tv_warranty_period.text = getString(R.string.warranty_month, product.WarrantyMonth)
        tv_product_information.text =
            HtmlCompat.fromHtml(product.ProductContent, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

        itemPhone.setTextInfo(getInfoValue(product.UsedPhone))
        itemAgencyName.setTextInfo(getInfoValue(product.StoreName))
        itemAgencyAddress.setTextInfo(getInfoValue(product.StoreAddress))
        itemActiveAddress.setTextInfo(getInfoValue(product.ActiveAddress))
    }

    private fun showLayoutGuarantee() {
        visibleViews(cl_guarantee)
        goneViews(cl_product, cl_trademark)
    }

    private fun showLayoutProduct() {
        visibleViews(cl_product)
        goneViews(cl_guarantee, cl_trademark)
    }

    private fun showLayoutTrademark() {
        visibleViews(cl_trademark)
        goneViews(cl_product, cl_guarantee)
    }

    private fun showWarrantyRepairDialog(data: List<RepairCategoryResult>) {
        context?.let { context ->
            warrantyRepairDialog = WarrantyRepairDialog(context)
                .showDialog()
                .setItems(data)
                .setOnPositivePressed {
                    var repairCategoryId = it.getRepairCategoryIds()
                    if (!repairCategoryId.isNullOrEmpty()) {
                        var content = it.getInputText()
                        if (content.isNullOrEmpty()) {
                            showToast(getString(R.string.msg_err_enter_repair_content))
                        } else {
                            it.dismiss()
                            viewModel.addWarrantyRepair(content, repairCategoryId.toString())
                        }
                    } else {
                        showToast(getString(R.string.msg_err_choose_repair))
                    }
                }
        }
    }
}
