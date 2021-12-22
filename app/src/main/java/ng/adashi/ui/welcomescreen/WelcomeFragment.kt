package ng.adashi.ui.welcomescreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentWelcomeBinding
import ng.adashi.network.SessionManager
import javax.inject.Inject

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject lateinit var sessions : SessionManager

    override fun start() {

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )


        val state = prefs.getBoolean(SessionManager.LOGINSTATE, false)
        if (state) {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment())
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment())
        }
        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignupFragment())
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

    }

}