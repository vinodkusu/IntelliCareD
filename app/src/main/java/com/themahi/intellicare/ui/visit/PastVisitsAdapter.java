package com.themahi.intellicare.ui.visit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themahi.intellicare.databinding.ItemVisitBinding;
import com.themahi.intellicare.model.VisitDetails;

import java.util.List;

public class PastVisitsAdapter extends RecyclerView.Adapter<PastVisitsAdapter.ViewHolder> {
    private final List<VisitDetails> visitDetailsList;
    private ItemVisitBinding binding;

    public PastVisitsAdapter(List<VisitDetails> visitDetailsList) {
        this.visitDetailsList = visitDetailsList;
    }

    @NonNull
    @Override
    public PastVisitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemVisitBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull PastVisitsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        VisitDetails details = visitDetailsList.get(position);
        binding.dateTime.setText(String.format("Visit Time: %s %s", details.getDate(), details.getTime()));
        binding.doctor.setText(String.format("Doctor Name: %s", details.getDoctor()));
        binding.notes.setText(String.format("Notes: Visit was for %s with complaint %s", details.getPatient(), details.getComplaint()));
    }

    @Override
    public int getItemCount() {
        return visitDetailsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}