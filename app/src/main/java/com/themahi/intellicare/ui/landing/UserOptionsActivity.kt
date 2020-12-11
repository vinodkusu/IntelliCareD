package com.themahi.intellicare.ui.landing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.R
import com.themahi.intellicare.databinding.ActivityUserOptionsBinding
import com.themahi.intellicare.ui.registration.InsuranceCardsListActivity
import com.themahi.intellicare.ui.visit.CreateVisitActivity
import com.themahi.intellicare.ui.visit.PastVisitsActivity
import com.themahi.intellicare.utils.Utils.toast
import java.util.*

class UserOptionsActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityUserOptionsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOptionsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        try {
            supportActionBar?.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding!!.btnCreateVisit.setOnClickListener(this)
        binding!!.btnPastVisits.setOnClickListener(this)
        binding!!.btnInsuranceCard.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnCreateVisit) {
            moveToCreateVisit()
        } else if (view.id == R.id.btnPastVisits) {
            moveToPastVisits()
        } else if (view.id == R.id.btnInsuranceCard) {
            moveToInsuranceCardList()
        }
    }

    private fun moveToCreateVisit() {
        val intent = Intent(this@UserOptionsActivity, CreateVisitActivity::class.java)
        startActivity(intent)
    }

    private fun moveToPastVisits() {
        val intent = Intent(this@UserOptionsActivity, PastVisitsActivity::class.java)
        startActivity(intent)
    }

    private fun moveToInsuranceCardList() {
        val intent = Intent(this@UserOptionsActivity, InsuranceCardsListActivity::class.java)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        val t = Thread(timer)
        t.start()
    }

    private val timer = Runnable {
        for (i in 0..9) {
            try {
                Thread.sleep(1000)
                runOnUiThread {
                    toast(i.toString() + "", this@UserOptionsActivity)
                    binding!!.btnTimer.text = i.toString() + ""
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}