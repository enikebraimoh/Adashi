package ng.adashi.ui.payout.models

data class PayoutResponse(
    val `data`: Data,
    val message: String,
    val status: String
)