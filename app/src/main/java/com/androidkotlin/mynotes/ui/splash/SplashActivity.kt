package com.androidkotlin.mynotes.ui.splash


import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.androidkotlin.mynotes.ui.base.BaseActivity
import com.androidkotlin.mynotes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<Boolean?>() {

    override val viewModel: SplashViewModel by viewModel()

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