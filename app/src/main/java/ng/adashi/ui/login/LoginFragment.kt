package ng.adashi.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentLoginBinding
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState
import ng.adashi.utils.Utils
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    @Inject lateinit var sessions : SessionManager

    override fun start() {

        Log.d("ERRR","in login fragment")

        val viewModel : LoginViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToDashboard.observe(this, {
            if (it) {
                val editor = sessions.prefs.edit()
                editor.putBoolean(SessionManager.LOGINSTATE, true)
                editor.apply()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                viewModel.navigationDone()
            }
        })

        viewModel.login.observe(this, { response ->
            when (response) {
                is DataState.Success<LoginToken> -> {
                    displayProgressBar(false)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    Toast.makeText(requireContext(), "Slow or no Internet Connection", Toast.LENGTH_SHORT).show()
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                    if (response.code!! >= 500){
                        showSnackBar("internal server error")
                    }
                    else{
                        showSnackBar(response.error?.message!!)
                    }
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