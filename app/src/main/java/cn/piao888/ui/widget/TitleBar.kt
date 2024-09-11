package cn.piao888.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import cn.piao888.R
import com.google.android.material.appbar.AppBarLayout

class TitleBar constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    val toolbar: Toolbar
    val menu: Menu
        get() = toolbar.menu
    var title: CharSequence?
        get() = toolbar.title
        set(title) {
            if (toolbar.title != title) {
                toolbar.title = title
            }
        }
    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        toolbar = findViewById(R.id.tab_layout)
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.TitleBar,
            R.attr.titleBarStyle, 0
        )
    }

}