package checkvn.com.viettiepbhdt.presentation.ui.personal

import checkvn.com.viettiepbhdt.R
import kotlinx.android.synthetic.main.fragment_personal.*

class PersonalContainerFragment : BasePersonalFragment() {

    override var layoutId: Int = R.layout.fragment_personal_container

    override fun initToolbar() {
        toolbar = topBar
    }

    override fun initViewListener() {

    }

    override fun initDataObserver() {

    }
}
