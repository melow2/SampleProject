package com.example.episode.presentation.home.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.episode.R
import com.example.episode.presentation.activity.MainActivity
import com.example.episode.base.view.BaseFragment
import com.example.episode.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<
        PayDetailViewState,
        PayDetailSingleEvent,
        PayDetailViewModel,
        FragmentDetailBinding>(R.layout.fragment_detail) {

    private val mainActivity get() = requireActivity() as MainActivity

    override val viewModel: PayDetailViewModel by viewModels()

    override fun setUpView(view: View, savedInstanceState: Bundle?) {}
    override fun render(viewState: PayDetailViewState) {}
    override fun handleEvent(event: PayDetailSingleEvent) {}

    companion object {
        val TAG = DetailFragment::class.simpleName
        const val NO_TITLE = "제목없음"
    }


}