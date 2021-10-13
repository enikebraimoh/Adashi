package ng.adashi.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.AddSavingsDialogueSheetBinding
import ng.adashi.databinding.FragmentHomeBinding
import ng.adashi.models.Transactions
import ng.adashi.ui.deposit.DepositBottomSheet
import ng.adashi.ui.makesavings.AddSavingsBottomSheet
import ng.adashi.ui.payout.PayoutBottomSheet
import ng.adashi.ui.withdraw.WithdrawBottomSheet

class homeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onStart() {
        super.onStart()

        // fake data
        var listData = mutableListOf<Transactions>(
            Transactions(44),
            Transactions(44),
            Transactions(44),
            Transactions(44)
        )

        initAdapter(listData)
        binding.savings.setOnClickListener {
            val BS = AddSavingsBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.payout.setOnClickListener {
            val BS = PayoutBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.deposit.setOnClickListener {
            val BS = DepositBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.withdraw.setOnClickListener {
            val BS = WithdrawBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")


        }


    }

    private fun initAdapter(data: MutableList<Transactions>) {

        val adapter = HomeAdapter {

        }

        binding.recyclerView .adapter = adapter
        adapter.submitList(data)

    }

}