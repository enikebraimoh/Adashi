package ng.adashi.ui.home.models.wallet

import ng.adashi.ui.home.models.wallet.Data

data class AgentWalletResponse(
    val `data`: Data,
    val message: String,
    val status: String
)