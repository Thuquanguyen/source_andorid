package checkvn.com.viettiepbhdt.presentation.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.presentation.ui.main.MainViewModel
import checkvn.com.viettiepbhdt.utils.custom.CustomToolbarView
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BasePersonalFragment : Fragment(), View.OnClickListener {

    abstract var layoutId: Int
    val viewModel: PersonalViewModel by viewModel()
    lateinit var mainNavigator: MainNavigator
    lateinit var toolbar: CustomToolbarView
    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { mainNavigator = MainNavigator(it as MainActivity) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewListener()
        initToolbar()
        initActionForToolbar()
        initDataObserver()
    }

    fun showLoading() {
        mainViewModel.isLoading.postValue(true)
    }

    fun hideLoading() {
        mainViewModel.isLoading.postValue(false)
    }

    fun showErroNetwork() {
        showToast(R.string.no_internet_connection)
    }

    private fun initActionForToolbar() {
        toolbar.iv_back.setOnClickListener(this)
        toolbar.iv_menu.setOnClickListener(this)
    }

    private fun backFragment() {
        requireActivity().onBackPressed()
    }

    private fun showMenuDrawer() {
        (activity as MainActivity)
            .showDrawer()
    }

    abstract fun initToolbar()

    abstract fun initViewListener()

    abstract fun initDataObserver()

    override fun onClick(view: View?) {
        if (viewModel.isLoading.value == true)
            return
        when (view?.id) {
            iv_back.id -> backFragment()
            iv_menu.id -> showMenuDrawer()
        }
    }
}