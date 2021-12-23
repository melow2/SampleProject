package com.example.episode.presentation.home.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.episode.R
import com.example.episode.databinding.FragmentMainBinding
import com.example.episode.presentation.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val viewModel: PayMainViewModel by viewModels()
    private val mainActivity get() = requireActivity() as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() = with(viewModel) {
        searchBookList("안녕")
        state.observe(viewLifecycleOwner,{ viewState ->
            when(viewState.state){
                PayMainViewState.State.DATA ->{
                    Timber.tag("DEBUG").d("${viewState.books}")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = MainFragment::class.simpleName
        private const val TITLE = "카카오 도서 검색"
    }
}
