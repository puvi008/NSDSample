package com.dharsini.nsd_sample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dharsini.nsd_sample.databinding.RowItemBinding

class ServiceAdapter(val applicationContext: Context, val dataList: ArrayList<ScannedData>?) :
    RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = DataBindingUtil.inflate<RowItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_item,
            parent,
            false
        )
        return MovieViewHolder(view, applicationContext)
    }

    override fun getItemCount(): Int = dataList?.size ?: 0


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(dataList!!.get(position))
    }

    fun addData(moreItem: ArrayList<ScannedData>?) {
        dataList?.addAll(moreItem!!)
        notifyDataSetChanged()
    }
}

class MovieViewHolder(
    var binding: RowItemBinding,
    var applicationContext: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(aScannedData: ScannedData) {
        binding.mScannedData = aScannedData
    }

}