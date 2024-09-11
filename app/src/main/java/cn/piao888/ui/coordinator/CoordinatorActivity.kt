package cn.piao888.ui.coordinator

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cn.piao888.R
import cn.piao888.ui.coordinator.musicCollectionFragment.MusicCollectionFragment
import cn.piao888.ui.coordinator.musicFragment.MusicFragment
import cn.piao888.ui.titleBarActivity.coordinFragment.CoordinatorFragment
import cn.piao888.ui.titleBarActivity.coordinFragment.qqStepView.QQStepFragment
import cn.piao888.ui.titleBarActivity.coordinFragment.textView.TextFragment
import cn.piao888.ui.titleBarActivity.titleFragment.TitleBarFragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CoordinatorActivity : AppCompatActivity() {
    val imageView by lazy { findViewById<ImageView>(R.id.picture) }
    val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }
    val viewPage: ViewPager2 by lazy { findViewById<ViewPager2>(R.id.viewPage) }
    val fragmentNameList = arrayOf("歌曲", "专辑")
    val fragmentList = arrayOf(MusicFragment(), MusicCollectionFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        initData()
        onListener()
    }

    private fun initData() {
        viewPage.adapter = ViewPagerFragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = fragmentNameList[position] + "$position"  // 根据需要设置 Tab 的标题
        }.attach()
        val imageUrl =
            "https://bkimg.cdn.bcebos.com/pic/d1a20cf431adcbef76091cafaff839dda3cc7cd93159?x-bce-process=image/format,f_auto/watermark,image_d2F0ZXIvYmFpa2UyNzI,g_7,xp_5,yp_5,P_20/resize,m_lfit,limit_1,h_1080"
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun onListener() {

    }

    inner class ViewPagerFragmentAdapter(fm: FragmentActivity) :
        FragmentStateAdapter(fm) {
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}