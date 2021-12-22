package ng.adashi.ui.splashscreen

import android.os.Handler
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFullScreenFragment
import ng.adashi.databinding.FragmentSplashScreenBinding
import ng.adashi.utils.App
import ng.adashi.utils.Constants

class SplashScreenFragment : BaseFullScreenFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen){
    override fun onResume() {
        super.onResume()
            Handler().postDelayed({
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeFragment())
            }, Constants.SPLASH_SCREEN_TIMEER)

    }
}