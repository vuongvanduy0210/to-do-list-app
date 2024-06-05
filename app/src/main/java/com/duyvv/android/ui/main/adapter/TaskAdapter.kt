package com.duyvv.android.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.duyvv.android.R
import com.duyvv.android.databinding.ItemNotFoundBinding
import com.duyvv.android.databinding.ItemTaskBinding
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.util.app.FormatUtils

class TaskAdapter(
    private val context: Context,
    private val onClickItemTask: (Task) -> Unit,
    private val onClickOptions: (Task, View) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Task>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Task>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                return TaskViewHolder(
                    ItemTaskBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class NotFoundViewHolder(binding: ItemNotFoundBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): NotFoundViewHolder {
                return NotFoundViewHolder(
                    ItemNotFoundBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> TaskViewHolder.create(parent)
            VIEW_TYPE_NOT_FOUND -> NotFoundViewHolder.create(parent)
            else -> NotFoundViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            val task = items[position]
            if (holder is TaskViewHolder) {
                holder.binding.apply {
                    tvTitle.text = task.title
                    tvDescription.text = task.description
                    tvDayOfWeek.text = FormatUtils.getDayOfWeekFromCalendar(task.time)
                    tvDay.text = FormatUtils.getDayFromCalendar(task.time)
                    tvMonth.text = FormatUtils.getMonthFromCalendar(task.time)
                    tvTime.text = FormatUtils.convertDateToHourAndMinute(task.time)
                    tvStatus.text = task.status.des
                    tvStatus.setTextColor(
                        if (task.status == TaskStatus.COMPLETED) {
                            ContextCompat.getColor(context, R.color.blue_dark)
                        } else {
                            Color.RED
                        }
                    )

                    btnOptions.setOnClickListener {
                        onClickOptions.invoke(task, it)
                    }

                    layoutItemTask.setOnClickListener {
                        onClickItemTask.invoke(task)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) 1 else items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) VIEW_TYPE_NOT_FOUND else VIEW_TYPE_ITEM
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_NOT_FOUND = 1
    }
}
