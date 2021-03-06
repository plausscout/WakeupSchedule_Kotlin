package com.suda.yzune.wakeupschedule.settings.view_binder

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.suda.yzune.wakeupschedule.R
import com.suda.yzune.wakeupschedule.settings.bean.SeekBarItem
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.*

class SeekBarItemViewBinder constructor(private val onSeekValueChange: (SeekBarItem, Int) -> Unit) : ItemViewBinder<SeekBarItem, SeekBarItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SeekBarItemViewBinder.ViewHolder {
        val view = AnkoContext.create(parent.context).apply {
            verticalLayout {
                lparams(matchParent, wrapContent)

                linearLayout {
                    textView {
                        id = R.id.anko_text_view
                        textColor = Color.BLACK
                        textSize = 16f
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(0, matchParent) {
                        marginStart = dip(16)
                        marginEnd = dip(16)
                        weight = 1f
                    }

                    textView {
                        id = R.id.anko_tv_value
                        textSize = 12f
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(wrapContent, matchParent)

                    textView {
                        id = R.id.anko_tv_unit
                        textSize = 12f
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(wrapContent, matchParent) {
                        marginStart = dip(8)
                        marginEnd = dip(16)
                    }
                }.lparams(matchParent, wrapContent) {
                    topMargin = dip(16)
                }

                seekBar {
                    id = R.id.anko_seek_bar
                }.lparams(matchParent, wrapContent) {
                    marginStart = dip(8)
                    marginEnd = dip(8)
                    topMargin = dip(8)
                    bottomMargin = dip(16)
                }
            }
        }.view
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeekBarItemViewBinder.ViewHolder, item: SeekBarItem) {
        //holder.setIsRecyclable(false)

        holder.tvTitle.text = item.title
        holder.seekBar.progress = item.valueInt - item.min
        holder.seekBar.max = item.max - item.min
        holder.tvValue.text = item.valueInt.toString()
        holder.tvUnit.text = item.unit
        holder.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    onSeekValueChange.invoke(item, progress)
                    holder.tvValue.text = "${progress + item.min}"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.find(R.id.anko_text_view)
        val tvUnit: TextView = itemView.find(R.id.anko_tv_unit)
        val tvValue: TextView = itemView.find(R.id.anko_tv_value)
        val seekBar: SeekBar = itemView.find(R.id.anko_seek_bar)
    }
}