package ng.adashi.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import ng.adashi.R
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE




class BalanceViewPagerAdapter(private var balance : MutableList<String>,private var title : List<String>)
    : RecyclerView.Adapter<BalanceViewPagerAdapter.BalanceViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        return BalanceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.balance_item,parent,false))
    }

    override fun getItemCount(): Int =  balance.size

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.balance.text = balance[position]
        holder.balance_title.text = title[position]
    }


    inner class BalanceViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val balance : TextView = itemView.findViewById(R.id.the_balance)
        val balance_title : TextView = itemView.findViewById(R.id.bl_title)

    }

}