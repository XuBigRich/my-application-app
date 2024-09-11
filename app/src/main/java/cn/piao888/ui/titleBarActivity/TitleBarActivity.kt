package cn.piao888.ui.titleBarActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.piao888.R
import cn.piao888.ui.titleBarActivity.coordinFragment.CoordinatorFragment
import cn.piao888.ui.titleBarActivity.titleFragment.TitleBarFragment
import com.google.android.material.tabs.TabLayout

class TitleBarActivity : AppCompatActivity() {
    val viewPage by lazy { findViewById<ViewPager>(R.id.viewPage) }
    val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }
    val fragmentList = arrayOf(TitleBarFragment(), CoordinatorFragment())
    val fragmentNameList = arrayOf("自定义Title", "原始AppBarLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_bar)
        var fragmentManager = getSupportFragmentManager()
        viewPage.adapter = ViewPagerFragmentAdapter(fragmentManager)
        tabLayout.setupWithViewPager(viewPage)
        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view_tag, TitleBarFragment())
//            .add(R.id.fragment_container_view_tag, CoordinatorFragment())
            .add(R.id.fragment_Linear_container_view_tag, TitleBarFragment())
            //不会覆盖上面的
            .add(R.id.fragment_Linear_container_view_tag, CoordinatorFragment())
            .commit()
    }


    inner class ViewPagerFragmentAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return fragmentList.count()
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return fragmentNameList[position]
        }
    }
}
