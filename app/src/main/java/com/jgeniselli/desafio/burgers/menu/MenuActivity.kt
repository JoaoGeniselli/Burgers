package com.jgeniselli.desafio.burgers.menu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jgeniselli.desafio.burgers.R
import com.jgeniselli.desafio.burgers.commons.replaceFragment

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        if (savedInstanceState == null)
            replaceFragment(MenuFragment(), R.id.frame)
    }
}
