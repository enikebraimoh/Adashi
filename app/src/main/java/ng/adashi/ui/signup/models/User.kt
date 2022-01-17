package ng.adashi.ui.signup.models

data class User(
    val _id: String,
    val agent_id: String,
    val bankAccounts: List<Any>,
    val email: String,
    val email_verified_at: Any,
    val firstName: String,
    val identity: Identity,
    val isAggregatorApproved: Boolean,
    val lastName: String,
    val role: List<String>,
    val savers: List<Any>,
    val wallet: String
)