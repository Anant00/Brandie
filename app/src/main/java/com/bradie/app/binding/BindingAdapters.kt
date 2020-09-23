package com.bradie.app.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import kotlin.math.roundToInt

/**
 * A class that has custom adapter to be used by data binding.
 * Using these adapters with data-binding avoids boilerplate code.
 * @see {https://developer.android.com/topic/libraries/data-binding}
 */
object BindingAdapters {
    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Adapter used by ImageView.
     * The @param view and @param imageUrl are sent via xml
     * with app:ImageUrl=@{item.Url}. The url is the object of the data model
     * that is being used in the xml and view is passed itself, without mentioning it
     * explicitly.
     */
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

    /**
     * A method to convert the number of likes, views, and shares to the nearest 1000 int
     * value.
     */
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
