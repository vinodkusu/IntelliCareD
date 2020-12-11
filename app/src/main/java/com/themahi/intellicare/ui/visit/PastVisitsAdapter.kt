package com.themahi.intellicare.ui.visit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themahi.intellicare.databinding.ItemVisitBinding
import com.themahi.intellicare.model.VisitDetails

class PastVisitsAdapter(private val visitDetailsList: List<VisitDetails>) : RecyclerView.Adapter<PastVisitsAdapter.ViewHolder>() {
    private var binding: ItemVisitBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemVisitBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val (doctor, date, time, patient, _, complaint) = visitDetailsList[position]
        binding!!.dateTime.text = String.format("Visit Time: %s %s", date, time)
        binding!!.doctor.text = String.format("Doctor Name: %s", doctor)
        binding!!.notes.text = String.format("Notes: Visit was for %s with complaint %s", patient, complaint)
    }

    override fun getItemCount(): Int {
        return visitDetailsList.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}