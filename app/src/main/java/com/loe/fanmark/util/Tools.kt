package com.loe.fanmark.util

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.loe.fanmark.R
import www.sanju.motiontoast.MotionToast

private const val TAG = "FanMark"

object Tools {

    fun l(message: String) {

        Log.e(TAG, message)

    }

    fun t(message: String) {
        Toast.makeText(Application.getContext(), message, Toast.LENGTH_SHORT).show()
    }


    fun displayMT(activity: Activity, message: String, style: String) {
        MotionToast.createToast(
            activity,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(activity, R.font.ubuntu_bold)
        )
    }

}