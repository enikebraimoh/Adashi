package ng.adashi.ui.deposit

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import ng.adashi.R
import ng.adashi.databinding.PayoutDialogueSheetBinding
import ng.adashi.utils.RoundedBottomSheet


class PayoutBottomSheet : RoundedBottomSheet() {
    lateinit var binding: PayoutDialogueSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.payout_dialogue_sheet, container, false)


        binding.copyBtn.setOnClickListener {
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("account","${binding.bankAcountNumber.text.toString()}\n${binding.bankname.text.toString()}\nEnike Braimoh")
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "copied to clipboard 🤗", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}