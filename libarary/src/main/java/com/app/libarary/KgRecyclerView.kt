package com.app.libarary

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class KgRecyclerView @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, private val defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val recycler: RecyclerView
    private val loadingView: ProgressBar
    private val noData: LinearLayoutCompat
    private var onDataChangedObserver: CustomDataChangedObserver? = null
    private var isLoading = false
    private var text: AppCompatTextView? = null
    private var icon: AppCompatImageView? = null

    init {
        layoutSettings()

        recycler = initRecyclerview()
        addView(recycler)

        noData = initNoDataView()
        initAttrs()
        addView(noData)

        loadingView = initLoadingView()
        addView(loadingView)

    }

    private fun layoutSettings() {
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }

    private fun initAttrs() {
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.AdvanceRecyclerView, defStyleAttr, 0)

        val noDataIconId = ta.getResourceId(R.styleable.AdvanceRecyclerView_noDataIconId, -1)
        val noDataText = ta.getString(R.styleable.AdvanceRecyclerView_noDataText)
        val noDataTextColor = ta.getColor(R.styleable.AdvanceRecyclerView_noDataTextColor, -1)

        if (noDataIconId != -1)
            setNoDataIcon(noDataIconId)

        if (noDataText != null) {
            setNoDataText(noDataText, if (noDataTextColor == -1) Color.BLACK else noDataTextColor)
        }

        ta.recycle()
    }

    private fun initRecyclerview(): RecyclerView {
        return RecyclerView(context).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0.dp, 1f)
            hide()
        }
    }

    private fun initNoDataView(): LinearLayoutCompat {
        return LinearLayoutCompat(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            orientation = VERTICAL
            gravity = Gravity.CENTER

            hide()
        }
    }

    fun setNoDataIcon(iconId: Int) {
        if (this.icon == null) {
            this.icon = AppCompatImageView(context).apply {
                setImageResource(iconId)
                layoutParams = LayoutParams(64.dp, 64.dp)
            }
            noData.addView(this.icon, 0)
        } else {
            this.icon?.setImageResource(iconId)
        }
    }

    fun setNoDataText(txt: String, textColor: Int = Color.BLACK, typeface: Typeface? = null) {
        if (this.text == null) {
            this.text = AppCompatTextView(context).apply {
                layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = txt
                setPadding(16.dp, 16.dp, 16.dp, 16.dp)
                setTextColor(textColor)
                textSize = 16.sp.toFloat()
                if (typeface != null) {
                    setTypeface(typeface)
                }
            }
            noData.addView(this.text)
        } else {
            this.text?.text = txt
            this.text?.setTextColor(textColor)
        }
    }

    private fun initLoadingView(): ProgressBar {
        return ProgressBar(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            isIndeterminate = true
            show()
        }
    }


    fun setHasFixedSize(fix: Boolean) {
        recycler.setHasFixedSize(fix)
    }

    fun setNestedScrolling(enable: Boolean) {
        recycler.isNestedScrollingEnabled = enable
    }

    fun isAnimationEnable(isEnable: Boolean) {
        (recycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = isEnable
    }

    fun setAnimation(animResId : Int){
        recycler.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(context ,animResId))
    }

    fun setLayoutManager(lm: RecyclerView.LayoutManager = LinearLayoutManager(context)) {
        recycler.layoutManager = lm
    }

    fun getLayoutManager() = recycler.layoutManager

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        if (recycler.adapter != null) {
            recycler.adapter?.unregisterAdapterDataObserver(onDataChangedObserver!!)
        }
        onDataChangedObserver = CustomDataChangedObserver()
        adapter.registerAdapterDataObserver(onDataChangedObserver!!.apply {
            setOnChangedListener {
                dataChanged()
            }
        })
        recycler.adapter = adapter
        dataChanged()
    }

    fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? = recycler.adapter

    private fun dataChanged() {
        isLoading = false
        if (getAdapter()?.itemCount != 0) {
            noData.hide()
            loadingView.hide()
            recycler.show()

        } else {
            recycler.hide()
            loadingView.hide()
            noData.show()
        }
    }

    fun enableEndlessRecyclerView(atEnd: () -> Unit) {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && !isLoading &&
                    (recyclerView.adapter?.itemCount ?: 0) >= (getAdapter()!!.itemCount - 3)
                ) {
                    isLoading = true
                    noData.hide()
                    loadingView.show()
                    recyclerView.post {
                        atEnd()
                    }
                }
            }
        })
    }

    fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
        recycler.addOnScrollListener(listener)
    }

    fun removeOnScrollListener(listener: RecyclerView.OnScrollListener) {
        recycler.removeOnScrollListener(listener)
    }

    // Activity | Fragment Destroyed
    override fun onDetachedFromWindow() {
        recycler.adapter?.unregisterAdapterDataObserver(onDataChangedObserver!!)
        super.onDetachedFromWindow()
    }
}