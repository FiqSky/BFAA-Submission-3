package com.fiqsky.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.db.MappingHelper
import com.fiqsky.githubuserapp.db.UserHelper
import com.fiqsky.githubuserapp.ui.adapter.UserAdapter
import com.fiqsky.githubuserapp.utils.User
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var helper: UserHelper
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

            //Inisialisasi class user helper
            helper = UserHelper.getInstance(applicationContext)
            helper.open()

            initRecyclerView()

            //Mendapatkan hasil list query dan ditampilkan ke recyclerview
            loadUsersAsync()
        }

        private fun initRecyclerView() {
            adapter = UserAdapter(onClick = { user: User ->
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra(InfoActivity.EXTRA_USER, user)
                startActivity(intent)
            }, onLongClick = { user, position ->
                deleteUser(user, position)
            })
            rv_favorite.layoutManager = LinearLayoutManager(this)
            rv_favorite.adapter = adapter
        }

        private fun deleteUser(user: User, position: Int) {
            val result = helper.deleteByUsername(username = user.userName)
            if (result > 0) {
                adapter.removeItem(position)
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_LONG).show()
            }
        }

        private fun loadUsersAsync() {
            GlobalScope.launch(Dispatchers.Main) {
                progress_bar_fav.visibility = View.VISIBLE
                val deferredNotes = async(Dispatchers.IO) {
                    //Get data query dari tabel
                    val cursor = helper.queryAll()
                    MappingHelper().mapCursorToArrayList(cursor)
                }
                progress_bar_fav.visibility = View.INVISIBLE
                //Hasil dari query: list user
                val notes: ArrayList<User> = deferredNotes.await()
                addUsersToAdapter(notes)
            }
        }

        private fun addUsersToAdapter(notes: ArrayList<User>) {
            when {
                notes.isNotEmpty() -> {
                    adapter.addAll(notes)
                }
                else -> {
                    adapter.addAll(emptyList())
                    Toast.makeText(
                        this@FavoriteActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            helper.close()
        }
    }