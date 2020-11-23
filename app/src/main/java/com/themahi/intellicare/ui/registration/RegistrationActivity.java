package com.themahi.intellicare.ui.registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.R;
import com.themahi.intellicare.databinding.ActivityRegistarationBinding;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRegistarationBinding binding;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.btnGetOTP.setEnabled(isValidForm());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("from").equalsIgnoreCase("login"))
                finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistarationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnGetOTP.setOnClickListener(this);
        binding.etPhoneNumber.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.themahi.intellicare.ACTION_FINISH");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGetOTP) {
            moveToVerifyAndLogin(binding.etPhoneNumber.getText().toString().trim());
        }
    }

    private boolean isValidForm() {
        String phoneNumber = binding.etPhoneNumber.getText().toString().trim();
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() == 10;
    }

    private void moveToVerifyAndLogin(String phoneNumber) {
        Intent intent = new Intent(RegistrationActivity.this, OTPVerificationActivity.class);
        intent.putExtra("phone", phoneNumber);
        startActivity(intent);
    }
}