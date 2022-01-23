package ng.adashi.ui.verification

import androidx.navigation.fragment.findNavController
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentAgentBasicVerificationBinding

class AgentBasicVerificationFragment : BaseFragment<FragmentAgentBasicVerificationBinding>(R.layout.fragment_agent_basic_verification) {

    override fun start() {
        super.start()

        binding.nextButton.setOnClickListener {

            findNavController().navigate(AgentBasicVerificationFragmentDirections.actionAgentBasicVerificationFragmentToAgentOthersVerificationFragment())

        }

    }

}