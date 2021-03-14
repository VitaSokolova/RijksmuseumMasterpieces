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
        openMasterpiecesListFragment()
    }

    private fun openMasterpiecesListFragment() {
        supportFragmentManager.commit {
            replace<MasterpiecesListFragment>(R.id.fragment_container)
            addToBackStack(MasterpiecesListFragment.NAME)
        }
    }
}