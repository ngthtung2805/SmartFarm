package com.smartfarm.smartfarm

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mDatabase:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mDatabase = FirebaseDatabase.getInstance().getReference()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        fetchDevice()
        handleSwitch()

    }

    fun fetchDevice(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                var state1 = dataSnapshot.child("controller").child("states").child("001").value
                if(state1== true){
                    switch1.isChecked = true
                }else{
                    switch1.isChecked = false
                }
                var switch002 = dataSnapshot.child("controller").child("states").child("002").value
                if(switch002 == true){
                    switch2.isChecked = true
                }else{
                    switch2.isChecked = false
                }
                var switch003 = dataSnapshot.child("controller").child("states").child("003").value
                if(switch003 == true){
                    switch3.isChecked = true
                }else{
                    switch3.isChecked = false
                }

                //handle title
                var title1 = dataSnapshot.child("controller").child("title").child("001").value
                switch1.text = title1.toString()
                var title2 = dataSnapshot.child("controller").child("title").child("002").value
                switch2.text = title2.toString()
                var title3 = dataSnapshot.child("controller").child("title").child("003").value
                switch3.text = title3.toString()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        mDatabase.addValueEventListener(postListener)
    }
    fun handleSwitch(){
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                mDatabase.child("controller").child("states").child("001").setValue(true)
            }else{
                mDatabase.child("controller").child("states").child("001").setValue(false)
            }
        }
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                mDatabase.child("controller").child("states").child("002").setValue(true)
            }else{
                mDatabase.child("controller").child("states").child("002").setValue(false)
            }
        }
        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                mDatabase.child("controller").child("states").child("003").setValue(true)
            }else{
                mDatabase.child("controller").child("states").child("003").setValue(false)
            }
        }
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
