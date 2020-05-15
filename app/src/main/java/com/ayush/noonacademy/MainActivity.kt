package com.ayush.noonacademy

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ayush.noonacademy.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var i =1
    val subject = PublishSubject.create<Int>()
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subject.hide().firstOrError()
            .subscribe ( { Log.d("VALUE", "PRINT {$it}") } ,
                {
                    Log.d("VALUE", "PRINT {$it}")
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
