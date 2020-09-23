package com.androidkotlin.mynotes.ui.main


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.ui.base.BaseActivity
import com.androidkotlin.mynotes.ui.note.NoteActivity
import com.androidkotlin.mynotes.ui.splash.SplashActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by viewModel()

    override val layoutRes: Int = R.layout.activity_main
    lateinit var adapter: NotesRVA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)


//        setSupportActionBar(toolbar)//Не забудьте поменять в Стилях приложения тему на Theme.AppCompat.Light.NoActionBar - поменял

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVA {
            NoteActivity.start(this, it.id)
        }

        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        when (item.itemId) {
//            R.id.logout -> showLogoutDialog().let { true }
//            else -> false
//        }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.confirm_exit))
            .setNegativeButton(getString(R.string.note_delete_cancel)){ dialog, which -> dialog.dismiss()}
            .setPositiveButton(getString(R.string.note_delete_ok)){ dialog, which ->
                AuthUI.getInstance().signOut(this).addOnCompleteListener{
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()}}
            .show()


//        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
//        LogoutDialog().show(supportFragmentManager, LogoutDialog.TAG)
    }

//    override fun onLogout() {
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener{
//                startActivity(Intent(this, SplashActivity::class.java))
//                finish()
//            }
//    }

}


