package cn.piao888.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.data.bean.MusicBean

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MusicHolder>() {
    var datas: MutableList<MusicBean>? = null

    class MusicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicName = lazy { itemView.findViewById<TextView>(R.id.music_name) }
        val singerName = lazy { itemView.findViewById<TextView>(R.id.singer_name) }
        val isLike = lazy { itemView.findViewById<ImageView>(R.id.is_like) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MusicHolder(inflater.inflate(R.layout.item_music, parent, false))
    }

    override fun getItemCount(): Int {
        datas?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        var music = datas!![position]
        holder.isLike.value.setImageResource(if (music.isLike) R.drawable.like else R.drawable.unlike)
        holder.musicName.value.text = music.musicName
        holder.singerName.value.text = music.singerName
    }
}