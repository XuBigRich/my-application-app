package cn.piao888.ui.titleBarActivity.titleFragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.piao888.R
import cn.piao888.ui.widget.QQStepView

class TitleBarFragment : Fragment() {
    val menuInflater: MenuInflater
        @SuppressLint("RestrictedApi")
        get() = SupportMenuInflater(requireContext())
    lateinit var qqStepView: QQStepView

//    lateinit var tileBar: TitleBar
    lateinit var viewPage: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_title_bar, container, false)
        mView?.let {
//            tileBar = it.findViewById(R.id.title_bar)
//            setSupportToolbar(tileBar.toolbar)
            viewPage = it.findViewById(R.id.view_pager_bookshelf)
            qqStepView = mView.findViewById(R.id.step_id)
        }
        //设置动画
        var ofFloat = ValueAnimator.ofInt(0, 30000)
        ofFloat.setDuration(1000)
        ofFloat.addUpdateListener {
            val animatedValue = it.getAnimatedValue()
            qqStepView.mCurrentStep = animatedValue as Int
        }
        ofFloat.start()
//        tileBar = mView?.findViewById(R.id.title_bar)!!
//        viewPage = mView.findViewById(R.id.view_pager_bookshelf)!!
        return mView
    }

    fun setSupportToolbar(toolbar: Toolbar) {
        toolbar.apply {
            menu.apply {
                onCompatCreateOptionsMenu(this)
            }
        }
    }

    fun onCompatCreateOptionsMenu(menu: Menu) {
        menuInflater.inflate(R.menu.nav_button_menu, menu)
    }

}