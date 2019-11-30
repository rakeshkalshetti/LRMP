package com.lrm.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lrm.databinding.DashboardLayoutBinding
import com.lrm.databinding.ListFooterLayoutBinding
import com.lrm.model.DashBoard
import com.lrm.util.State
import kotlinx.android.synthetic.main.dashboard_layout.view.*
import kotlinx.android.synthetic.main.list_footer_layout.view.*
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import android.graphics.Bitmap
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions

class DashBoardAdapter(private val retry: () -> Unit)
    : PagedListAdapter<DashBoard, RecyclerView.ViewHolder>(dashDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE)
            DashViewHolder.create(parent)
        else ListFooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as DashViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val dashDiffCallback = object : DiffUtil.ItemCallback<DashBoard>() {
            override fun areItemsTheSame(oldItem: DashBoard, newItem: DashBoard): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: DashBoard, newItem: DashBoard): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    class DashViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(dashBoard: DashBoard?) {
            if (dashBoard != null) {
                itemView.nameTextView.setText(dashBoard.name)
                itemView.numberTextView.setText(dashBoard.mobileNumber)

                dashBoard.lifelineDocuments.let {

                    if(!dashBoard.lifelineDocuments.isEmpty()) {
                        Glide.with(itemView.context)
                            .load(dashBoard.lifelineDocuments.get(0))
                            .apply(RequestOptions.circleCropTransform())
                            .into(itemView.imageView);
                    }
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): DashViewHolder {

                val binding = DashboardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DashViewHolder(binding.root)
            }
        }
    }

    class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: State?) {
            itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
            itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val binding = ListFooterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                binding.txtError.setOnClickListener { retry() }
                return ListFooterViewHolder(binding.root)
            }
        }
    }
}