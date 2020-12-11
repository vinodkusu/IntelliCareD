package com.themahi.intellicare.ui.registration

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.R
import com.themahi.intellicare.databinding.ActivityRegistarationBinding
import java.util.*

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityRegistarationBinding? = null
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            binding!!.btnGetOTP.isEnabled = isValidForm
        }

        override fun afterTextChanged(s: Editable) {}
    }
    private var receiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getStringExtra("from").equals("login", ignoreCase = true)) finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        try {
            supportActionBar?.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding!!.btnGetOTP.setOnClickListener(this)
        binding!!.etPhoneNumber.addTextChangedListener(textWatcher)
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction("com.themahi.intellicare.ACTION_FINISH")
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver)
            receiver = null
        }
        super.onDestroy()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnGetOTP) {
            moveToVerifyAndLogin(binding!!.etPhoneNumber.text.toString().trim { it <= ' ' })
        }
    }

    private val isValidForm: Boolean
        private get() {
            val phoneNumber = binding!!.etPhoneNumber.text.toString().trim { it <= ' ' }
            return !TextUtils.isEmpty(phoneNumber) && phoneNumber.length == 10
        }

    private fun moveToVerifyAndLogin(phoneNumber: String) {
        val intent = Intent(this@RegistrationActivity, OTPVerificationActivity::class.java)
        intent.putExtra("phone", phoneNumber)
        startActivity(intent)
    }
}