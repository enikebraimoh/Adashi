package ng.adashi.ui.verification.docs

import android.widget.ImageView

data class DocInfo(
    val idType: String,
    val idNumber: String,
    val idFile: ImageView,
    val billFile: ImageView,
    val signatureFile: ImageView
)