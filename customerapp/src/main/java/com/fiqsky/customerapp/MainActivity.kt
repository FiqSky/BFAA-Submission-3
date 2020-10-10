package com.fiqsky.customerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fiqsky.customerapp.db.MappingHelper
import com.fiqsky.customerapp.db.UserHelper
import com.fiqsky.customerapp.utils.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

//    private lateinit var helper: UserHelper
    private lateinit var adapter: UserAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*helper = UserHelper.getInstance(applicationContext)
        helper.open()*/

        initRecyclerView()

        initThreadObserver()

        if (savedInstanceState == null) {
            loadUserAsync()
        }else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFav = list
            }
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
            Toast.makeText(this, user.userName, Toast.LENGTH_LONG).show()
        })
        rv_main_customer.layoutManager = LinearLayoutManager(this)
        rv_main_customer.adapter = adapter
        rv_main_customer.hasFixedSize()
    }

    private fun addUsersToAdapter(users: ArrayList<User>) {
        when {
            users.isNotEmpty() -> {
                adapter.addAll(users)
//                Log.d(EXTRA_STATE, "addUsersToAdapter: " )
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
//                val cursor = helper.queryAll()
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
