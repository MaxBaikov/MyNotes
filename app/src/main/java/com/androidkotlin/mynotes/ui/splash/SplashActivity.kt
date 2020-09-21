package com.androidkotlin.mynotes.ui.splash


import android.os.Handler
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
        Handler().postDelayed({viewModel.requestUser()}, 1000)
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