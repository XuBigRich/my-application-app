package cn.piao888.ui.pageView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import cn.piao888.R
import com.google.android.material.tabs.TabLayout

class PageViewActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPage: ViewPager
    var pages: MutableList<View> = ArrayList()
    val res = arrayOf(R.drawable.img, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_page_view)
        tabLayout = this.findViewById(R.id.tab_layout)
        viewPage = this.findViewById(R.id.viewPage)
        initView()
        viewPage.adapter = ViewPagerAdapter()
        tabLayout.setupWithViewPager(viewPage)
        useTabLayout()
    }


    private fun initView() {
        for (i in res) {
            val picture = this.findViewById<View>(R.id.picture)
            var page = LayoutInflater.from(this).inflate(R.layout.view_picture, null)
            var imageView = page.findViewById<ImageView>(R.id.picture)
            imageView.setBackgroundResource(i)
            pages.add(page)
        }
    }

    private fun useTabLayout() {
        tabLayout.visibility = View.VISIBLE
    }

    inner class ViewPagerAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return pages.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view = pages[position]
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(pages[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "页面" + position
        }
    }
}