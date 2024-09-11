package cn.piao888.viewModel

import androidx.lifecycle.MutableLiveData
import cn.piao888.data.bean.MusicBean
import cn.piao888.data.bean.MusicCollectionBean
import cn.piao888.extra.request
import cn.piao888.network.ResultState
import cn.piao888.repository.MusicViewRepository
import cn.piao888.viewModel.base.BaseViewModel

class MusicViewModel : BaseViewModel() {
    var musicBeanList = MutableLiveData<ResultState<MutableList<MusicBean>?>>()
    private val api: MusicViewRepository by lazy {
        MusicViewRepository()
    }

    fun getMusicBeanList(pageNumber: Int, pageSize: Int) {
        request(
            block = { api.musicList(pageNumber, pageSize) },
            resultState = musicBeanList
        )
    }
}