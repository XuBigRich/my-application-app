package cn.piao888.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.piao888.R
import cn.piao888.adapter.MainAdapter
import cn.piao888.data.bean.MainBean
import cn.piao888.ui.homePage.HomePageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    lateinit var mRefreshLayout: SwipeRefreshLayout
    lateinit var recycler: RecyclerView

    var dataSize: Int = 0
    var mData: ArrayList<MainBean> = ArrayList()
    val mAdapter: MainAdapter by lazy { MainAdapter() }
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        val toolbar = this.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar) //
        mRefreshLayout = this.findViewById(R.id.refresh_layout)
        recycler = this.findViewById(R.id.recycler)
        initData()
        onListener()
    }

    private fun onListener() {
        mRefreshLayout.setOnRefreshListener(this)
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            }


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    // 加载更多数据
                    generalData()
                    mAdapter.notifyDataSetChanged()
                }
            }
        })

    }


    private fun initData() {
        generalData()
        recycler.let {
            it.adapter = mAdapter
            //初始化数据
            mAdapter.datas = mData
            it.layoutManager = LinearLayoutManager(this)
        }
    }

    fun generalData() {
        for (i in 0..10) {
            var a = i % 3
            var photos = ArrayList<String>()
            for (b in 0..a) {
                photos.add("/storage/emulated/0/\$MuMu共享文件夹/$b.jpg")
            }
            dataSize++
            mData.add(MainBean(a, "我是第 $dataSize 个", photos))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_button_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_page -> {
                val intent = Intent(this, HomePageActivity::class.java);
                startActivity(intent)
            }

            R.id.forward -> println("forward")
            R.id.back -> println("back")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRefresh() {
        lifecycleScope.launch {
            mData.add(0, MainBean(0, "我是添加的", listOf("ss")))
            mAdapter.notifyDataSetChanged()
            withContext(Dispatchers.Main) {
                mRefreshLayout.isRefreshing = false
            }
        }
    }

}