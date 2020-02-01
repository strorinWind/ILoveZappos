package com.strorin.zappos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strorin.zappos.ui.main.TransactionGraphFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TransactionGraphFragment.newInstance())
                    .commitNow()
        }
    }

}
