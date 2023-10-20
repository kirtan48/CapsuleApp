package com.example.capsuleapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.capsuleapp.R
import com.example.capsuleapp.activity.MainActivity
import com.example.capsuleapp.databinding.FragmentVideoBinding
import com.example.capsuleapp.viewModel.CapsuleViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource


class VideoFragment : Fragment() {
    private val viewPager: ViewPager2 by lazy {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager)
    }
    private  lateinit var binding: FragmentVideoBinding
    lateinit var viewModel: CapsuleViewModel
    private  var exoPlayer:ExoPlayer?=null
    private var playbackPosition = 0L
    private var playWhenReady = true
    private  lateinit var nextButton: CardView
    private var URL:String=""


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      Log.d("reached","cal++")
      super.onViewCreated(view, savedInstanceState)
      Log.d("reached","link2 ${viewModel.capsule.videoUrl}  ")
      nextButton=view.findViewById(R.id.cd_next)
      nextButton.setOnClickListener {
          // Get the index of the current fragment.
          val currentItem = viewPager.currentItem
          viewPager.setCurrentItem(currentItem+1,true)

      }

  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(CapsuleViewModel::class.java)
        URL=viewModel.capsule.videoUrl
        preparePlayer()
        return binding?.root
    }
    private fun  preparePlayer() {

        exoPlayer = ExoPlayer.Builder(requireContext())
            .setSeekBackIncrementMs(9000)
            .setSeekForwardIncrementMs(9000)
            .build()
        exoPlayer?.playWhenReady = true
        binding.playerView.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri("https://$URL")
        val mediaSource =
            DashMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.playWhenReady = playWhenReady

        exoPlayer?.prepare()

    }

    private fun  relasePlayer(){

        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        relasePlayer()
    }
    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        Log.d("reached","resume++")
        val linearLayout = (activity as? MainActivity)?.findViewById<LinearLayout>(R.id.linear_layout)
        if (linearLayout != null) {
            linearLayout.visibility = View.VISIBLE
        }
        val underline2 = (activity as? MainActivity)?.findViewById<View>(R.id.underline2)
        val underline3 = (activity as? MainActivity)?.findViewById<View>(R.id.underline3)
        val linesText=(activity as? MainActivity)?.findViewById<TextView>(R.id.ll_tv2)
        val notesText=(activity as? MainActivity)?.findViewById<TextView>(R.id.ll_tv3)
        Log.d("reached","color")
        if(  linesText!=null && notesText!=null) {
            val resolvedColor = ContextCompat.getColor(requireContext(), R.color.grey)
            Log.d("reached","color")
            linesText.setTextColor(resolvedColor)
            notesText.setTextColor(resolvedColor)

        }
        if(underline2!=null){
            underline2.setBackgroundResource(R.color.text_underline_notselected)
        }
        if(underline3!=null){
            underline3.setBackgroundResource(R.color.text_underline_notselected)
        }
        val progressBar = (activity as? MainActivity)?.findViewById<ProgressBar>(R.id.question_progress)
        val questionNo= (activity as? MainActivity)?.findViewById<TextView>(R.id.question_no)
        val cardBookMark=(activity as? MainActivity)?.findViewById<CardView>(R.id.cd_bookmark)
        if(progressBar!=null && questionNo!=null && cardBookMark !=null){
            progressBar.visibility=View.INVISIBLE
            questionNo.visibility=View.INVISIBLE
            cardBookMark.visibility=View.INVISIBLE
        }
        super.onResume()
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = true // Resume playback
        }

    }
     override fun onPause() {
        super.onPause()
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = false // Pause playback
        }
    }










}

