package com.fiqsky.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.api.ApiClient
import com.fiqsky.githubuserapp.db.DatabaseContract
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.ID
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.USERNAME
//import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.NAME
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
/*import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.BLOG*/
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fiqsky.githubuserapp.db.MappingHelper
/*import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.REPO
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWER
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWING*/
import com.fiqsky.githubuserapp.db.UserHelper
import com.fiqsky.githubuserapp.ui.adapter.SectionAdapter
import com.fiqsky.githubuserapp.ui.adapter.UserAdapter
import com.fiqsky.githubuserapp.ui.fragment.FollowingFragment
import com.fiqsky.githubuserapp.utils.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.btn_fav.*
import kotlinx.android.synthetic.main.desc_user.*
import kotlinx.android.synthetic.main.info_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class InfoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "user"
    }

    private var isFavorite = false

    //    private lateinit var id : String
    private lateinit var favouriteList: ArrayList<User>
    private lateinit var adapter: SectionAdapter
    private lateinit var uAdapter: UserAdapter
    private lateinit var helper: UserHelper
    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        helper = UserHelper.getInstance(applicationContext)
        helper.open()
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val userName = user.userName ?: ""
        getDetail(userName)

        title = userName


        adapter = SectionAdapter(supportFragmentManager)
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)

        /*uriWithId = Uri.parse("$CONTENT_URI/$userName")
        val userFav = contentResolver.query(uriWithId, null, null, null, null)*/
        checkFavorite(user)
        /*val cursor: Cursor = helper.queryById(userName)
        if (cursor.moveToNext()) {
            isFavorite = true
            setIsFavorite(isFavorite)
        }*/

        isFavorite = favouriteList.indexOf(user) >= 0
        Log.d("status",isFavorite.toString())
        setIsFavorite(isFavorite)

        btn_favorite.setOnClickListener {
            addToFavorite(user)
        }
    }

    private fun setIsFavorite(isFavorite: Boolean) {
        val check = R.string.add_to_favorite
        val checked = R.string.delete_from_favorite
        if (isFavorite) {
            btn_favorite.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            btn_favorite.setText(checked)
        } else {
            btn_favorite.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorPrimaryDark))
            btn_favorite.setText(check)
        }
    }

    /*private fun checkFavorite(favCursor: Cursor?) {
        val favObject = MappingHelper.mapCursorToArrayList(favCursor)
        for(data in favObject){
            if(this.user?.id == data.id){
                Log.d("ini", "cekFav favObject: $favObject")
                Log.d("inisih", "cekData data: $data")
                isFavorite= true
                Log.d("inikan", "cekData fav: $isFavorite")
            }
        }
    }*/

    private fun checkFavorite(user: User){
        favouriteList = MappingHelper.mapCursorToArrayList(helper.queryByUsername(user.userName))
        Log.d("checkfavourite", favouriteList.toString())
    }

    private fun getFollowers(userName: String, title: String) {
        val call = ApiClient.service.getFollowers(userName)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>, response: Response<List<User>>
            ) {
                Log.d("message", "onResponse: " + response.body())
                if (response.isSuccessful) {
                    val list = ArrayList(response.body().orEmpty())
                    adapter.addFragment(FollowingFragment.newInstance(list), title)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("message", "onFailure: " + t.message)
            }

        })
    }

    private fun getFollowing(userName: String, title: String) {
        val call = ApiClient.service.getFollowing(userName)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.d("message", "onResponse: " + response.body())
                val list = ArrayList(response.body().orEmpty())
                adapter.addFragment(FollowingFragment.newInstance(list), title)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("message", "onFailure: " + t.message)
            }

        })

    }

    private fun getDetail(userName: String) {
        val call = ApiClient.service.getDetail(userName)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("message", "onResponse: " + response.body())
                if (response.isSuccessful) {
                    val user = response.body()
                    initDetailUser(user)
                    getFollowing(userName, "${user?.totalFollowing} Following")
                    getFollowers(userName, "${user?.totalFollowers} Followers")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("message", "onFailure: " + t.message)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailUser(user: User?) {
        txt_repo.text = user?.publicRepos.toString()
        txt_followers.text = user?.totalFollowers.toString()
        txt_followings.text = user?.totalFollowing.toString()

        if (user?.name != null) {
            txt_name.text = user.name
        } else {
            txt_name.text = getString(R.string.unknown)
        }

        if (user?.location != null) {
            txt_location.text = user.location
        } else {
            txt_location.text = getString(R.string.nope)
        }

        if (user?.company != null) {
            txt_work.text = user.company
        } else {
            txt_work.text = getString(R.string.nope)
        }

        if (user?.blog != null) {
            val url = user.blog
            txt_link.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            txt_link.text = url
        } else {
            txt_link.text = getString(R.string.nope)
        }

        Picasso.get()
            .load(user?.avatarUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.color.design_default_color_error)
            .into(img_avatar)
    }

    /*private fun addToFavorite(user: User?) {
        if (user != null) {
            //Inisialisasi content values
            val values = ContentValues()
            values.put(ID, user.id)
            values.put(USERNAME, user.userName)
            values.put(AVATAR_URL, user.avatarUrl)

            //Panggil method insert dari helper
            helper.insert(values)
            showResult()
            isFavorite = !isFavorite
            setIsFavorite(isFavorite)
//            addButton(state = true)
        } else {
            val userName = user?.userName.toString()
            val result = helper.deleteByUsername(userName)
            uriWithId = Uri.parse("$CONTENT_URI/$USERNAME")
            contentResolver.delete(uriWithId, null, null)
            isFavorite = !isFavorite
            setIsFavorite(isFavorite)
            showResultRemove(result)
        }
    }*/
    private fun addToFavorite(user: User?) {
        if (!isFavorite) {
            //Inisialisasi content values
            val values = ContentValues()
            values.put(ID, user?.id)
            values.put(USERNAME, user?.userName)
            values.put(AVATAR_URL, user?.avatarUrl)

            //Panggil method insert dari helper
            val result = helper.insert(values)
            showResult(result)
            isFavorite = !isFavorite
            setIsFavorite(isFavorite)
//            addButton(state = true)
        } else {
            val userName = user?.userName.toString()
            val result = helper.deleteByUsername(userName)
//            uriWithId = Uri.parse("$CONTENT_URI/$USERNAME")
//            contentResolver.delete(uriWithId, null, null)
            isFavorite = !isFavorite
            setIsFavorite(isFavorite)
            showResultRemove(result)
        }
    }

    private fun showResult(result: Long) {
        when {
            result > 0 -> {
                Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.close()
    }

    private fun showResultRemove(result: Int) {
        when {
            result > 0 -> {
                Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}