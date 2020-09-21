package com.androidkotlin.mynotes.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.androidkotlin.mynotes.ui.base.BaseActivity
import com.androidkotlin.mynotes.ui.base.BaseViewModel
import com.androidkotlin.mynotes.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

}