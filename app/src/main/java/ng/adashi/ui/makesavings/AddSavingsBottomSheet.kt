package ng.adashi.ui.makesavings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ng.adashi.R
import ng.adashi.databinding.AddSavingsDialogueSheetBinding
import ng.adashi.ui.SuccessDialogue
import ng.adashi.utils.RoundedBottomSheet

class AddSavingsBottomSheet : RoundedBottomSheet() {
    lateinit var binding : AddSavingsDialogueSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_savings_dialogue_sheet ,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            val ss = SuccessDialogue(requireActivity())
            dismiss()
            ss.show()
        }
    }

}