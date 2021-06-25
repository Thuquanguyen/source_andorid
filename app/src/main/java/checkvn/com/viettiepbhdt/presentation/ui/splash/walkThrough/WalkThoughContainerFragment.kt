package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.animations.DepthPageTransformer
import checkvn.com.viettiepbhdt.utils.navigateTo
import checkvn.com.viettiepbhdt.utils.setUsed
import checkvn.com.viettiepbhdt.utils.ui
import kotlinx.android.synthetic.main.fragment_splash_fragment_container.*
import kotlinx.android.synthetic.main.splash_header.*

class WalkThoughContainerFragment : Fragment() {

    private lateinit var walkThroughFragments: ArrayList<Fragment>
    private lateinit var fragmentPagerAdapter: SplashFragmentAdapter
    private lateinit var walkThoughViewModel: WalkThoughViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewListener()
        initViewModel()
        initObserver()
    }

    private fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        walkThroughFragments = ArrayList()
        walkThroughFragments.add(WalkThroughFirstFragment())
        walkThroughFragments.add(WalkThoughSecondFragment())
        walkThroughFragments.add(WalkThoughThirdFragment())
        walkThroughFragments.add(WalkThoughFourthFragment())
        walkThroughFragments.add(WalkThoughInfoFragment())
        walkThroughFragments.add(WalkThoughFifthFragment())

        fragmentPagerAdapter = SplashFragmentAdapter(walkThroughFragments, this)
        fragmentContainer.adapter = fragmentPagerAdapter
        fragmentContainer.isUserInputEnabled = false
        fragmentContainer.setPageTransformer(DepthPageTransformer())
    }

    private fun initViewListener() {
        tvSkip.setOnClickListener {
            setUsed()
            navigateTo(AuthenticationActivity::class.java)
        }
    }

    private fun initViewModel() {
        walkThoughViewModel = ViewModelProvider(this).get(WalkThoughViewModel::class.java)
    }

    private fun initObserver() {
        walkThoughViewModel.screenIndex.observe(viewLifecycleOwner, Observer {
            moveFragment(it)
        })

        walkThoughViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            setLoading(it)
        })
    }

    private fun moveFragment(position: Int) {
        if (position < walkThroughFragments.size - 1)
            fragmentContainer.currentItem = position + 1
        else {
            moveToHomeActivity()
        }
    }

    private fun moveToHomeActivity() {
        activity?.run {
            ui {
                setLoading(true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        loadingView.isVisible = isLoading
        transparentView.isVisible = isLoading
    }
}
