package com.diphrogram.kolsa_test.presentation.video

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.diphrogram.kolsa_test.R
import com.diphrogram.kolsa_test.databinding.FragmentVideoBinding
import com.diphrogram.kolsa_test.presentation.common.BaseFragment
import com.diphrogram.kolsa_test.presentation.common.ScreenState
import com.diphrogram.utils.ZERO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@UnstableApi
@AndroidEntryPoint
class VideoFragment: BaseFragment<FragmentVideoBinding>(
    FragmentVideoBinding::inflate
), Player.Listener {

    private val viewModel: VideoViewModel by viewModels()

    private lateinit var exoPlayer: ExoPlayer
    private var tracksInfo: TracksInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exoPlayer = viewModel.buildExoPlayer(requireContext())
        exoPlayer.addListener(this)
        arguments?.getInt(ID_ARG)?.let { id ->
            viewModel.getVideo(id)
        }
        bind()
        observeData()
    }

    private fun bind() = with(binding) {
        val title = arguments?.getString(TITLE_ARG).toString()
        val description = arguments?.getString(DESCRIPTION_ARG)
        toolbarText.text = title
        if (description != null) {
            descriptionText.isVisible = true
            descriptionText.text = description
        }
        player.player = exoPlayer

        qualityButton.setOnClickListener {
            if (tracksInfo == null) {
                tracksInfo = viewModel.getTracksInfo()
            }
            tracksInfo?.let { info ->
                showQualitySelectorDialog(info)
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                changeLoaderVisibility(state.screenState)
                when(state.screenState) {
                    ScreenState.LoadingComplete -> {
                        if (state.url.isNotEmpty()) {
                            val mediaItem = MediaItem.fromUri(state.url)
                            exoPlayer.setMediaItem(mediaItem)
                            exoPlayer.prepare()
                        }
                        setUIsVisibility(state.error)
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY) {
            binding.qualityButton.isEnabled = true
        }
    }

    private fun changeLoaderVisibility(screenState: ScreenState) = with(binding) {
        when(screenState) {
            ScreenState.LoadingProcess -> {
                loader.visibility = View.VISIBLE
                player.visibility = View.INVISIBLE
                qualityButton.visibility = View.INVISIBLE
            }
            ScreenState.LoadingComplete -> {
                loader.visibility = View.GONE
                player.visibility = View.VISIBLE
                qualityButton.visibility = View.VISIBLE
            }
        }
    }

    private fun setUIsVisibility(error: String) = with(binding) {
        val isUIVisible = error.isEmpty()
        player.visibility =
            if (isUIVisible) View.VISIBLE
            else View.INVISIBLE
        qualityButton.isVisible = isUIVisible
        errorText.isVisible = !isUIVisible
        if (!isUIVisible) {
            errorText.text = error
        }
    }

    private fun showQualitySelectorDialog(tracksInfo: TracksInfo) {
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(R.string.choose_video_quality))
            .setItems(tracksInfo.items.toTypedArray()) { _, index ->
                if (index != Int.ZERO) {
                    val realIndex = index - 1
                    tracksInfo.trackFormats[realIndex]
                    viewModel.setTrackSelector(tracksInfo, realIndex)
                }
            }
            .show()
    }

    companion object {

        private const val TITLE_ARG = "title_arg"
        private const val DESCRIPTION_ARG = "description_arg"
        private const val ID_ARG = "id_arg"

        fun newInstance(id: Int, title: String, description: String?): VideoFragment {
            val fragment = VideoFragment()
            val args = Bundle().apply {
                putInt(ID_ARG, id)
                putString(TITLE_ARG, title)
                putString(DESCRIPTION_ARG, description)
            }
            fragment.arguments = args
            return fragment
        }
    }
}