package com.enike.weatherapp.core

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ng.adashi.domain_models.Transactions

@BindingAdapter("amount")
fun Amount(tv: TextView, data: Transactions) {
    tv.text = data.ammount.toString()
}
