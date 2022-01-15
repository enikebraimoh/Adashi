package ng.adashi.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ng.adashi.R
import ng.adashi.databinding.PasswordBottomSheetBinding
import ng.adashi.utils.RoundedBottomSheet

class PasswordBottomSheet(val click : (pin : String) -> Unit): RoundedBottomSheet() {
    lateinit var binding: PasswordBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.password_bottom_sheet, container, false
        )

        binding.continueBtn.setOnClickListener {
            val pin = binding.otpView.text.toString()
            click(pin)
            dismiss()
        }

        return binding.root
    }







}