package com.themahi.intellicare.ui.form

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.R
import com.themahi.intellicare.utils.Utils.toast
import com.themahi.intellicare.databinding.ActivityUserFormBinding
import com.themahi.intellicare.ui.landing.UserOptionsActivity
import java.util.*

class UserFormActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityUserFormBinding? = null
    private val isMemberIDPicked = true
    private val isMemberPhotoPicked = true
    private var isLegalTermsAccepted = false
    private var isHipaaTermsAccepted = false
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun updateButtonState() {
        binding!!.btnSubmit.isEnabled = isValidForm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFormBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        try {
            supportActionBar?.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding!!.btnSubmit.setOnClickListener(this)
        binding!!.etDOB.setOnClickListener(this)
        binding!!.etMemberId.addTextChangedListener(textWatcher)
        binding!!.etLastName.addTextChangedListener(textWatcher)
        binding!!.etEmail.addTextChangedListener(textWatcher)
        binding!!.etMobile.addTextChangedListener(textWatcher)
        binding!!.etOtherNotes.addTextChangedListener(textWatcher)
        binding!!.etDOB.addTextChangedListener(textWatcher)
        setClickableTexts()
        binding!!.cbLegalTerms.setOnCheckedChangeListener { buttonView, isChecked ->
            isLegalTermsAccepted = isChecked
            updateButtonState()
        }
        binding!!.cbHipaaTerms.setOnCheckedChangeListener { buttonView, isChecked ->
            isHipaaTermsAccepted = isChecked
            updateButtonState()
        }
    }

    private fun setClickableTexts() {
        //https://stackoverflow.com/questions/8184597/how-do-i-make-a-portion-of-a-checkboxs-text-clickable
        val ss = SpannableString(resources.getString(R.string.agree_to_intellicare_legal_terms))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(checkbox: View) {
                // Prevent CheckBox state from being toggled when link is clicked
                checkbox.cancelPendingInputEvents()
                toast("Legal Terms", this@UserFormActivity)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 9, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.cbLegalTerms.text = ss
        binding!!.cbLegalTerms.movementMethod = LinkMovementMethod.getInstance()
        binding!!.cbLegalTerms.highlightColor = Color.TRANSPARENT
        val ss2 = SpannableString(resources.getString(R.string.agree_to_hipaa_terms))
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(checkbox: View) {
                // Prevent CheckBox state from being toggled when link is clicked
                checkbox.cancelPendingInputEvents()
                toast("HIPAA Terms", this@UserFormActivity)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss2.setSpan(clickableSpan2, 9, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.cbHipaaTerms.text = ss2
        binding!!.cbHipaaTerms.movementMethod = LinkMovementMethod.getInstance()
        binding!!.cbHipaaTerms.highlightColor = Color.TRANSPARENT
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnSubmit) {
            validateDetailsAndSubmit()
            finish()
        } else if (view.id == R.id.etDOB) {
            showDatePicker()
        }
    }

    private fun validateDetailsAndSubmit() {
        //move to next screen
        toast("User details submitted successfully", this@UserFormActivity)
        moveToHome()
    }

    private fun moveToHome() {
        val intent = Intent(this@UserFormActivity, UserOptionsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val isValidForm: Boolean
        private get() {
            val memberID = binding!!.etMemberId.text.toString().trim { it <= ' ' }
            val lastName = binding!!.etLastName.text.toString().trim { it <= ' ' }
            val email = binding!!.etEmail.text.toString().trim { it <= ' ' }
            val mobile = binding!!.etMobile.text.toString().trim { it <= ' ' }
            val otherNotes = binding!!.etOtherNotes.text.toString().trim { it <= ' ' }
            val dob = binding!!.etDOB.text.toString().trim { it <= ' ' }
            return (!TextUtils.isEmpty(memberID) && !TextUtils.isEmpty(lastName)
                    && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile)
                    && !TextUtils.isEmpty(otherNotes) && !TextUtils.isEmpty(dob)
                    && isLegalTermsAccepted && isHipaaTermsAccepted
                    && isMemberIDPicked && isMemberPhotoPicked)
        }

    private fun showDatePicker() {
        // Get Current Date
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding!!.etDOB.setText(String.format(Locale.US, "%02d-%02d-%d", monthOfYear + 1, dayOfMonth, year)) }, mYear, mMonth, mDay)
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }
}