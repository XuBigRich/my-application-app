package cn.piao888.ui.homePage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.piao888.ui.main.MainActivity
import cn.piao888.R
import cn.piao888.ui.coordinator.CoordinatorActivity
import cn.piao888.ui.flowLayout.FlowLayoutActivity
import cn.piao888.ui.pageView.PageViewActivity
import cn.piao888.ui.titleBarActivity.TitleBarActivity

class HomePageActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var pageViewButton: Button
    lateinit var adapterButton: Button
    lateinit var titleButton: Button
    lateinit var flowLayoutButton: Button
    val coordinatorButton by lazy {
        findViewById<Button>(R.id.coordinatorButton)
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_home_page)
        val toolbar = this.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        pageViewButton = this.findViewById(R.id.pageViewButton)
        adapterButton = this.findViewById(R.id.adapterButton)
        titleButton = this.findViewById(R.id.titleBarButton)
        flowLayoutButton = this.findViewById(R.id.flowLayoutButton)
        onListener()
    }

    private fun onListener() {
        pageViewButton.setOnClickListener(this)
        adapterButton.setOnClickListener(this)
        titleButton.setOnClickListener(this)
        flowLayoutButton.setOnClickListener(this)
        coordinatorButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pageViewButton -> {
                val pageViewIntent = Intent(this, PageViewActivity::class.java)
                startActivity(pageViewIntent)
            }

            R.id.adapterButton -> {
                val adapterIntent = Intent(this, MainActivity::class.java)
                startActivity(adapterIntent)
            }

            R.id.titleBarButton -> {
                val titleBarActivity = Intent(this, TitleBarActivity::class.java)
                startActivity(titleBarActivity)
            }

            R.id.flowLayoutButton -> {
                val flowLayout = Intent(this, FlowLayoutActivity::class.java)
                startActivity(flowLayout)
            }

            R.id.coordinatorButton -> {
                val coordinatorActivity = Intent(this, CoordinatorActivity::class.java)
                startActivity(coordinatorActivity)
            }
        }


    }
}