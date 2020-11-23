package com.themahi.intellicare;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

abstract public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showAlertDialog(String title, String message, String firstBtn, String secondBtn, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message)
                .setPositiveButton(firstBtn, (dialog, which) -> {
                    onPositiveClick();
                })
                .setNegativeButton(secondBtn, (dialog, which) -> {
                    onNegativeClick();
                })
                .setCancelable(isCancelable);

        builder.show();
    }

    public void showPickerDialog(String title) {
        CharSequence[] items = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title)
                .setIcon(R.drawable.ic_menu_gallery)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            onPositiveClick(); //camera
                        } else if (which == 1) {
                            onNegativeClick(); //Gallery
                        }
                    }
                });

        builder.show();
    }

    protected void onPositiveClick() {
        Log.i(TAG, "positive click");
    }

    protected void onNegativeClick() {
        Log.i(TAG, "negative click");
    }

}