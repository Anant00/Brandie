package com.bradie.app.view.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bradie.app.R
import com.bradie.app.apiservice.Hit
import com.bradie.app.databinding.ActivityImageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details)
        getData()
    }

    /**
     * Get the data sent from the previous fragment.
     * The model `HIT` must be annotated by @Parcelise and extended with Parcelable.
     * These would make the model parcelable and that could be sent via `putExtra()` from the
     * previous activity or fragment.
     */
    private fun getData() {
        val data = intent.getParcelableExtra<Hit>("data")

        /**
         * the varible `item` is in the activity_image_details layout.
         * Set this varbiable to data so that it'd render the info.
         */
        binding.item = data
    }
}
