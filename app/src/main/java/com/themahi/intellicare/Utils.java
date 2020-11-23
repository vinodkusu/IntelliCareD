package com.themahi.intellicare;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class Utils {
    public static void toast(String msg, Context c) {
        if (!TextUtils.isEmpty(msg) && c != null) {
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
