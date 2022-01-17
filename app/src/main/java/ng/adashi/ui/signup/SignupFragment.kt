package ng.adashi.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentSignupBinding
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.login.LoginViewModel
import ng.adashi.ui.signup.models.SignUpResponse
import ng.adashi.utils.DataState

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    var email: String? = null
    var password: String? = null

    override fun start() {
        super.start()

        val viewModel: SignupViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this


        viewModel.signUp.observe(this, { response ->
            when (response) {
                is DataState.Success<SignUpResponse> -> {
                    Toast.makeText(requireContext(), "Account successfully created", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    displayProgressBar(false)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    Toast.makeText(
                        requireContext(),
                        "Slow or no Internet Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DataState.GenericError -> {
                    displayProgressBar(false)
                    if (response.code!! >= 500) {
                        showSnackBar("internal server error")
                    } else {
                        showSnackBar(response.error?.message!!.toString())
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