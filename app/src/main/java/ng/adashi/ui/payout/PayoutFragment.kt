package ng.adashi.ui.payout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentPayoutBinding

class PayoutFragment : BaseFragment<FragmentPayoutBinding>(R.layout.fragment_payout) {
    override fun start() {
        super.start()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}