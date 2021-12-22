package ng.adashi.ui.home.models.transactions

data class Transaction(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val balance_after: Int,
    val balance_before: Int,
    val createdAt: String,
    val description: String,
    val purpose: List<String>,
    val `receiver`: String,
    val reference: String,
    val sender: String,
    val status: List<String>,
    val txn_type: List<String>,
    val updatedAt: String,
    val user: String
)