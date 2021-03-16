package com.rijksmuseum.masterpieces.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.features.list.MasterpiecesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentByTag(MasterpiecesListFragment.NAME)
        if (fragment == null) {
            openMasterpiecesListFragment()
        }
    }

    override fun onBackPressed() {
        val fragmentCount = supportFragmentManager.backStackEntryCount
        when {
            fragmentCount == 1 -> finish()
            fragmentCount > 1 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }

    private fun openMasterpiecesListFragment() {
        supportFragmentManager.commit {
            replace<MasterpiecesListFragment>(
                R.id.fragment_container,
                MasterpiecesListFragment.NAME
            )
            addToBackStack(MasterpiecesListFragment.NAME)
        }
    }
}