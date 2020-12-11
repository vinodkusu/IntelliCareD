package com.themahi.intellicare.ui.visit

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.R
import com.themahi.intellicare.databinding.ActivityCreateVisitBinding
import com.themahi.intellicare.ui.videocall.VideoChatActivity
import java.util.*

class CreateVisitActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityCreateVisitBinding? = null
    private val patientPhotoPicked = true
    private var visitFor: String? = null
    private var complaint: String? = null
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun updateButtonState() {
        binding!!.btnStartVisit.isEnabled = isValidForm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVisitBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        try {
            supportActionBar?.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        doSpinnerThing()
        binding!!.btnStartVisit.setOnClickListener(this)
        binding!!.etBP.addTextChangedListener(textWatcher)
        binding!!.etTemperature.addTextChangedListener(textWatcher)
    }

    private fun doSpinnerThing() {
        binding!!.visitFor.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                visitFor = (view as TextView).text.toString()
                updateButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding!!.complaint.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                complaint = if (position != 0) (view as TextView).text.toString() else ""
                updateButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnStartVisit) {
            val intent = Intent(this@CreateVisitActivity, VideoChatActivity::class.java)
            finish()
        }
    }

    private val isValidForm: Boolean
        private get() {
            val bp = binding!!.etBP.text.toString().trim { it <= ' ' }
            val bodyTemp = binding!!.etTemperature.text.toString().trim { it <= ' ' }
            return !TextUtils.isEmpty(bp) &&
                    !TextUtils.isEmpty(bodyTemp) &&
                    !TextUtils.isEmpty(visitFor) &&
                    !TextUtils.isEmpty(complaint) && patientPhotoPicked
        }
}