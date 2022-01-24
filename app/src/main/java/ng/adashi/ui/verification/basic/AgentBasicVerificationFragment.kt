package ng.adashi.ui.verification.basic

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.samehadar.iosdialog.CamomileSpinner
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentAgentBasicVerificationBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.home.HomeFragmentDirections
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.utils.DataState
import java.util.*

@AndroidEntryPoint
class AgentBasicVerificationFragment : BaseFragment<FragmentAgentBasicVerificationBinding>(R.layout.fragment_agent_basic_verification) {

    private lateinit var loadingdialog: Dialog

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun start() {
        super.start()

        val viewModel: BasicVerificationViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

        val sessions = SessionManager(requireContext())

        loadingdialog = Dialog(requireContext())


        binding.birthdayField.setOnClickListener {
            showCalenderDialog()
        }

        binding.birthday.setText("14/09/2001")
        binding.bvn.setText("1234567890")
        binding.firstname.setText("Umar")
        binding.gender.setText("Male")
        binding.lastName.setText("Zakari")
        binding.phone.setText("8134660261")

        binding.nextButton.setOnClickListener {

            val info = BasicInfo(
                binding.birthday.text.toString(),
                binding.bvn.text.toString().toInt(),
                binding.firstname.text.toString(),
                binding.gender.text.toString(),
                binding.lastName.text.toString(),
                binding.phone.text.toString().toLong())

            viewModel.updateInfo(info)
        }

        viewModel.updateResponse.observe(this, { response ->
            when (response) {
                is DataState.Success<BasicInfoResponse> -> {
                    CancelProgressLoader()
                    findNavController().navigate(AgentBasicVerificationFragmentDirections.actionAgentBasicVerificationFragmentToAgentOthersVerificationFragment())
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
                        findNavController().navigate(AgentBasicVerificationFragmentDirections.actionAgentBasicVerificationFragmentToLoginFragment())
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

    fun showCalenderDialog(){
        val dpd = DatePickerDialog(requireContext(),{ view, year, monthOfYear, dayOfMonth->
            // Display Selected date in textbox
            var lblDate = ("$day/${monthOfYear}/$year")
            binding.birthday.setText(lblDate)
        }, year, month, day)

        dpd.show()
    }

}