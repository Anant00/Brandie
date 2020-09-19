package com.bradie.app.binding

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object BindingAdapters {
    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Picasso.get()
            .load(imageUrl)
            .tag("image")
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(view, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    Picasso.get().load(imageUrl).into(view)
                }

            })
    }

    @BindingAdapter("height")
    @JvmStatic
    fun setLayoutHeight(view: View, layoutParams: LinearLayout.LayoutParams) {
        view.layoutParams = layoutParams
    }
}
