package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

abstract class BaseWalkThoughFragment : Fragment(), View.OnClickListener {

    lateinit var viewModel: WalkThoughViewModel
    abstract var layoutId: Int

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
        initViewModel()
        onBackPressListener()
    }

    open fun onBackPressListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.screenIndex.value.let {
                if (it == null || it < 0) {
                    findNavController().popBackStack()
                } else {
                    viewModel.screenIndex.value = it - 1
                }
            }
        }
    }

    private fun initViewModel() {
        parentFragment?.run {
            viewModel = ViewModelProvider(this).get(WalkThoughViewModel::class.java)
        }
    }

    fun showLoading() {
        viewModel.isLoading.postValue(true)
    }

    fun hideLoading() {
        viewModel.isLoading.postValue(false)
    }

    override fun onClick(v: View?) {
        viewModel.isLoading.value?.let {
            if (!it) return
        }
    }

    abstract fun initViewListener()
    abstract fun moveToNextFragment()
}