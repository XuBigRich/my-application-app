package cn.piao888.ui.coordinator.musicCollectionFragment

import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.adapter.MusicAdapter
import cn.piao888.adapter.MusicCollectionAdapter
import cn.piao888.extra.parseState
import cn.piao888.viewModel.MusicCollectionViewModel
import cn.piao888.viewModel.MusicViewModel

class MusicCollectionFragment : Fragment() {
    val mAdapter: MusicCollectionAdapter by lazy { MusicCollectionAdapter() }
    val viewModel by viewModels<MusicCollectionViewModel>()
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
        val mView = inflater.inflate(R.layout.fragment_music, container, false)
        this.recycler = mView.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
        handleTask()
        return mView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
        onListener()
    }

    private fun handleTask() {
        if (paginationState.isLoading) return

        paginationState.isLoading = true

        val map = mutableMapOf<String, Any>(
            "pageNum" to paginationState.page,
            "pageSize" to paginationState.pageSize
        )
        viewModel.getMusicCollectionBeanList(map)
    }

    private fun createObserver() {
        viewModel.musicCollectionBeanList.observe(viewLifecycleOwner) {
            parseState(it, onSuccess = { data ->
                data?.let {
                    if (paginationState.page == 0) {
                        mAdapter.submitList(it)
                    } else {
                        mAdapter.addAll(it)
                    }
                    paginationState.page++
                    paginationState.isLastPage = it.size < paginationState.pageSize
                }
                paginationState.isLoading = false
            }, onError = {
                paginationState.isLoading = false
                // 显示错误信息，例如通过Toast
                showError("请求错误")
            })
        }
    }

    private fun showError(message: String) {
        // 显示错误信息，例如通过Toast
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
