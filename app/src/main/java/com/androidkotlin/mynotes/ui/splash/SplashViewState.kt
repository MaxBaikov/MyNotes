package com.androidkotlin.mynotes.ui.splash

import com.androidkotlin.mynotes.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null): BaseViewState<Boolean?>(authenticated, error)