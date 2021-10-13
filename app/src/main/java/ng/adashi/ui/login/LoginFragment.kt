package ng.adashi.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentLoginBinding
import ng.adashi.repository.LoginRepository

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun start() {
        val application = requireNotNull(this.activity).application
        val viewModelProviderFactory = LoginFactory(application, LoginRepository())

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(LoginViewModel::class.java)
        binding.data = viewModel
        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }


    }

}