package com.example.coroutinesflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    /*
    c)Flow основан на корутинах и лучше подходит, чем LiveData, для других слоев приложения,
     таких как - репозитории, источники данных и т.д.,
     они не завязаны на платформенные особенности Android и их будет легче тестировать.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragment_one, FirstFragment()).commit()
            fragmentManager.beginTransaction().replace(R.id.fragment_two, SecondFragment()).commit()
        }
    }
}