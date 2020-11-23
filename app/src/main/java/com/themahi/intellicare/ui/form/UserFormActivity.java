package com.themahi.intellicare.ui.form;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.MainActivity;
import com.themahi.intellicare.R;
import com.themahi.intellicare.Utils;
import com.themahi.intellicare.databinding.ActivityUserFormBinding;
import com.themahi.intellicare.ui.landing.UserOptionsActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UserFormActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUserFormBinding binding;
    private boolean isMemberIDPicked = true;
    private boolean isMemberPhotoPicked = true;
    private boolean isLegalTermsAccepted = false;
    private boolean isHipaaTermsAccepted = false;
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
        binding.btnSubmit.setEnabled(isValidForm());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.btnSubmit.setOnClickListener(this);
        binding.etDOB.setOnClickListener(this);
        binding.etMemberId.addTextChangedListener(textWatcher);
        binding.etLastName.addTextChangedListener(textWatcher);
        binding.etEmail.addTextChangedListener(textWatcher);
        binding.etMobile.addTextChangedListener(textWatcher);
        binding.etOtherNotes.addTextChangedListener(textWatcher);
        binding.etDOB.addTextChangedListener(textWatcher);

        setClickableTexts();

        binding.cbLegalTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLegalTermsAccepted = isChecked;
                updateButtonState();
            }
        });
        binding.cbHipaaTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHipaaTermsAccepted = isChecked;
                updateButtonState();
            }
        });
    }

    private void setClickableTexts() {
        //https://stackoverflow.com/questions/8184597/how-do-i-make-a-portion-of-a-checkboxs-text-clickable
        SpannableString ss = new SpannableString(getResources().getString(R.string.agree_to_intellicare_legal_terms));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View checkbox) {
                // Prevent CheckBox state from being toggled when link is clicked
                checkbox.cancelPendingInputEvents();
                Utils.toast("Legal Terms", UserFormActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan, 9, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.cbLegalTerms.setText(ss);
        binding.cbLegalTerms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.cbLegalTerms.setHighlightColor(Color.TRANSPARENT);

        SpannableString ss2 = new SpannableString(getResources().getString(R.string.agree_to_hipaa_terms));
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View checkbox) {
                // Prevent CheckBox state from being toggled when link is clicked
                checkbox.cancelPendingInputEvents();
                Utils.toast("HIPAA Terms", UserFormActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss2.setSpan(clickableSpan2, 9, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.cbHipaaTerms.setText(ss2);
        binding.cbHipaaTerms.setMovementMethod(LinkMovementMethod.getInstance());
        binding.cbHipaaTerms.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            validateDetailsAndSubmit();
            finish();
        } else if (view.getId() == R.id.etDOB) {
            showDatePicker();
        }
    }

    private void validateDetailsAndSubmit() {
        //move to next screen
        Utils.toast("User details submitted successfully", UserFormActivity.this);
        moveToHome();
    }

    private void moveToHome() {
        Intent intent = new Intent(UserFormActivity.this, UserOptionsActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValidForm() {
        String memberID = binding.etMemberId.getText().toString().trim();
        String lastName = binding.etLastName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String mobile = binding.etMobile.getText().toString().trim();
        String otherNotes = binding.etOtherNotes.getText().toString().trim();
        String dob = binding.etDOB.getText().toString().trim();

        return !TextUtils.isEmpty(memberID) && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile)
                && !TextUtils.isEmpty(otherNotes) && !TextUtils.isEmpty(dob)
                && isLegalTermsAccepted && isHipaaTermsAccepted
                && isMemberIDPicked && isMemberPhotoPicked
                ;
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

}