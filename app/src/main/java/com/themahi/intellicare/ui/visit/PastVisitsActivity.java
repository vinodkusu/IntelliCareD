package com.themahi.intellicare.ui.visit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.databinding.ActivityPastVisitsBinding;
import com.themahi.intellicare.model.VisitDetails;

import java.util.ArrayList;
import java.util.List;

public class PastVisitsActivity extends AppCompatActivity {
    private ActivityPastVisitsBinding binding;
    private PastVisitsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPastVisitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new PastVisitsAdapter(createVisits());
        binding.recyclerView.setAdapter(adapter);
    }

    private List<VisitDetails> createVisits() {
        List<VisitDetails> list = new ArrayList<>();
        VisitDetails visitDetails1 = new VisitDetails("Dr.Patrick", "15-Nov-2020", "05:30PM", "Anthony", "Sample notes for this visit goes here", "Head Ache");
        VisitDetails visitDetails2 = new VisitDetails("Dr.Qatrick", "16-Nov-2020", "06:30PM", "Anthony", "Sample notes for this visit goes here", "Tooth Ache");
        VisitDetails visitDetails3 = new VisitDetails("Dr.Ratrick", "17-Nov-2020", "07:30PM", "Anthony", "Sample notes for this visit goes here", "Stomach Ache");
        list.add(visitDetails1);
        list.add(visitDetails2);
        list.add(visitDetails3);
        list.add(visitDetails2);
        list.add(visitDetails1);
        list.add(visitDetails2);
        list.add(visitDetails1);
        list.add(visitDetails3);
        list.add(visitDetails1);
        return list;
    }
}