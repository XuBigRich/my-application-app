package cn.piao888.data.bean

data class MainBean(
    val viewType: Int,
    var title:String,
    var photos:List<String>
)
class MusicBean(
    var musicName: String,
    var singerName: String,
    var isLike: Boolean
)
class MusicCollectionBean(
    var musicCollectionIcon: String,
    var musicCollectionName: String,
    var singerName: String,
)