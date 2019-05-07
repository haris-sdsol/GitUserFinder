package com.seattle.gituserfinder.utils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.seattle.gituserfinder.R


class ImageLoader {

    companion object {

        fun loadCircularImage(url: String, iv: ImageView) {
            Glide.with(iv.context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.profile_icon)
                .placeholder(R.drawable.profile_icon)
                .into(object : BitmapImageViewTarget(iv) {
                    protected override fun setResource(resource: Bitmap) {
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(iv.context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        iv.setImageDrawable(circularBitmapDrawable)
                    }
                })
        }
    }
}