package cn.piao888.adapter

import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.data.bean.MainBean

class CameraAdapter : RecyclerView.Adapter<CameraAdapter.CameraHolder>() {
    var datas = mutableListOf<Uri>()

    companion object {
        var PICTURESIZE=100
    }

    class CameraHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img by lazy {
            view.findViewById<ImageView>(R.id.picture).apply {
                // 设置宽和高为40dp
                val displayMetrics = context.resources.displayMetrics
                val sizeInPx = (PICTURESIZE * displayMetrics.density).toInt()
                // 调整ImageView的LayoutParams
                layoutParams = layoutParams.apply {
                    width = sizeInPx
                    height = sizeInPx
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraHolder {
        val inflater = LayoutInflater.from(parent.context)

        return CameraHolder(inflater.inflate(R.layout.view_picture, parent, false).apply {
            var displayMetrics = resources.displayMetrics
            val sizeInPx = (PICTURESIZE * displayMetrics.density).toInt()
            layoutParams.apply {
                width = sizeInPx
                height = sizeInPx
            }
        })
    }

    override fun getItemCount(): Int {
        datas.let {
            return it.size
        }
    }

    override fun onBindViewHolder(holder: CameraHolder, position: Int) {
        holder.img.setImageURI(datas[position])
    }
}