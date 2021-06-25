package checkvn.com.viettiepbhdt.presentation.ui.main

import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.presentation.ui.active.TAG_ACTIVE_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.history.TAG_HISTORY_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.home.TAG_HOME_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.personal.ChangePasswordFragment
import checkvn.com.viettiepbhdt.presentation.ui.personal.PersonalInformationFragment
import checkvn.com.viettiepbhdt.presentation.ui.personal.TAG_PERSONAL_INFO_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.personal.TAG_UPDATE_PASSWORD
import checkvn.com.viettiepbhdt.presentation.ui.productdetail.TAG_PRODUCT_DETAIL_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.products.TAG_PRODUCTS_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.trademark.TAG_LIST_BRAND_FRAGMENT
import checkvn.com.viettiepbhdt.presentation.ui.website.TAG_WEBSITE_FRAGMENT
import checkvn.com.viettiepbhdt.utils.addFragmentBackStack
import checkvn.com.viettiepbhdt.utils.replaceFragmentBackStack
import checkvn.com.viettiepbhdt.utils.replaceFragmentBackStackBottomTop

class MainNavigator(internal val activity: MainActivity) {

    fun toHome() {
        activity.replaceFragmentBackStackBottomTop(
            TAG_HOME_FRAGMENT, R.id.frame_container
        ) {
            checkvn.com.viettiepbhdt.presentation.ui.home.newInstance()
        }
    }

    fun popBackStackToHome() {
        activity.supportFragmentManager.popBackStack(TAG_HOME_FRAGMENT, 0)
    }

    fun toWebsite(url: String) {
        popBackStackToHome()
        activity.addFragmentBackStack(
            TAG_WEBSITE_FRAGMENT, R.id.frame_container
        ) {
            checkvn.com.viettiepbhdt.presentation.ui.website.newInstance(url)
        }
    }

    fun toActive() {
        activity.addFragmentBackStack(
            TAG_ACTIVE_FRAGMENT, R.id.frame_container
        ) {
            checkvn.com.viettiepbhdt.presentation.ui.active.newInstance()
        }
    }

    fun toProducts(type: String? = null) {
        activity.addFragmentBackStack(TAG_PRODUCTS_FRAGMENT, R.id.frame_container) {
            checkvn.com.viettiepbhdt.presentation.ui.products.newInstance(type)
        }
    }

    fun toProductDetail(type: String? = null, id: String, isShowProtect : Boolean) {
        activity.addFragmentBackStack(TAG_PRODUCT_DETAIL_FRAGMENT, R.id.frame_container) {
            checkvn.com.viettiepbhdt.presentation.ui.productdetail.newInstance(type, id, isShowProtect)
        }
    }

    fun toProductActiveDetail(type: String? = null, id: String, isShowProtect : Boolean) {
        activity.addFragmentBackStack(TAG_PRODUCT_DETAIL_FRAGMENT, R.id.frame_container) {
            checkvn.com.viettiepbhdt.presentation.ui.productdetail.newInstance(type, id, isShowProtect)
        }
    }

    fun toPersonalInfo() {
        activity.addFragmentBackStack(TAG_PERSONAL_INFO_FRAGMENT, R.id.frame_container) {
            PersonalInformationFragment()
        }
    }

    fun toUpdatePassword() {
        activity.addFragmentBackStack(TAG_UPDATE_PASSWORD, R.id.frame_container) {
            ChangePasswordFragment()
        }
    }

    fun toHistory() {
        activity.addFragmentBackStack(TAG_HISTORY_FRAGMENT, R.id.frame_container) {
            checkvn.com.viettiepbhdt.presentation.ui.history.newInstance()
        }
    }

    fun toListBrand() {
        activity.addFragmentBackStack(TAG_LIST_BRAND_FRAGMENT, R.id.frame_container) {
            checkvn.com.viettiepbhdt.presentation.ui.trademark.newInstance()
        }
    }

}