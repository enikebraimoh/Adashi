package ng.adashi.ui.verification.docs

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentAgentDocsVerificationBinding
import android.content.Intent
import android.os.Environment
import android.widget.Toast

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.InputStream
import android.graphics.Bitmap




@AndroidEntryPoint
class AgentDocsVerificationFragment : BaseFragment<FragmentAgentDocsVerificationBinding>(R.layout.fragment_agent_docs_verification) {

    override fun start() {
        super.start()

        binding.idCardFile.setOnClickListener {
            callGallery()
        }

        binding.billFile.setOnClickListener {
            callGallery()
        }

        binding.sigatureFile.setOnClickListener {
            callGallery()
        }

    }

    fun callGallery(){
         Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val minetypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,minetypes)
            startActivityForResult(it, PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == PICK_IMAGE) {
                binding.cardId.setImageURI(data?.data)
                Toast.makeText(requireContext(), data?.data.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object{
        val PICK_IMAGE = 1234
    }

}