package com.symeonchen.wakeupscreen.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.symeonchen.wakeupscreen.R

class StatusItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var v: View? = null

    private var tvName: TextView? = null
    private var ivStatus: ImageView? = null
    private var mbNav: MaterialButton? = null

    private var name: String = ""
    private var status: Boolean = false

    var listener: OnItemClickListener? = null

    init {
        v = LayoutInflater.from(context).inflate(R.layout.view_item_list_main, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        tvName = v?.findViewById(R.id.main_tv_item_name)
        ivStatus = v?.findViewById(R.id.main_iv_item_status)
        mbNav = v?.findViewById(R.id.main_mb_item_nav)
        setListener()
    }

    private fun setListener() {
        this.isClickable = true
        this.isFocusable = true
        this.setOnClickListener {
            listener?.onItemClick()
        }
        mbNav?.setOnClickListener {
            listener?.onBtnClick()
        }
    }

    private fun refresh() {
        tvName?.text = name
        ivStatus?.setImageResource(if (status) R.drawable.ic_check_green_24dp else R.drawable.ic_close_red_24dp)
        mbNav?.visibility = if (status) View.INVISIBLE else View.VISIBLE
    }

    fun bindData(name: String?, status: Boolean = false) {
        this.name = name ?: ""
        this.status = status
        refresh()
    }

    fun setState(status: Boolean) {
        this.status = status
        refresh()
    }
}

interface OnItemClickListener {
    fun onItemClick()
    fun onBtnClick()
}

