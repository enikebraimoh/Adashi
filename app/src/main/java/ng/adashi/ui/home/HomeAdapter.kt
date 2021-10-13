package ng.adashi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import ng.adashi.core.BaseAdapter
import ng.adashi.databinding.TransactionItemLayoutBinding
import ng.adashi.models.Transactions

class HomeAdapter(val click : (vendor: Transactions)->Unit) : BaseAdapter<Transactions>(DiffCallBack()) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val view = TransactionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return view
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        val item = getItem(position)
        (binding as TransactionItemLayoutBinding).data = item
        binding.executePendingBindings()
    }

    class DiffCallBack() : DiffUtil.ItemCallback<Transactions>() {
        override fun areContentsTheSame(
            oldItem: Transactions,
            newItem: Transactions
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: Transactions,
            newItem: Transactions
        ): Boolean = oldItem.ammount == newItem.ammount
    }

}