package com.fiqsky.customerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fiqsky.customerapp.db.MappingHelper
import com.fiqsky.customerapp.utils.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        initThreadObserver()

        if (savedInstanceState == null) {
            loadUserAsync()
        }
    }

    private fun initThreadObserver() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUserAsync()
            }
        }
        contentResolver.registerContentObserver(
            CONTENT_URI,
            true,
            myObserver
        )
    }

    private fun initRecyclerView() {
        adapter = UserAdapter(onClick = { user: User ->
            Toast.makeText(this, user.name, Toast.LENGTH_LONG).show()
        })
        rv_main_customer.layoutManager = LinearLayoutManager(this)
        rv_main_customer.adapter = adapter
    }

    private fun addUsersToAdapter(users: ArrayList<User>) {
        when {
            users.isNotEmpty() -> {
                adapter.addAll(users)
            }
            else -> {
                adapter.addAll(emptyList())
                Toast.makeText(this, "Tidak ada data saat ini", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.IO) {

            val deferredUser = async(Dispatchers.IO) {
                val cursor = this@MainActivity.contentResolver.query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val users = deferredUser.await()
            runOnUiThread {
                addUsersToAdapter(users)
            }

        }
    }
}
