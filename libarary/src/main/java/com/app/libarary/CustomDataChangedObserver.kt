package com.app.libarary

import androidx.recyclerview.widget.RecyclerView

class CustomDataChangedObserver : RecyclerView.AdapterDataObserver() {

    private var onChangedListener : (() -> Unit)? = null

    fun setOnChangedListener(onChanged : () -> Unit){
        this.onChangedListener = onChanged
    }

    override fun onChanged() {
        super.onChanged()
        this.onChangedListener?.invoke()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
        this.onChangedListener?.invoke()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        super.onItemRangeChanged(positionStart, itemCount, payload)
        this.onChangedListener?.invoke()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        this.onChangedListener?.invoke()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        this.onChangedListener?.invoke()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount)
        this.onChangedListener?.invoke()
    }
}