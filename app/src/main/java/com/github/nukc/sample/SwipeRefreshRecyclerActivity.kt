package com.github.nukc.sample

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.nukc.stateview.StateView
import kotlin.math.roundToInt

/**
 * a example for SwipeRefreshLayout + RecyclerView
 */
class SwipeRefreshRecyclerActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private class ViewHolderImpl(v: View): RecyclerView.ViewHolder(v)

    private class Adapter: RecyclerView.Adapter<ViewHolderImpl>() {


        private val mDataSet = ArrayList<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderImpl {
            val tv = TextView(parent.context)
            tv.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, parent.context.resources.displayMetrics).roundToInt()
            )
            tv.gravity = Gravity.CENTER
            return ViewHolderImpl(tv)
        }

        override fun onBindViewHolder(holder: ViewHolderImpl, position: Int) {
            val tv = holder.itemView as TextView
            tv.text = mDataSet[position]
        }

        override fun getItemCount(): Int = mDataSet.size

        fun appendDataSet(dataSet: List<String>) {
            val from = mDataSet.size

            mDataSet.addAll(dataSet)
            notifyItemRangeInserted(from, dataSet.size)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_refresh_recycler)


        mSwipeRecyclerView = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh).apply {
            setOnRefreshListener(this@SwipeRefreshRecyclerActivity)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            addItemDecoration(DividerItemDecoration(this@SwipeRefreshRecyclerActivity, DividerItemDecoration.HORIZONTAL))
            adapter = mAdapter

        }

        // start refreshing
        mSwipeRecyclerView.isRefreshing = true
        onRefresh()

        // important:
        // we inject into RecyclerView after onResume()
        // instead of onCreate(), then the bug occurred !!!

        mSwipeRecyclerView.post {
            mStateView = StateView.inject(recyclerView)
            mStateView.onRetryClickListener = object : StateView.OnRetryClickListener {
                override fun onRetryClick() {
                    mSwipeRecyclerView.isRefreshing = true
                    onRefresh()
                }
            }
        }
    }

    private val mHandler = Handler(Looper.getMainLooper())

    private val mAdapter = Adapter()

    private lateinit var mStateView: StateView
    private lateinit var mSwipeRecyclerView: SwipeRefreshLayout


    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

    private var mStateId = 0

    override fun onRefresh() {
        // success or failure ?

        if ((++mStateId and 1) == 1) {
            // success
            mHandler.postDelayed(1000) {

                var idx = 0
                val newDataSet = Array(10) { idx++.toString() }

                mAdapter.appendDataSet(newDataSet.asList())
                mStateView.showContent()
                mSwipeRecyclerView.isRefreshing = false
            }
        }
        else {
            // ops :(
            mHandler.postDelayed(1000) {
                mStateView.showRetry()
                mSwipeRecyclerView.isRefreshing = false
            }
        }
    }
}

fun Handler.postDelayed(time: Long, action: Runnable) {
    postDelayed(action, time)
}