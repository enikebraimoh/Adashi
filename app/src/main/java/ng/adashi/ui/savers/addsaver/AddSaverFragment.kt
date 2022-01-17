package ng.adashi.ui.savers.addsaver

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.samehadar.iosdialog.CamomileSpinner
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentAddSaverBinding
import ng.adashi.network.SessionManager
import ng.adashi.ui.home.HomeFragmentDirections
import ng.adashi.ui.savers.addsaver.models.SaverResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.utils.DataState

@AndroidEntryPoint
class AddSaverFragment : BaseFragment<FragmentAddSaverBinding>(R.layout.fragment_add_saver) {

    private lateinit var loadingdialog: Dialog

    override fun start() {
        super.start()

        loadingdialog = Dialog(requireContext())


        val viewModel: AddSaverViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this

        val sessions = SessionManager(requireContext())


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addSaverBtn.setOnClickListener {
            val saver = SingleSaver(
                binding.address.text.toString(),
                binding.city.text.toString(),
                binding.email.text.toString(),
                binding.firstname.text.toString(),
                binding.lastName.text.toString(),
                binding.occupation.text.toString(),
                binding.phone.text.toString(),
                binding.state.text.toString()
            )

            viewModel.AddSaver(saver)
        }

        viewModel.addSaverResponse.observe(this, { response ->
            when (response) {
                is DataState.Success<SaverResponse> -> {
                    CancelProgressLoader()
                    showSnackBar("Successfully Added Saver")
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
                    if (response.code == 403 || response.error?.message.equals("Unauthenticated")) {
                        sessions.clearAuthToken()
                        CancelProgressLoader()
                        Toast.makeText(
                            requireContext(),
                            "login.. you have been idle for a while",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    } else {
                        CancelProgressLoader()
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    ShowProgressLoader(requireContext(),false,false)
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
        loadingdialog.getWindow()?.setBackgroundDrawable(context.resources.getDrawable(R.drawable.d_round_corner_white_bkg))
        val loader: CamomileSpinner = loadingdialog.findViewById(R.id.loader)
        loader.start()
        if (!outsideTouch) loadingdialog.setCanceledOnTouchOutside(false)
        if (!cancleable) loadingdialog.setCancelable(false)
        loadingdialog.show()
    }

}