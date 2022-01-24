package ng.adashi.ui.verification.basic.models

data class Data(
    val _id: String,
    val agent_id: String,
    val bankAccounts: List<Any>,
    val email: String,
    val email_verified_at: Any,
    val firstName: String,
    val identity: Identity,
    val isAggregatorApproved: Boolean,
    val lastName: String,
    val nextOfKin: NextOfKin,
    val phoneNumber: Long,
    val role: List<String>,
    val savers: List<String>,
    val wallet: String
)