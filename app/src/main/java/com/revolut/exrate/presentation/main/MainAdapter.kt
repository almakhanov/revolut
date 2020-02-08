package com.revolut.exrate.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rate.view.*
import com.revolut.exrate.R
import com.revolut.exrate.domain.entity.RateItem
import com.revolut.exrate.presentation.base.BaseViewHolder
import com.revolut.exrate.presentation.extensions.doOnTextChange

class MainAdapter : RecyclerView.Adapter<MainAdapter.RateViewHolder>() {

    private var dataSet = mutableListOf<RateItem>()
    var clickListener: ((RateItem) -> Unit)? = null
    var textChangeListener: ((RateItem, String) -> Unit)? = null

    fun updateListItems(data: List<RateItem>) {
        val diffCallback = DiffUtilsCallback(this.dataSet, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.dataSet.clear()
        this.dataSet.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rate,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    inner class RateViewHolder(view: View) : BaseViewHolder<RateItem>(view) {

        init {
            itemView.setOnClickListener {
                clickListener?.invoke(dataSet[adapterPosition])
            }
            itemView.rateEditText.doOnTextChange {
                textChangeListener?.invoke(dataSet[adapterPosition], it)
            }
        }

        override fun bind(item: RateItem) {
            itemView.logoImageView.setImageResource(item.icon)
            itemView.titleTextView.text = item.code
            itemView.descriptionTextView.text = item.label
            itemView.rateEditText.setText(item.rate?.toString())
            itemView.rateEditText.isEnabled = item.editable
            itemView.rateEditText.setSelection(item.selectionIndex)
        }
    }

    inner class DiffUtilsCallback(
        private val oldList: List<RateItem>,
        private val newList: List<RateItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].code == newList[newItemPosition].code

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}