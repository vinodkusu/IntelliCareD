package com.themahi.intellicare.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.R;
import com.themahi.intellicare.utils.Utils;
import com.themahi.intellicare.databinding.ActivityOtpVerificationBinding;

public class OTPVerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOtpVerificationBinding binding;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnVerifyOTP.setEnabled(isValidForm());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        phoneNumber = getIntent().getStringExtra("phone");
        binding.tvPhoneNumber.setText(phoneNumber);
        binding.btnVerifyOTP.setOnClickListener(this);
        binding.tvResendOTP.setOnClickListener(this);
        binding.tvChangePhoneNumber.setOnClickListener(this);
        binding.etOTP.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnVerifyOTP) {
            verifyOTP();
        } else if (view.getId() == R.id.tvResendOTP) {
            //service call
            resendOTP();
        } else if (view.getId() == R.id.tvChangePhoneNumber) {
            finish();
        }
    }

    private void resendOTP() {
        try {
            binding.etOTP.getText().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        otpToast();
        updateOTPTitleUI();
    }

    private void updateOTPTitleUI() {
        binding.otpTitle.setText("OTP has been successfully re-sent to");
        binding.otpTitle.setTextColor(getResources().getColor(R.color.shape_green));
    }

    private void otpToast() {
        Utils.toast("OTP sent successfully to " + phoneNumber, OTPVerificationActivity.this);
    }

    private void verifyOTP() {
        //call service
        //success: move to next screen
        moveToVerifyAndDetailsScreen();

        //failure: show error toast
        //Utils.toast("OTP is not valid", LoginActivity.this);
    }

    private boolean isValidForm() {
        String otp = binding.etOTP.getText().toString().trim();
        return !TextUtils.isEmpty(otp) && otp.length() == 5;
    }

    private void moveToVerifyAndDetailsScreen() {
        //send broadcast
        sendBroadcast(new Intent("com.themahi.intellicare.ACTION_FINISH").putExtra("from", "login"));
        Intent intent = new Intent(OTPVerificationActivity.this, UserVerificationActivity.class);
        intent.putExtra("phone", phoneNumber);
        startActivity(intent);
        finish();
    }
}