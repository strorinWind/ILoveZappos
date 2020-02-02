package com.strorin.zappos.ui.main

import android.os.Bundle
import com.strorin.zappos.ui.history.TransactionGraphFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import com.strorin.zappos.R
import com.strorin.zappos.ui.settings.SettingsFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter


class MainActivity : MvpAppCompatActivity(), MainScreenView {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            showHistoryGraph()
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    override fun showState(state: MainScreenState) {
        when (state) {
            MainScreenState.HistoryGraph -> showHistoryGraph()
            MainScreenState.Settings -> showSettingsScreen()
        }
    }

    private fun showHistoryGraph() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, TransactionGraphFragment.newInstance())
            .commitNow()
    }


    private fun showSettingsScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SettingsFragment.newInstance())
            .commitNow()
    }


    private val bottomNavigationListener = object: BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId){
                R.id.graph -> presenter.onGraphClicked()
                R.id.settings -> presenter.onSettingsClicked()
                else -> return false
            }
            return true
        }
    }
}
