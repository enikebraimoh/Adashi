package com.enike.weatherapp.core

import android.media.Rating
import android.util.TypedValue
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ng.adashi.models.Transactions
import org.w3c.dom.Text
@BindingAdapter("amount")
fun Amount(tv: TextView, data: Transactions) {
    tv.text = data.ammount.toString()
}
