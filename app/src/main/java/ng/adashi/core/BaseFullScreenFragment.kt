package ng.adashi.core

import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

abstract class BaseFullScreenFragment<T : ViewBinding>(@LayoutRes var layoutRes: Int) :
    BaseFragment<T>(layoutRes) {


    override fun onResume() {
        super.onResume()
        setTransparentStatusBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    private fun setTransparentStatusBar() {
        // Hide the status bar.
       requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
               // Set the content to appear under the system bars so that the
               // content doesn't resize when the system bars hide and show.
               or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
               or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
               or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
               // Hide the nav bar and status bar
               or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               or View.SYSTEM_UI_FLAG_FULLSCREEN)

    }

}