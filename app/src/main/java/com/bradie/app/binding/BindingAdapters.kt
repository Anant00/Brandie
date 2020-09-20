package com.bradie.app.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bradie.app.R
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
            Picasso.get()
                .load(imageUrl)
                .tag("image")
                .placeholder(R.drawable.imgbg)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(view, object : Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(imageUrl).placeholder(R.drawable.imgbg).into(view)
                    }
                })
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
