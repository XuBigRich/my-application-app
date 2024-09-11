package cn.piao888.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cn.piao888.R
import cn.piao888.data.bean.MainBean
import java.net.URI
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    lateinit var datas: MutableList<MainBean>
    val ONE_PHOTO: Int = 0
    val TWO_PHOTO: Int = 1
    val THREE_PHOTO: Int = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ONE_PHOTO -> OneMainHolder(inflater.inflate(R.layout.item_main_one, parent, false))
            TWO_PHOTO -> TwoMainHolder(inflater.inflate(R.layout.item_main_two, parent, false))
            THREE_PHOTO -> ThreeMainHolder(
                inflater.inflate(
                    R.layout.item_main_three,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        return datas.get(position).viewType
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bindData(datas.get(position))
    }

    abstract class MainHolder(open var view: View) : ViewHolder(view) {
        open fun bindData(mainBean: MainBean) {
            view.findViewById<TextView>(R.id.title).text = mainBean.title
        }
    }

    open class OneMainHolder(view: View) : MainHolder(view) {
        override fun bindData(mainBean: MainBean) {
            super.bindData(mainBean)
            var uri = Uri.parse(mainBean.photos.get(0))
            view.findViewById<ImageView>(R.id.one).setImageURI(uri)
        }
    }

    open class TwoMainHolder(view: View) : OneMainHolder(view) {
        override fun bindData(mainBean: MainBean) {
            super.bindData(mainBean)
            var uri = Uri.parse(mainBean.photos.get(1))
            view.findViewById<ImageView>(R.id.two).setImageURI(uri)
        }
    }

    class ThreeMainHolder(view: View) : TwoMainHolder(view) {
        override fun bindData(mainBean: MainBean) {
            super.bindData(mainBean)
            var uri = Uri.parse(mainBean.photos.get(2))
            view.findViewById<ImageView>(R.id.three).setImageURI(uri)
        }
    }
}