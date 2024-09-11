package cn.piao888.viewModel

import androidx.lifecycle.MutableLiveData
import cn.piao888.data.bean.MusicBean
import cn.piao888.data.bean.MusicCollectionBean
import cn.piao888.extra.request
import cn.piao888.network.ResultState
import cn.piao888.repository.MusicViewRepository
import cn.piao888.viewModel.base.BaseViewModel

class MusicCollectionViewModel : BaseViewModel() {
    var musicCollectionBeanList = MutableLiveData<ResultState<MutableList<MusicCollectionBean>?>>()
    private val api: MusicViewRepository by lazy {
        MusicViewRepository()
    }

    fun getMusicCollectionBeanList(map: MutableMap<String, Any>) {
        request(
            block = { api.musicCollectionList(map) },
            resultState = musicCollectionBeanList
        )
    }
}