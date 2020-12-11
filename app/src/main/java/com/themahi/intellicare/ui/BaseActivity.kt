package com.themahi.intellicare.ui

import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.themahi.intellicare.R

abstract class BaseActivity : AppCompatActivity() {

    fun showAlertDialog(title: String?, message: String?, firstBtn: String?, secondBtn: String?, isCancelable: Boolean) {
        val builder = AlertDialog.Builder(this).setTitle(title)
                .setMessage(message)
                .setPositiveButton(firstBtn) { _: DialogInterface?, _: Int -> onPositiveClick() }
                .setNegativeButton(secondBtn) { _: DialogInterface?, _: Int -> onNegativeClick() }
                .setCancelable(isCancelable)
        builder.show()
    }

    fun showPickerDialog(title: String?) {
        val items = arrayOf<CharSequence>("Camera", "Gallery")
        val builder = AlertDialog.Builder(this).setTitle(title)
                .setIcon(R.drawable.ic_menu_gallery)
                .setItems(items) { _, which ->
                    if (which == 0) {
                        onPositiveClick() //camera
                    } else if (which == 1) {
                        onNegativeClick() //Gallery
                    }
                }
        builder.show()
    }

    protected open fun onPositiveClick() {
        Log.i(TAG, "positive click")
    }

    protected open fun onNegativeClick() {
        Log.i(TAG, "negative click")
    }

    companion object {
        private const val TAG = "Base"
    }
}