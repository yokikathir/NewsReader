package com.kathir.newsreader

import android.app.ProgressDialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kathir.newsreader.adapter.NewsAdapter
import com.kathir.newsreader.entity.Newsdb
import com.kathir.newsreader.network.ApiClient
import com.kathir.newsreader.network.ApiInterface
import com.kathir.newsreader.network.ArticleResponse
import com.kathir.newsreader.viewmodel.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by KATHIR on 12-01-2022.
 */
class MainActivity : AppCompatActivity() {

    var retailerDetail: MutableList<Newsdb> = ArrayList()
    var targetviewmodel: NewsViewModel? = null
    var adapter: NewsAdapter? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var searchitem: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchitem = findViewById(R.id.edit_search)
        swipeRefreshLayout = findViewById<View>(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        loading = ProgressDialog(this@MainActivity)
        loading!!.setCancelable(false)
        loading!!.setMessage("Loading.......")
        loading!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        loading!!.dismiss()
        recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)
        targetviewmodel = ViewModelProviders.of(this)[NewsViewModel::class.java]
        if (isNetworkConnected) {
            targetviewmodel!!.deleteAll()
            internetNews
        } else {
            Toast.makeText(this@MainActivity, "internet is disconnected", Toast.LENGTH_LONG).show()
            targetviewmodel!!.allProduct.observe(this@MainActivity, { targetLiveDataDBS ->
                retailerDetail = targetLiveDataDBS
                try {
                    Log.e("getalldata", "" + retailerDetail)
                    adapter = NewsAdapter(retailerDetail)
                    recyclerView!!.adapter = adapter
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
        swipinit()
        searchitem?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private val internetNews: Unit
        get() {
            loading!!.show()
            val apiInterface = ApiClient.getClient(this)?.create(
                ApiInterface::class.java
            )
            apiInterface?.topStories?.enqueue(object : Callback<List<Int?>?> {
                override fun onResponse(call: Call<List<Int?>?>, response: Response<List<Int?>?>) {
                    val topStories = response.body()
                    for (i in 0..99) {
                        apiInterface.getArticle(topStories?.get(i)!!)
                            ?.enqueue(object : Callback<ArticleResponse?> {
                                override fun onResponse(
                                    call: Call<ArticleResponse?>,
                                    response: Response<ArticleResponse?>
                                ) {
                                    if (response.body() != null) {
                                        val title = response.body()!!.title.toString()
                                        try {
                                            val news = Newsdb(title)
                                            targetviewmodel!!.insert(news)
                                            retailerDetail.add(news)
                                            val adapter = NewsAdapter(retailerDetail)
                                            recyclerView!!.adapter = adapter
                                            adapter.notifyDataSetChanged()
                                            loading!!.dismiss()
                                        } catch (e: Exception) {
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ArticleResponse?>, t: Throwable) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Something wrong",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    loading!!.dismiss()
                                }
                            })
                    }
                }

                override fun onFailure(call: Call<List<Int?>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Something wrong", Toast.LENGTH_LONG).show()
                    loading!!.dismiss()
                }
            })
        }

    private fun swipinit() {
        swipeRefreshLayout!!.setOnRefreshListener {
            if (isNetworkConnected) {
                targetviewmodel!!.deleteAll()
                swipeRefreshLayout!!.isRefreshing = false
                internetNews
            } else {
                Toast.makeText(this@MainActivity, "internet is disconnected", Toast.LENGTH_LONG)
                    .show()
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<Newsdb>()
        for (item in retailerDetail)
            if (item.desc.contains(text, ignoreCase = true))
                filteredList.add(item)
        adapter = NewsAdapter(filteredList)
        recyclerView!!.adapter = adapter
        adapter!!.filterList(filteredList)
    }

    private val isNetworkConnected: Boolean
        get() {
            val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }

    companion object {
        var loading: ProgressDialog? = null
    }

}