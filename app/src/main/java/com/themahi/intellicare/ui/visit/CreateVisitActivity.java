package com.themahi.intellicare.ui.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.R;
import com.themahi.intellicare.databinding.ActivityCreateVisitBinding;

import java.util.Objects;

public class CreateVisitActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCreateVisitBinding binding;
    private boolean patientPhotoPicked = true;
    private String visitFor;
    private String complaint;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void updateButtonState() {
        binding.btnStartVisit.setEnabled(isValidForm());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateVisitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doSpinnerThing();

        binding.btnStartVisit.setOnClickListener(this);
        binding.etBP.addTextChangedListener(textWatcher);
        binding.etTemperature.addTextChangedListener(textWatcher);
    }

    private void doSpinnerThing() {
        binding.visitFor.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visitFor = ((TextView) view).getText().toString();
                updateButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.complaint.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                complaint = (position != 0) ? ((TextView) view).getText().toString() : "";
                updateButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStartVisit) {
            finish();
        }
    }

    private boolean isValidForm() {
        String bp = binding.etBP.getText().toString().trim();
        String bodyTemp = binding.etTemperature.getText().toString().trim();
        return !TextUtils.isEmpty(bp) &&
                !TextUtils.isEmpty(bodyTemp) &&
                !TextUtils.isEmpty(visitFor) &&
                !TextUtils.isEmpty(complaint) && patientPhotoPicked;
    }
}