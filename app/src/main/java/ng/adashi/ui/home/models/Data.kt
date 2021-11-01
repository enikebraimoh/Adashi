package ng.adashi.ui.home.models

data class Data(
    val allow_bank_transfer: Boolean,
    val allow_fund: Boolean,
    val allow_wallet_transfer: Boolean,
    val allow_withdraw: Boolean,
    val balance: Int,
    val balances: List<Balance>,
    val created_at: String,
    val currency: String,
    val customer_email: String,
    val customer_phone: String,
    val environment: String,
    val meta_data: Any,
    val organization_id: String,
    val status: String,
    val updated_at: String,
    val wallet_id: String
)