package cn.piao888.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.data.bean.MusicBean
import cn.piao888.data.bean.MusicCollectionBean
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter

class MusicCollectionAdapter :
    BaseQuickAdapter<MusicCollectionBean, MusicCollectionAdapter.MusicCollectionHolder>() {


    class MusicCollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicCollectionIcon =
            lazy { itemView.findViewById<ImageView>(R.id.music_collection_icon) }
        val musicCollectionName =
            lazy { itemView.findViewById<TextView>(R.id.music_collection_name) }
        val singerName = lazy { itemView.findViewById<TextView>(R.id.singer_name) }
    }

    override fun onBindViewHolder(
        holder: MusicCollectionAdapter.MusicCollectionHolder,
        position: Int,
        music: MusicCollectionBean?
    ) {
        music?.let {
            Glide.with(holder.itemView)
                .load(music.musicCollectionIcon)
                .into(holder.musicCollectionIcon.value)
            holder.musicCollectionName.value.text = music.musicCollectionName
            holder.singerName.value.text = music.singerName
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): MusicCollectionAdapter.MusicCollectionHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MusicCollectionHolder(
            inflater.inflate(
                R.layout.item_music_collection,
                parent,
                false
            )
        )
    }

}