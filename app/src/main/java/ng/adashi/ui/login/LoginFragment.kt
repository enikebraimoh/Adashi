package ng.adashi.ui.login

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentLoginBinding
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState
import ng.adashi.utils.Utils

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun start() {
        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = LoginFactory(application, AuthRepository(network))

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
                    checkLoginState(response.data)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(response.data.data.user?.firstName!!))
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    if ()
                    displayProgressBar(false)
                    showSnackBar(response.error?.message!!)
                }
                DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    fun checkLoginState(datastate: LoginResponse) {
        val state = Utils.LoginState(requireContext().applicationContext)
                GlobalScope.launch {
                    state.saveLoginState(true)
                    state.saveAccessToken(datastate)
            }
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.spinKit.visibility = View.VISIBLE
                binding.loginButton.visibility = View.INVISIBLE
                binding.emailField.isEnabled = false
                binding.passwordField.isEnabled = false
            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.loginButton.visibility = View.VISIBLE
                binding.emailField.isEnabled = true
                binding.passwordField.isEnabled = true
            }
        }

    }

}