package ng.adashi.ui.more

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentMoreBinding
import ng.adashi.databinding.FragmentTransactionsBinding
import ng.adashi.network.SessionManager
import ng.adashi.utils.App
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {


    override fun start() {
        super.start()

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val sessions = SessionManager(requireContext().applicationContext)


        binding.logout.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean(SessionManager.LOGINSTATE, true)
            editor.apply()
            App.token = null
            sessions.clearAuthToken()
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToLoginFragment())
        }

    }

}