package ng.adashi.ui.savers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import ng.adashi.core.BaseAdapter
import ng.adashi.databinding.SaverItemBinding
import ng.adashi.ui.savers.models.Saver

class SaversAdapter(val click : (saver: Saver)->Unit) : BaseAdapter<Saver>(DiffCallBack()) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val view = SaverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return view
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        val item = getItem(position)
        (binding as SaverItemBinding).data = item
        binding.root.setOnClickListener { click(item) }
        binding.executePendingBindings()
    }

    class DiffCallBack() : DiffUtil.ItemCallback<Saver>() {
        override fun areContentsTheSame(
            oldItem: Saver,
            newItem: Saver
        ): Boolean = oldItem == newItem

        override fun areItemsTheSame(
            oldItem: Saver,
            newItem: Saver
        ): Boolean = oldItem._id == newItem._id
    }

}