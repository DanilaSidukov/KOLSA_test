package com.diphrogram.kolsa_test.presentation.video

import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.diphrogram.kolsa_test.common.BaseFragment
import com.diphrogram.kolsa_test.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment: BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {

    private val viewModel: VideoViewModel by viewModels()

    private lateinit var exoPlayer: ExoPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exoPlayer = buildExoPlayer()
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
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.url.isNotEmpty()) {
                    val mediaItem = MediaItem.fromUri(state.url)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
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

    @OptIn(UnstableApi::class)
    private fun buildExoPlayer(): ExoPlayer {
        val trackSelector = DefaultTrackSelector(requireContext())
        trackSelector.buildUponParameters().setAllowMultipleAdaptiveSelections(true)
        val renderFactory = DefaultRenderersFactory(requireContext())
            .setEnableDecoderFallback(true)
        val player = ExoPlayer.Builder(requireContext())
            .setRenderersFactory(renderFactory)
            .setTrackSelector(trackSelector)
            .setSeekBackIncrementMs(SEEK_VALUE)
            .setSeekForwardIncrementMs(SEEK_VALUE)
            .build()
        player.playbackParameters = PlaybackParameters(1f)

        return player
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

private const val SEEK_VALUE = 3000L