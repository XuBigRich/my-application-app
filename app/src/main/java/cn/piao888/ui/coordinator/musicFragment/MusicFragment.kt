package cn.piao888.ui.coordinator.musicFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.adapter.MusicAdapter
import cn.piao888.extra.parseState
import cn.piao888.viewModel.MusicViewModel
import okhttp3.internal.notifyAll

class MusicFragment : Fragment() {
    val mAdapter: MusicAdapter by lazy { MusicAdapter() }
    val viewModel by viewModels<MusicViewModel>()
    private lateinit var recycler: RecyclerView
    private val paginationState = PaginationState()

    data class PaginationState(
        var page: Int = 1,
        val pageSize: Int = 10,
        var isLoading: Boolean = false,
        var isLastPage: Boolean = false
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mView = inflater.inflate(R.layout.fragment_music, container, false)
        this.recycler = mView.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
        handleTask()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
        onListener()
    }

    private fun handleTask() {
        if (paginationState.isLoading) return
        paginationState.isLoading = true
        viewModel.getMusicBeanList(paginationState.page, paginationState.pageSize)
    }

    private fun createObserver() {
        viewModel.musicBeanList.observe(viewLifecycleOwner) {
            parseState(it, onSuccess = { data ->
                data?.let {musicList->
                    if (mAdapter.datas == null) {
                        mAdapter.datas = mutableListOf()
                    }
                    val startPosition = mAdapter.datas!!.size
                    mAdapter.datas!!.addAll(musicList)

                    // 使用 notifyItemRangeInserted 代替 notifyDataSetChanged
                    mAdapter.notifyItemRangeInserted(startPosition, musicList.size)
                    paginationState.page++
                    paginationState.isLastPage = musicList.size < paginationState.pageSize

                }
                paginationState.isLoading = false
            }, onError = { "请求错误" })
        }
    }

    private fun onListener() {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && !paginationState.isLoading && !paginationState.isLastPage) {
                    // 加载更多数据
                    handleTask()
                }
            }
        })
    }
}