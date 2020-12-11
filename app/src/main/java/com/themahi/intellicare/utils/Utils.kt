package com.themahi.intellicare.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

object Utils {
    @JvmStatic
    fun toast(msg: String?, c: Context?) {
        if (!TextUtils.isEmpty(msg) && c != null) {
            Toast.makeText(c, msg, Toast.LENGTH_SHORT).show()
        }
    }
}