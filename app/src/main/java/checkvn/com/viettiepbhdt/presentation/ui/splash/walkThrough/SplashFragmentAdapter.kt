package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SplashFragmentAdapter(
    private val listFragment: List<Fragment>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

}