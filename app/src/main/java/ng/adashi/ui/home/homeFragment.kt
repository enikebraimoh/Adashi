package ng.adashi.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentHomeBinding
import ng.adashi.domain_models.Transactions
import ng.adashi.network.SessionManager
import ng.adashi.ui.deposit.DepositBottomSheet
import ng.adashi.ui.home.models.AgentWalletResponse
import ng.adashi.ui.makesavings.AddSavingsBottomSheet
import ng.adashi.ui.payout.PayoutBottomSheet
import ng.adashi.ui.withdraw.WithdrawBottomSheet
import ng.adashi.utils.DataState

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

        val viewModel : HomeViewModel by viewModels()
        //setHasOptionsMenu(true)

        Log.d("ERRR","in home fragment")

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val sessions = SessionManager(requireContext())

        binding.data = viewModel

        binding.lifecycleOwner = this
        viewModel.wallet_ballance.observe(this, { response ->
            when (response) {
                is DataState.Success<AgentWalletResponse> -> {
                    showSnackBar(response.data.data.balance.toString())
                }
                is DataState.Error -> {
                    if (!response.error.localizedMessage.isNullOrEmpty()){
                        showSnackBar(response.error.localizedMessage!!)
                    }
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    if (response.code == 403 || response.error?.message.equals("Unauthenticated") ){
                        sessions.clearAuthToken()
                        findNavController().navigate(homeFragmentDirections.actionHomeFragmentToLoginFragment())
                    }else{
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    showSnackBar("loading..")
                }
            }
        })

        binding.savings.setOnClickListener {
            viewModel.getWalletDetails("6174be37175974f7ca2f3336")
            val BS = AddSavingsBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.deposit.setOnClickListener {
            val BS = PayoutBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.withdrawal.setOnClickListener {
            val BS = DepositBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")
            findNavController().popBackStack()

        }
        binding.payout.setOnClickListener {
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

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}