package ng.adashi.ui.savers

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentSaversBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.home.HomeFragmentDirections
import ng.adashi.ui.savers.models.Data
import ng.adashi.ui.savers.models.Saver
import ng.adashi.utils.DataState
import javax.inject.Inject

@AndroidEntryPoint
class SaversFragment : BaseFragment<FragmentSaversBinding>(R.layout.fragment_savers) {

    @Inject lateinit var sessions : SessionManager

    override fun start() {
        super.start()

        val viewModel :SaversViewModel by viewModels()
        binding.data = viewModel
        binding.lifecycleOwner = this

        viewModel.savers.observe(this, Observer { response ->
            when (response) {
                is DataState.Success<Data> -> {
                    initAdapter(response.data.savers)
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
                        findNavController().navigate(SaversFragmentDirections.actionSaversFragmentToLoginFragment())
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

    private fun initAdapter(data: MutableList<Saver>) {

        val adapter = SaversAdapter()

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}