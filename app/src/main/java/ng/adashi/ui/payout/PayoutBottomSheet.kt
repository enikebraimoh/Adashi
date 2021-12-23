package ng.adashi.ui.payout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ng.adashi.R
import ng.adashi.databinding.PayoutDialogueSheetBinding
import ng.adashi.ui.SuccessDialogue
import ng.adashi.utils.RoundedBottomSheet

class PayoutBottomSheet : RoundedBottomSheet() {
    lateinit var binding : PayoutDialogueSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.payout_dialogue_sheet ,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}