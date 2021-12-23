package ng.adashi.ui.transactions

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentTransactionsBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.home.HomeFragmentDirections
import ng.adashi.ui.home.TransactonsAdapter
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.transactions.Transaction
import ng.adashi.ui.savers.models.Saver
import ng.adashi.utils.DataState
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : BaseFragment<FragmentTransactionsBinding>(R.layout.fragment_transactions){

    @Inject
    lateinit var sessions : SessionManager

    override fun start() {
        super.start()

        val viewModel : TransactionsViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

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
                        findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToLoginFragment())
                    }else{
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    // showSnackBar("loading..")
                }
            }
        })

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun initAdapter(data: MutableList<Transaction>) {

        val adapter = TransactonsAdapter{

        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}