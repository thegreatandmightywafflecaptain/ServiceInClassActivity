package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.os.Handler
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    val handler = Handler(Looper.getMainLooper()) {
        true
    }

    var timeBinder : TimerService.TimerBinder? = null

    val serviceConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timeBinder = service as TimerService.TimerBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timeBinder = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE)

    }

    override fun onDestroy(){
        super.onDestroy()
        unbindService(serviceConnection)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.start -> timeBinder?.start(20, handler)
        }
        when(item.itemId){
            R.id.pause -> timeBinder?.pause()
        }
        when(item.itemId){
            R.id.stop -> timeBinder?.stop()
        }
        return super.onOptionsItemSelected(item)
    }
}