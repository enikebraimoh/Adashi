package ng.adashi.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentLoginBinding
import ng.adashi.models.login.LoginResponse
import ng.adashi.network.NetworkDataSource
import ng.adashi.network.RetrofitInstance
import ng.adashi.repository.LoginRepository
import ng.adashi.utils.DataState
import ng.adashi.utils.ErrorResponse
import ng.adashi.utils.Status

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun start() {
        val application = requireNotNull(this.activity).application
        val network = NetworkDataSource(RetrofitInstance)
        val viewModelProviderFactory = LoginFactory(application, LoginRepository(network))

        val viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        ).get(LoginViewModel::class.java)
        binding.data = viewModel
        binding.lifecycleOwner = this
        viewModel.login.observe(this, { response ->
            when (response) {
                is DataState.Success -> {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
                is DataState.Error -> {
                    showSnackBar(response.error.localizedMessage!!)
                }
                is DataState.GenericError -> {
                  showSnackBar(response.error?.message!!)
                }
                DataState.Loading -> {
                    showSnackBar("Loading.....")
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(message: String) {

    }

}