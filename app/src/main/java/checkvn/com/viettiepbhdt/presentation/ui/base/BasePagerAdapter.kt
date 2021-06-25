package checkvn.com.viettiepbhdt.presentation.ui.base

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BasePagerAdapter(
    fm: FragmentManager,
    listFragments: List<Fragment>?,
    titles: List<String>?
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mListFragment = ArrayList<Fragment>()
    private val mTitles = ArrayList<String>()

    init {
        if (!listFragments.isNullOrEmpty()) mListFragment.addAll(listFragments)
        if (!titles.isNullOrEmpty()) mTitles.addAll(titles)
    }

    override fun getItem(i: Int): Fragment {
        return mListFragment[i]
    }

    override fun getCount(): Int {
        return mListFragment.size
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        if (position < mTitles.size)
            return mTitles[position]
        return super.getPageTitle(position)
    }
}