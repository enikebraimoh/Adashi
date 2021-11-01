package ng.adashi.ui.home

import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentHomeBinding
import ng.adashi.domain_models.Transactions
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.repository.HomeRepository
import ng.adashi.ui.deposit.DepositBottomSheet
import ng.adashi.ui.home.models.AgentWalletResponse
import ng.adashi.ui.login.LoginFactory
import ng.adashi.ui.login.LoginFragmentDirections
import ng.adashi.ui.login.LoginViewModel
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
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val sessions = SessionManager(requireContext())
        val viewModelProviderFactory = HomeViewModelFactory(application, HomeRepository(network))

        val viewModel = ViewModelProvider(
            requireActivity(), viewModelProviderFactory).get(HomeViewModel::class.java)

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
                        findNavController().popBackStack()
                    }else{
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    showSnackBar("loading..")
                }
            }
        })

        // fake data
        var listData = mutableListOf<Transactions>(
            Transactions(44),
            Transactions(44),
            Transactions(44),
            Transactions(44)
        )

        initAdapter(listData)

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