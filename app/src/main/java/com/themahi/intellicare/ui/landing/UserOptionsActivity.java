package com.themahi.intellicare.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.themahi.intellicare.R;
import com.themahi.intellicare.Utils;
import com.themahi.intellicare.databinding.ActivityUserOptionsBinding;
import com.themahi.intellicare.ui.registration.InsuranceCardsListActivity;
import com.themahi.intellicare.ui.visit.CreateVisitActivity;
import com.themahi.intellicare.ui.visit.PastVisitsActivity;

import java.util.Objects;

public class UserOptionsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityUserOptionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnCreateVisit.setOnClickListener(this);
        binding.btnPastVisits.setOnClickListener(this);
        binding.btnInsuranceCard.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCreateVisit) {
            moveToCreateVisit();
        } else if (view.getId() == R.id.btnPastVisits) {
            moveToPastVisits();
        } else if (view.getId() == R.id.btnInsuranceCard) {
            moveToInsuranceCardList();
        }
    }

    private void moveToCreateVisit() {
        Intent intent = new Intent(UserOptionsActivity.this, CreateVisitActivity.class);
        startActivity(intent);
    }

    private void moveToPastVisits() {
        Intent intent = new Intent(UserOptionsActivity.this, PastVisitsActivity.class);
        startActivity(intent);
    }

    private void moveToInsuranceCardList() {
        Intent intent = new Intent(UserOptionsActivity.this, InsuranceCardsListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Thread t = new Thread(timer);
        t.start();
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            for(int i=0; i< 10; i++) {
                try {
                    Thread.sleep(1000);
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.toast(finalI +"", UserOptionsActivity.this);
                            binding.btnTimer.setText(finalI +"");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}