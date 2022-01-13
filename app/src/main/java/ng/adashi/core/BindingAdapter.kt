package ng.adashi.core

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import ng.adashi.ui.home.models.transactions.Transaction
import ng.adashi.ui.savers.models.Wallet
import ng.adashi.utils.convertStringToDate
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("verify_field")
fun verifyField(field: TextInputLayout, error: String?) {
    if (error != null) {
        field.isErrorEnabled = true
        field.error = error
    } else {
        field.isErrorEnabled = false
    }
}

@BindingAdapter("format_date_time")
fun formatDateTime(view: TextView, date: String) {
    val formatedDate = convertStringToDate(date)
    val formatedTime = formatedDate.format(DateTimeFormatter.ofPattern("hh:mm a"))

    view.text = "${formatedDate.dayOfWeek}, $formatedTime"

}


@BindingAdapter("currency_converter")
fun currencyConverter(tv: TextView, data: Any) {
    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
    newformat.setMaximumFractionDigits(0)
    newformat.setCurrency(Currency.getInstance("NGN"))
    val bal = data
    tv.text = newformat.format(bal)

}

@BindingAdapter("get_saver_balance")
fun getSaverBalance(tv: TextView, data: Wallet) {
    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
    newformat.setMaximumFractionDigits(0)
    newformat.setCurrency(Currency.getInstance("NGN"))
    val bal = data.balance
    tv.text = newformat.format(bal)
}

@BindingAdapter("azza")
fun Azza(tv: TextView, data: List<String>) {
    tv.text = data.toString().replace("]","").replace("[","")
}

@BindingAdapter("long_to_string")
fun longToString(tv: TextView, data: Long) {
    tv.text = data.toString()
}