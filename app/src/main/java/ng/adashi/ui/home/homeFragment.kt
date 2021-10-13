package ng.adashi.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_menu -> {
                findNavController().navigate(R.id.notificationsFragment)
                true
            }
            else -> false
        }
    }

    override fun start() {
        setHasOptionsMenu(true)
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
        binding.deposit.setOnClickListener {
            val BS = PayoutBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.deposit.setOnClickListener {
            val BS = DepositBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.withdrawal.setOnClickListener {
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