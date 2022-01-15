package ng.adashi.ui.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.databinding.WithdrawDialogueSheetBinding
import ng.adashi.ui.password.PasswordBottomSheet
import ng.adashi.utils.RoundedBottomSheet

@AndroidEntryPoint
class WithdrawBottomSheet : RoundedBottomSheet() {
    lateinit var binding: WithdrawDialogueSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.withdraw_dialogue_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.proceed.setOnClickListener {

            val passwordBS = PasswordBottomSheet{

            }

            passwordBS.show(requireActivity().supportFragmentManager, "something")

        }
    }

}