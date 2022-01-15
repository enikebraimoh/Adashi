package ng.adashi.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.R
import ng.adashi.core.BaseFragment
import ng.adashi.databinding.FragmentHomeBinding
import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.network.SessionManager
import ng.adashi.ui.home.models.transactions.Data
import ng.adashi.ui.home.models.transactions.Transaction
import ng.adashi.ui.payout.PayoutBottomSheet
import ng.adashi.ui.withdraw.WithdrawBottomSheet
import ng.adashi.utils.DataState
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    var firstTime = true
    override fun start() {

        val money = mutableListOf("NGN 0", "NGN 0")
        val balance = mutableListOf("Adashi Balance", "Earnings")

        Log.d("ERRR", "in home fragment")

        val viewModel: HomeViewModel by viewModels()

        binding.data = viewModel
        binding.lifecycleOwner = this


        val sessions = SessionManager(requireContext())

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val agent_name = prefs.getString(SessionManager.AGENT_FIRST_NAME, "name")
        val wallet_id = prefs.getString(SessionManager.WALLET_ID, "aggentt")

        binding.agentname.text = getString(R.string.agent_name, agent_name)
        //Toast.makeText(requireContext(), wallet_id, Toast.LENGTH_SHORT).show()

        addFirstDot(binding)

        //Listening to page callbacks
        binding.viewpager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (firstTime)
                        firstTime = false
                    else
                        addDot(position)
                }
            }
        )


        binding.viewpager.adapter = BalanceViewPagerAdapter(money, balance)
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewModel.transactions.observe(this, { response ->
            when (response) {
                is DataState.Success<Data> -> {
                    initAdapter(response.data.transactions)
                    binding.viewmore.visibility = View.VISIBLE

                }
                is DataState.Error -> {
                    if (!response.error.localizedMessage.isNullOrEmpty()) {
                        showSnackBar(response.error.localizedMessage!!)
                    }
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    if (response.code == 403 || response.error?.message.equals("Unauthenticated")) {
                        sessions.clearAuthToken()
                        Toast.makeText(
                            requireContext(),
                            "login.. you have been idle for a while",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    } else {
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    // showSnackBar("loading..")
                }
            }
        })

        viewModel.wallet_ballance.observe(this, { response ->
            when (response) {
                is DataState.Success<AgentWalletDetails> -> {

                    val newformat: NumberFormat = NumberFormat.getCurrencyInstance()
                    newformat.setMaximumFractionDigits(0)
                    newformat.setCurrency(Currency.getInstance("NGN"))

                    val bal = response.data.data.balance.toString()
                    val earnings = response.data.data.earnings.toString()

                    money.clear()

                    money.addAll(
                        0,
                        listOf(newformat.format(bal.toLong()), newformat.format(earnings.toLong()))
                    )
                    (binding.viewpager.adapter as BalanceViewPagerAdapter).notifyDataSetChanged()

                    //money.add(newformat.format(bal.toLong()))
                    //money.add(newformat.format(earnings.toLong()))


                }
                is DataState.Error -> {
                    if (!response.error.localizedMessage.isNullOrEmpty()) {
                        showSnackBar(response.error.localizedMessage!!)
                    }
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {
                    if (response.code == 403 || response.error?.message.equals("Unauthenticated")) {
                        sessions.clearAuthToken()
                        /* Toast.makeText(
                             requireContext(),
                             "login.. you have been idle for a while",
                             Toast.LENGTH_SHORT
                         ).show()*/
                        // findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                    } else {
                        showSnackBar(response.error?.message!!)
                    }
                }
                DataState.Loading -> {
                    // showSnackBar("loading..")
                }
            }
        })



        binding.viewmore.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransactionsFragment())
        }

        binding.savings.setOnClickListener {
            //val BS = AddSavingsBottomSheet()
            //BS.show(requireActivity().supportFragmentManager, "something")
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMakeSavingFragment())

        }
        binding.deposit.setOnClickListener {
            val BS = PayoutBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")

        }
        binding.withdrawal.setOnClickListener {
            val BS = WithdrawBottomSheet()
            BS.show(requireActivity().supportFragmentManager, "something")
        }
        binding.payout.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPayoutFragment())
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                /**
                 *
                 *  Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
                 *
                 */
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


    }

    private fun initAdapter(data: MutableList<Transaction>) {

        val adapter = TransactonsAdapter{
            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)

        }

        binding.recyclerView.adapter = adapter
        if (data.size > 5) {
            val newData = data.slice(0..4)
            adapter.submitList(newData)
        } else {
            adapter.submitList(data)
        }


    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    // creates dot indicator for the first enterance of the onBoardingScreen
    private fun addFirstDot(view: FragmentHomeBinding) {

        view.pos1.setText(Html.fromHtml("&#8226;"))
        view.pos2.setText(Html.fromHtml("&#8226;"))

        view.pos1.textSize = 40f
        view.pos2.textSize = 40f


        view.pos1.setTextColor(Color.parseColor("#C4C4C4"))
        view.pos2.setTextColor(Color.parseColor("#C4C4C4"))
        view.pos1.setTextColor(Color.parseColor("#FED525"))

    }

    //creates dot indicator
    private fun addDot(position: Int) {
        val textViews = arrayOfNulls<TextView>(2)
        binding?.let { it.liner.removeAllViews() }
        var i = 0
        while (i < 2) {
            textViews[i] = TextView(requireContext())
            textViews[i]?.setText(Html.fromHtml("&#8226;"))
            textViews[i]?.textSize = 40f
            textViews[i]?.setTextColor(Color.parseColor("#C4C4C4"))

            binding?.let { _view ->
                _view.liner.addView(textViews[i])
            }
            i++
        }

        if (textViews.size > 0)
            textViews[position]?.setTextColor(Color.parseColor("#FED525"))
    }

}