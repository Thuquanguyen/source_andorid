package checkvn.com.viettiepbhdt.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainViewModel
import checkvn.com.viettiepbhdt.utils.hideKeyboard


abstract class BaseFragment : Fragment() {

    abstract var layoutId: Int
    val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    fun setLoading() {
        mainViewModel.isLoading.postValue(true)
    }

    fun hideLoading() {
        mainViewModel.isLoading.postValue(false)
    }

    fun isLoading(): Boolean {
        return mainViewModel.isLoading.value ?: false
    }

    fun hideKeyboard() {
        activity as MainActivity
        activity?.hideKeyboard()
    }

    abstract fun observeData()
}