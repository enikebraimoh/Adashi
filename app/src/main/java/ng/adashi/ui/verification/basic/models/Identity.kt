package ng.adashi.ui.verification.basic.models

data class Identity(
    val birthday: String,
    val bvn: String,
    val gender: String,
    val homeAddress: HomeAddress,
    val kycDocs: List<Any>,
    val lgaOfOrigin: String,
    val stateOfOrigin: String
)