package ng.adashi.ui.verification.docs.models

data class DocsInfoResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)