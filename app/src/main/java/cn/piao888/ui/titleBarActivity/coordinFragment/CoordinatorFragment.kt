package cn.piao888.ui.titleBarActivity.coordinFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import cn.piao888.R
import cn.piao888.ui.titleBarActivity.coordinFragment.qqStepView.QQStepFragment
import cn.piao888.ui.titleBarActivity.coordinFragment.textView.TextFragment
import cn.piao888.ui.titleBarActivity.titleFragment.TitleBarFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CoordinatorFragment : Fragment() {
    lateinit var toolbar: Toolbar
    lateinit var viewPage: ViewPager2
    lateinit var tabLayout: TabLayout
    val fragmentList = arrayOf(TextFragment(), QQStepFragment())
    val fragmentNameList = arrayOf("文字Fragment", "计步器Fragment")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mView = inflater.inflate(R.layout.fragment_coordinator, container, false)

        mView?.let {
            toolbar = it.findViewById(R.id.toolbar)
            tabLayout = it.findViewById(R.id.tabLayout)
            viewPage = it.findViewById(R.id.viewPage)
            viewPage.adapter = ViewPagerFragmentAdapter(requireActivity())
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.title = "自定义 One"
            TabLayoutMediator(tabLayout, viewPage) { tab, position ->
                tab.text = fragmentNameList[position]+"$position"  // 根据需要设置 Tab 的标题
            }.attach()
        }
        return mView
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