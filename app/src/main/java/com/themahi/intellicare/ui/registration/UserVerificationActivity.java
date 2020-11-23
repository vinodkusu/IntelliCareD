package com.themahi.intellicare.ui.registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;

import com.themahi.intellicare.BaseActivity;
import com.themahi.intellicare.MainActivity;
import com.themahi.intellicare.R;
import com.themahi.intellicare.databinding.ActivityUserVerificationBinding;
import com.themahi.intellicare.ui.form.UserFormActivity;
import com.themahi.intellicare.ui.landing.UserOptionsActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UserVerificationActivity extends BaseActivity implements View.OnClickListener {
    private ActivityUserVerificationBinding binding;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnSubmit.setEnabled(isValidForm());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.btnSubmit.setOnClickListener(this);
        binding.etDOB.setOnClickListener(this);
        binding.etDOB.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            validateDetailsAndSubmit();
        } else if (view.getId() == R.id.etDOB) {
            showDatePicker();
        }
    }

    private void validateDetailsAndSubmit() {
        String dob = binding.etDOB.getText().toString().trim();
        String lastName = binding.etLastName.getText().toString().trim();
        if(lastName.equalsIgnoreCase("unknown") || lastName.equalsIgnoreCase("test")) {
            showAlertDialog("Warning", "Sorry! We couldn't fetch details. Please create an account or enter correct credentials", "Create Account", "Cancel", true);
        } else {
            //move to next screen
            moveToHome(dob, lastName);
        }
    }

    private void moveToHome(String dob, String lastName) {
        Intent intent = new Intent(UserVerificationActivity.this, UserOptionsActivity.class);
        intent.putExtra("dob", dob);
        intent.putExtra("last", lastName);
        startActivity(intent);
        finish();
    }

    private void moveToUserForm() {
        Intent intent = new Intent(UserVerificationActivity.this, UserFormActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValidForm() {
        String dob = binding.etDOB.getText().toString().trim();
        String lastName = binding.etLastName.getText().toString().trim();
        return !TextUtils.isEmpty(dob) && !TextUtils.isEmpty(lastName);
    }


    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.etDOB.setText(String.format(Locale.US, "%02d-%02d-%d", monthOfYear + 1, dayOfMonth, year));
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    protected void onPositiveClick() {
        moveToUserForm();
    }

    @Override
    protected void onNegativeClick() {

    }
}