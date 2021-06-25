package checkvn.com.viettiepbhdt.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.base.BasePagerAdapter
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.personal.PersonalFragment
import kotlinx.android.synthetic.main.fragment_home.*

internal const val TAG_HOME_FRAGMENT = "HomeFragment"

fun newInstance(): Fragment = HomeFragment()

class HomeFragment : Fragment() {

    private lateinit var pagerAdapter: BasePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setupViewPager()
        setListener()
    }

    private fun setupViewPager() {
        activity?.let {
            val fragments = ArrayList<Fragment>()

//            fragments.add(checkvn.com.viettiepbhdt.presentation.ui.overview.newInstance())
            fragments.add(checkvn.com.viettiepbhdt.presentation.ui.products.newInstance())
            fragments.add(checkvn.com.viettiepbhdt.presentation.ui.website.newInstance())
            fragments.add(checkvn.com.viettiepbhdt.presentation.ui.notification.newInstance())
            fragments.add(checkvn.com.viettiepbhdt.presentation.ui.personal.newInstance())

            pagerAdapter = BasePagerAdapter(childFragmentManager, fragments, null)
            vp_home.adapter = pagerAdapter
            vp_home.offscreenPageLimit = fragments.size
        }
    }

    private fun setListener() {
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                (activity as MainActivity).apply {
                    setSelectedNavigation(position)
                }
                if (pagerAdapter != null) {
                    val fragment = pagerAdapter.getItem(position)
                    if (fragment is PersonalFragment) {
                        fragment.getProfile()
                        fragment.initData()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    fun setCurrentItemViewPager(position: Int) {
        vp_home.setCurrentItem(position, true)
    }
}
