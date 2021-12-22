package ng.adashi.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentHomeBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.deposit.DepositBottomSheet
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.transactions.Transaction
import ng.adashi.ui.makesavings.AddSavingsBottomSheet
import ng.adashi.ui.payout.PayoutBottomSheet
import ng.adashi.ui.withdraw.WithdrawBottomSheet
import ng.adashi.utils.DataState

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun start() {

        Log.d("ERRR","in home fragment")

        val viewModel : HomeViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val agent_name = prefs.getString(SessionManager.AGENT_FIRST_NAME, "name")
        binding.agentname.text = getString(R.string.agent_name, agent_name)

        val sessions = SessionManager(requireContext())

        viewModel.transactions.observe(this, { response ->
            when (response) {
                is DataState.Success<Data> -> {
                    initAdapter(response.data.transactions)
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
                        Toast.makeText(requireContext(), "login.. you have been idle for a while", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    }else{
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                   // showSnackBar("loading..")
                }
            }
        })


        binding.savings.setOnClickListener {
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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                /**
                 *
                 *  Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
                 *
                 */
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


    }

    private fun initAdapter(data: MutableList<Transaction>) {

        val adapter = TransactonsAdapter {

        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}