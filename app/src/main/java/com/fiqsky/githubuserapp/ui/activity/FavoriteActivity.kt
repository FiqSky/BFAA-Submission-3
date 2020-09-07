package com.fiqsky.githubuserapp.ui.activity

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fiqsky.githubuserapp.db.MappingHelper
import com.fiqsky.githubuserapp.db.UserHelper
import com.fiqsky.githubuserapp.ui.adapter.UserAdapter
import com.fiqsky.githubuserapp.utils.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var helper: UserHelper
    private lateinit var adapter: UserAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        title = getString(R.string.favorite_users)

//            Initialisation class user helper
            helper = UserHelper.getInstance(applicationContext)
            helper.open()

            initRecyclerView()

            //Mendapatkan hasil list query dan ditampilkan ke recyclerview
//            loadUsersAsync()
            /*val handlerThread = HandlerThread("DataObserver")
            handlerThread.start()
            val handler = Handler(handlerThread.looper)

            val myObserver = object : ContentObserver(handler){
                override fun onChange(selfChange: Boolean) {
                    loadUsersAsync()
                }
            }*/

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
//                loadNotesAsync()
                loadUsersAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
//            loadNotesAsync()
            loadUsersAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)
            if (list != null) {
                adapter.listFav = list
            }
        }

        }

        private fun initRecyclerView() {
            adapter = UserAdapter(onClick = { user: User ->
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra(InfoActivity.EXTRA_USER, user)
                startActivity(intent)
            }, onLongClick = { user, position ->
                deleteUser(user, position)
            })
//            adapter = UserAdapter()
            rv_favorite.layoutManager = LinearLayoutManager(this)
            rv_favorite.hasFixedSize()
            rv_favorite.adapter = adapter
        }

        /*private fun deleteUser(user: User, position: Int) {
            val result = helper.deleteByUsername(username = user.userName)
            if (result > 0) {
                adapter.removeItem(position)
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_LONG).show()
            }
        }*/

        private fun loadUsersAsync() {
            GlobalScope.launch(Dispatchers.Main) {
                progress_bar_fav.visibility = View.VISIBLE
                val deferredNotes = async(Dispatchers.IO) {
                    //Get data query dari tabel
                    val cursor = helper.queryAll()
//                    val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                    MappingHelper.mapCursorToArrayList(cursor)
                }
                progress_bar_fav.visibility = View.INVISIBLE
                //Hasil dari query: list user
                val notes = deferredNotes.await()
                addUsersToAdapter(notes)
                /*if (notes.size > 0) {
                    adapter.list = notes
                } else {
                    adapter.list = ArrayList()
                    Toast.makeText(
                        this@FavoriteActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }*/
            }
        }

        private fun addUsersToAdapter(notes: ArrayList<User>) {
            when {
                notes.isNotEmpty() -> {
                    adapter.addAll(notes)
                }
                else -> {
                    adapter.addAll(notes)
                    Toast.makeText(
                        this@FavoriteActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    /*override fun onSaveInstanceState(outState: Bundle, result: List<User>) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFav)
    }*/

    override fun onResume() {
        super.onResume()
        loadUsersAsync()
    }
    /*override fun onResume() {
        super.onResume()
        helper.close()
    }*/
        /*override fun onDestroy() {
            super.onDestroy()
            helper.close()
        }*/
    private fun deleteUser(user: User, position: Int) {
        val result = helper.deleteByUsername(username = user.userName)
        if (result > 0) {
            adapter.removeItem(position)
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_LONG).show()
        }
    }
}