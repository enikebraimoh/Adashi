package ng.adashi.ui.payout

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.samehadar.iosdialog.CamomileSpinner
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentPayoutBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.makesavings.MakeSavingFragmentDirections
import ng.adashi.ui.makesavings.MakeSavingsViewModel
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.password.PasswordBottomSheet
import ng.adashi.ui.payout.models.PayoutResponse
import ng.adashi.utils.DataState
import javax.inject.Inject

@AndroidEntryPoint
class PayoutFragment : BaseFragment<FragmentPayoutBinding>(R.layout.fragment_payout) {

    @Inject
    lateinit var sessions: SessionManager

    private lateinit var loadingdialog: Dialog

    override fun start() {
        super.start()

        val viewModel: PayoutViewModel by viewModels()
        binding.data = viewModel
        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.payout.setOnClickListener {

            if (viewModel.validateAmountFIeld()) {
                if (viewModel.validateSaverIdField()) {

                    val passwordBS = PasswordBottomSheet { pin ->
                        val details = SaveDetails(
                            binding.accountId.text.toString(),
                            binding.amount.text.toString(),
                            pin
                        )
                        viewModel.pay(details)
                    }

                    passwordBS.show(requireActivity().supportFragmentManager, "something")

                }
            }
        }

        viewModel.response.observe(this, { response ->
            when (response) {
                is DataState.Success<PayoutResponse> -> {
                    CancelProgressLoader()
                    showSnackBar("Money successfully Payed Out!")
                    findNavController().popBackStack()
                }
                is DataState.Error -> {
                    if (!response.error.localizedMessage.isNullOrEmpty()) {
                        CancelProgressLoader()
                        showSnackBar(response.error.localizedMessage!!)
                    }
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    if (response.error?.message.equals("Unauthenticated")) {
                        sessions.clearAuthToken()
                        CancelProgressLoader()
                        Toast.makeText(
                            requireContext(),
                            "login.. you have been idle for a while",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(PayoutFragmentDirections.actionPayoutFragmentToLoginFragment())
                    } else {
                        CancelProgressLoader()
                        showSnackBar(response.error?.message.toString())
                    }
                }
                DataState.Loading -> {
                    ShowProgressLoader(requireContext(), false, false)
                }
            }
        })

    }


    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    fun CancelProgressLoader() {
        loadingdialog.cancel()
        loadingdialog.dismiss()
    }

    fun ShowProgressLoader(context: Context, outsideTouch: Boolean, cancleable: Boolean) {
        loadingdialog = Dialog(context)
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingdialog.setContentView(R.layout.loader_dialog)
        loadingdialog.getWindow()
            ?.setBackgroundDrawable(context.resources.getDrawable(R.drawable.d_round_corner_white_bkg))
        val loader: CamomileSpinner = loadingdialog.findViewById(R.id.loader)
        loader.start()
        if (!outsideTouch) loadingdialog.setCanceledOnTouchOutside(false)
        if (!cancleable) loadingdialog.setCancelable(false)
        loadingdialog.show()
    }


}