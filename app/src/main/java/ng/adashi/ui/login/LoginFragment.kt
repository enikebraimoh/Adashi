package ng.adashi.ui.login

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentLoginBinding
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSource
import ng.adashi.network.RetrofitInstance
import ng.adashi.repository.LoginRepository
import ng.adashi.utils.DataState

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
                is DataState.Success<LoginResponse> -> {
                    displayProgressBar(false)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackBar("Unknown Error")
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                  showSnackBar(response.error?.message!!)
                }
                DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        when (isLoading){
            true ->{
                binding.spinKit.visibility = View.VISIBLE
                binding.loginButton.visibility = View.INVISIBLE
                binding.emailField.isEnabled = false
                binding.passwordField.isEnabled = false
            }
            false ->{
                binding.spinKit.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.emailField.isEnabled = true
                binding.passwordField.isEnabled = true
            }
        }

    }

}