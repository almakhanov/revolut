package com.revolut.exrate.presentation.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.revolut.exrate.R
import com.revolut.exrate.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null
    private var firstTimeLoading = true

    private var listNeedToBeScrolledToTop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpAdapter()
    }

    private fun setUpViewModel() {
        viewModel = getViewModel()
        viewModel.errorLiveData.observe(this, errorMessageObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.rateList.observe(this, Observer {
            adapter.updateListItems(it)
            firstTimeLoading = false
            if (listNeedToBeScrolledToTop) {
                mRecyclerView.scrollToPosition(0)
                listNeedToBeScrolledToTop = false
            }
        })
        getRatePeriodically()
    }

    // I use Handler as it is cheap resource than AlarmManager, BroadcastReceiver
    private fun getRatePeriodically() {
        mHandler = Handler()
        mRunnable = Runnable {
            mHandler?.postDelayed(mRunnable, 1000)
            viewModel.getRate()
        }
        mRunnable?.run()
    }

    private fun setUpAdapter() {
        adapter = MainAdapter()
        adapter.clickListener = {
            viewModel.setBaseItem(it)
            listNeedToBeScrolledToTop = true
            mRecyclerView.scrollToPosition(0)
        }

        adapter.textChangeListener = { item, text ->
            viewModel.onItemValueChanged(item, text)
        }
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
    }


    override fun showProgress() {
        super.showProgress()
        if (firstTimeLoading) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        progressBar.visibility = View.GONE
    }
}
