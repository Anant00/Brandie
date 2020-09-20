package com.bradie.app.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bradie.app.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

object BindingAdapters {
    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .override(Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(view)
        }
    }

    @BindingAdapter("height")
    @JvmStatic
    fun setLayoutHeight(view: View, layoutParams: LinearLayout.LayoutParams) {
        view.layoutParams = layoutParams
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("count")
    @JvmStatic
    fun covertToK(view: View, count: Int) {
        val value = if (count > 999) {
            (count / 1000).toFloat().roundToInt().toString()
        } else {
            count.toString()
        }
        if (view is TextView) {
            view.text = value + "K"
        }
    }
}
