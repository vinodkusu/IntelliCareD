package com.themahi.intellicare.ui.visit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.databinding.ActivityPastVisitsBinding
import com.themahi.intellicare.model.VisitDetails
import java.util.*

class PastVisitsActivity : AppCompatActivity() {
    private var binding: ActivityPastVisitsBinding? = null
    private var adapter: PastVisitsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastVisitsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        adapter = PastVisitsAdapter(createVisits())
        binding!!.recyclerView.adapter = adapter
    }

    private fun createVisits(): List<VisitDetails> {
        val list: MutableList<VisitDetails> = ArrayList()
        val visitDetails1 = VisitDetails("Dr.Patrick", "15-Nov-2020", "05:30PM", "Anthony", "Sample notes for this visit goes here", "Head Ache")
        val visitDetails2 = VisitDetails("Dr.Qatrick", "16-Nov-2020", "06:30PM", "Anthony", "Sample notes for this visit goes here", "Tooth Ache")
        val visitDetails3 = VisitDetails("Dr.Ratrick", "17-Nov-2020", "07:30PM", "Anthony", "Sample notes for this visit goes here", "Stomach Ache")
        list.add(visitDetails1)
        list.add(visitDetails2)
        list.add(visitDetails3)
        list.add(visitDetails2)
        list.add(visitDetails1)
        list.add(visitDetails2)
        list.add(visitDetails1)
        list.add(visitDetails3)
        list.add(visitDetails1)
        return list
    }
}