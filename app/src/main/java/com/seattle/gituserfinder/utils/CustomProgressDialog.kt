package com.seattle.gituserfinder.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.seattle.gituserfinder.R

/**
 * @author Haris Bhatti
 * @see CustomProgressDialog       this class is used to show the progress dialog
 * @param context   context to that class from which it is initiated.
 */
class CustomProgressDialog(context: Context?) : Dialog(context!!) {
    val mBinding: com.seattle.gituserfinder.databinding.DialogProgressBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 1000
        rotate.fillAfter = true
        rotate.repeatCount = Animation.INFINITE
        mBinding?.loaderIv?.startAnimation(rotate)
    }

}