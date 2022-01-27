package ng.adashi.ui.verification.others

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.samehadar.iosdialog.CamomileSpinner
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentAgentOthersVerificationBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.verification.basic.AgentBasicVerificationFragmentDirections
import ng.adashi.ui.verification.basic.BasicVerificationViewModel
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.ui.verification.others.models.OthersInfo
import ng.adashi.ui.verification.others.models.OthersInfoResponse
import ng.adashi.utils.DataState

@AndroidEntryPoint
class AgentOthersVerificationFragment : BaseFragment<FragmentAgentOthersVerificationBinding>(R.layout.fragment_agent_others_verification) {

    private lateinit var loadingdialog: Dialog

    override fun start() {
        super.start()

        val viewModel: OthersVerificationViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

        val sessions = SessionManager(requireContext())

        loadingdialog = Dialog(requireContext())


        binding.Address.setText("NO 805 Primary School Street Danladi Nasidi Kano")
        binding.city.setText("Kano")
        binding.lga.setText("Kumbotso")
        binding.state.setText("Kano")
        binding.OLga.setText("Kano")
        binding.OState.setText("Kano")
        binding.nextName.setText("Zakari Sale")
        binding.nextPhone.setText("7038094938")

        binding.nextButton.setOnClickListener {

            val info = OthersInfo(
                binding.OLga.text.toString(),
                binding.OState.text.toString(),
                binding.Address.text.toString(),
                city = binding.city.text.toString(),
                binding.lga.text.toString(),
                binding.nextName.text.toString(),
                binding.nextPhone.text.toString().toLong(),
                binding.state.text.toString()
            )

            viewModel.updateInfo(info)
        }

        viewModel.updateResponse.observe(this, { response ->
            when (response) {
                is DataState.Success<OthersInfoResponse> -> {
                    CancelProgressLoader()
                    showSnackBar("Success")
                    findNavController().navigate(AgentOthersVerificationFragmentDirections.actionAgentOthersVerificationFragmentToAgentDocsVerificationFragment())
                }
                is DataState.Error -> {
                    Log.d("TESTT","error in fragment")
                    CancelProgressLoader()
                    if (!response.error.localizedMessage.isNullOrEmpty()) {
                        showSnackBar(response.error.localizedMessage!!.toString())
                    } else {
                        showSnackBar("Slow or no Internet Connection")
                    }
                }
                is DataState.GenericError -> {
                    CancelProgressLoader()
                    if (response.code == 403 || response.error?.message.equals("Unauthenticated")) {
                        sessions.clearAuthToken()
                        Toast.makeText(
                            requireContext(),
                            "login.. you have been idle for a while",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(AgentOthersVerificationFragmentDirections.actionAgentOthersVerificationFragmentToLoginFragment())
                    } else {
                        CancelProgressLoader()
                        showSnackBar(response.error?.message!!.toString())
                    }
                }
                DataState.Loading -> {
                    ShowProgressLoader(requireContext(), false, false)
                }
            }
        })


    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_LONG).show()
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