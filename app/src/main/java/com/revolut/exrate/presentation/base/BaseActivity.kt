package com.revolut.exrate.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.revolut.exrate.R
import com.revolut.exrate.presentation.extensions.alert

abstract class BaseActivity : AppCompatActivity() {

    var layoutId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId?.let {
            setContentView(it)
        }
    }


    fun showFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.slide_right_in, R.anim.slide_left_out,
            R.anim.slide_left_in, R.anim.slide_right_out)
//        transaction.replace(R.id.fragmentContainer, fragment, tag).addToBackStack(null).commit()
    }

    open fun showProgress() {
        // do nothing
    }

    open fun hideProgress() {
        // do nothing
    }

    open fun success() {
        // do nothing
    }

    open fun onError(message: String) {
        // do nothing
    }

    protected open val statusObserver = Observer<Status> {
        it?.let {
            when (it) {
                Status.SHOW_LOADING -> showProgress()
                Status.HIDE_LOADING -> hideProgress()
                Status.SUCCESS -> success()
            }
        }
    }

    protected val errorMessageObserver = EventObserver<String> {
        this.alert(message = it)
        onError(it)
    }


}