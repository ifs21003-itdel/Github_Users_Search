package com.example.githubuserssearch.util

import android.app.Activity
import android.app.AlertDialog
import com.example.githubuserssearch.R

class LoadingDialog(val mActivity : Activity) {
    private  lateinit var isdialog: AlertDialog

    fun startLoading(){
        val infalter =  mActivity.layoutInflater
        val dialogview = infalter.inflate(R.layout.loading_item,null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogview)
        builder.setCancelable(false)
        isdialog = builder.create()
        isdialog.show()
    }

    fun isDismiss(){
        isdialog.dismiss()
    }
}