package checkvn.com.viettiepbhdt.presentation.ui.personal


import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.UpdateProfile
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.UserRole.AGENCY
import checkvn.com.viettiepbhdt.utils.DateUtils.Companion.DATE_FORMAT_DAY_MONTH_YEAR
import checkvn.com.viettiepbhdt.utils.custom.AwesomeDialog
import checkvn.com.viettiepbhdt.utils.custom.TYPE_SELECTION
import checkvn.com.viettiepbhdt.utils.extensions.getNotBlankValue
import com.michaldrabik.classicmaterialtimepicker.CmtpDateDialogFragment
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnDatePickedListener
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_personal_data.*
import java.util.*

internal const val TAG_PERSONAL_INFO_FRAGMENT = "TAG_PERSONAL_INFO_FRAGMENT"

class PersonalInformationFragment : BasePersonalFragment() {

    override var layoutId: Int = R.layout.fragment_personal_data
    private var birthDate = ""

    override fun onResume() {
        super.onResume()
        initDataView()
    }

    private fun initDataView() {
        btnFullName.setTextEnd(getNotBlankValue(getFullName()))
//        btnEmail.setTextEnd(getNotBlankValue(getEmail()))
        btnPhone.setTextEnd(getNotBlankValue(getPhone()))
        btnBirth.setTextEnd(getNotBlankValue(getBirth()))
        btnGender.setTextEnd(getNotBlankValue(getGender()))
        llAgencyInfo.isVisible = getRoleType() == AGENCY
        btnAgencyName.setTextEnd(getNotBlankValue(getAgencyName()))
        btnAddress.setTextEnd(getNotBlankValue(getAgencyAddress()))
        loadImage(getAvatarUri(), imgAvatar)
    }

    override fun initToolbar() {
        toolbar = topBar
    }

    private fun handleBirth() {
        var initialDate =
            getDateFromString(getBirth(), DATE_FORMAT_DAY_MONTH_YEAR)
        showDateTimePickerDialog(initialDate)
    }

    private fun showDateTimePickerDialog(initialDate: Date) {
        val dialog = CmtpDateDialogFragment.newInstance(
            getString(R.string.confirm),
            getString(R.string.cancel)
        )
        val calendar = Calendar.getInstance()
        calendar.time = initialDate
        dialog.setInitialDate(
            calendar.get(Calendar.DATE),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
        dialog.setOnDatePickedListener {
            birthDate = "" + it.year + "-" + it.month + "-" + it.day
            var datePicker = "" + it.day + "-" + it.month + "-" + it.year
            btnBirth.setTextEnd(datePicker)
        }
        dialog.show(parentFragmentManager, "DateTimePicker")
    }

    private fun handleGender() {
        val genders = resources.getStringArray(R.array.genders).toCollection(ArrayList())
        AwesomeDialog(requireContext())
            .showDialog()
            .setDialogTitle(R.string.sort_title)
            .setDialogType(TYPE_SELECTION)
            .setItems(genders)
            .setOnItemSelectedListener { position ->
                btnGender.setTextEnd(genders[position])
            }
    }

    private fun toChangePassword() {
        mainNavigator.toUpdatePassword()
    }

    private fun updateProfile() {
        showLoading()

        val userId = Prefs.getString(Constants.USER_ID, "")
        val agencyId = Prefs.getString(Constants.AGENCY_ID, "")
        val phoneNumber = btnPhone.getTextEnd()
        val gender = btnGender.getTextEnd()
        val fullName = btnFullName.getTextEnd()
        val birth = birthDate
//        val email = btnEmail.getTextEnd()

        viewModel.updateProfile(userId, fullName, birth, gender, phoneNumber, agencyId)
            .observe(viewLifecycleOwner, Observer {
                hideLoading()
                handleUpdateResult(it)
            })
    }

    private fun handleUpdateResult(it: Result<UpdateProfile>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    saveData()
                    showToast(R.string.update_profile_success)
                } else {
                    showToast(R.string.have_error_please_try_again)
                }
            }
            else -> showErroNetwork()
        }
    }

    private fun saveData() {
        savePrefs(Constants.FULLNAME, btnFullName.getTextEnd())
//        savePrefs(Constants.EMAIL, btnEmail.getTextEnd())
        savePrefs(Constants.BIRTH, btnBirth.getTextEnd())
        savePrefs(Constants.GENDER, btnGender.getTextEnd())
        if (getRoleType() == AGENCY) {
            setAgencyName(btnAgencyName.getTextEnd())
            setAgencyAddress(btnAddress.getTextEnd())
        }
    }

    override fun initViewListener() {
        btnBirth.setOnClickListener(this)
        btnGender.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnChangePassword.setOnClickListener(this)
    }

    override fun initDataObserver() {

    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            btnBirth.id -> handleBirth()
            btnGender.id -> handleGender()
            btnSave.id -> updateProfile()
            btnChangePassword.id -> toChangePassword()
        }
    }
}
