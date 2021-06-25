package checkvn.com.viettiepbhdt.presentation.ui.notification

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.ListNotificationResult
import checkvn.com.viettiepbhdt.domain.entities.NotificationResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseFragment
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.utils.OnItemClickListener
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.fragment_notification.*
import org.koin.android.viewmodel.ext.android.viewModel

fun newInstance() = NotificationFragment()

class NotificationFragment : BaseFragment() {
    val viewModel: NotificationViewModel by viewModel()
    private lateinit var mainNavigator: MainNavigator
    private lateinit var guaranteeAdapter: NotificationAdapter

    override var layoutId = R.layout.fragment_notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { mainNavigator = MainNavigator(it as MainActivity) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setupAdapter()
        setListener()
    }

    override fun observeData() {
        viewModel.getListNotificationResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleResult(it)
        })
    }

    private fun handleResult(it: Result<ListNotificationResult>) {
        when (it) {
            is Result.Success -> {
                showResult(it.data.data)
            }
            is Result.Fail -> {
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun showResult(data: List<NotificationResult>) {
        if (data.isNullOrEmpty()) {
            cl_empty_notification.visibility = View.VISIBLE
            rv_guarantee.visibility = View.GONE
        } else {
            cl_empty_notification.visibility = View.GONE
            rv_guarantee.visibility = View.VISIBLE
            this.guaranteeAdapter.setData(data)
        }
    }

    private fun setupAdapter() {
        context?.let {
            guaranteeAdapter = NotificationAdapter(ArrayList(), it)
            guaranteeAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    showToast("Click more!")
                }
            })
            rv_guarantee.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            rv_guarantee.adapter = guaranteeAdapter

            viewModel.getListNotification()
        }
    }

    private fun setListener() {
        toolbar.setOnClickBack(View.OnClickListener {
            // Navigation to overview
            (activity as MainActivity).apply {
                setSelectedNavigation(0)
            }
        })
        toolbar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })
        tv_active_warranty.setOnClickListener {
            mainNavigator.toActive()
            (activity as MainActivity).requestUpdateLocation()
        }
    }
}
