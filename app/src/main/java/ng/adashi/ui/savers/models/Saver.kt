package ng.adashi.ui.savers.models

data class Saver(
    val __v: Int,
    val _id: String,
    val address: String,
    val circles: List<String>,
    val createdAt: String,
    val firstName: String,
    val lastName: String,
    val occupation: String,
    val phone: Long,
    val referee: String,
    val saverId: String,
    val state: String,
    val updatedAt: String,
    val wallet: Wallet
)