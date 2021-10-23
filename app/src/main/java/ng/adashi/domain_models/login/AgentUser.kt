package ng.adashi.domain_models.login

data class AgentUser(
    val Savers: List<String>? = null,
    val __v: Int? = null,
    val _id: String,
    val agentID: String,
    val email: String,
    val firstName: String,
    val homeAddress: List<Any>? = null,
    val identity: Identity? = null,
    val lastName: String,
    val name: String,
    val walletId: String
)