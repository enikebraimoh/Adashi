package ng.adashi.ui.savers.saverbottomsheet

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ng.adashi.R
import ng.adashi.databinding.SaverdetailsbottomsheetBinding
import ng.adashi.ui.savers.models.Saver
import ng.adashi.utils.RoundedBottomSheet
import ng.adashi.utils.convertToCurrency

class SaverDetailsBottomSheet(val data: Saver) : RoundedBottomSheet() {

    lateinit var binding: SaverdetailsbottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.saverdetailsbottomsheet, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saverNamee.text = "${data.firstName}  ${data.lastName}"
        binding.saverEmail.text = convertToCurrency(data.wallet.balance)
        binding.saverId.text = data.saverId
        binding.saverState.text = data.state
        binding.saverAddress.text = data.address
        binding.saverPhoneNumber.text = data.phone.toString()
        binding.saverOccupation.text = data.occupation

        binding.copyImage.setOnClickListener {
            val clipboard =
                ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("saver_id",data.saverId)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Saver's ID copied", Toast.LENGTH_SHORT).show()
        }





    }


}