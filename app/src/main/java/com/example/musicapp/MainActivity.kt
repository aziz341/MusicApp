package com.example.musicapp

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import java.util.*

class MainActivity : AppCompatActivity() {
    var playPauseBtn:ImageButton ?=null
    var volumeControl:SeekBar ?=null
    var musicProgress:SeekBar ?=null
    var player:MediaPlayer?=null
    var manager:AudioManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inisialization()
        setVolumeOptions()
        musicOption()

        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                musicProgress!!.progress = player!!.currentPosition
                if (player!!.isPlaying){
                    playPauseBtn!!.setImageResource(R.drawable.ic_pause)
                }
            }
        }, 0,300)

    }

    private fun musicOption() {
        musicProgress?.apply {
            max = player!!.duration
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, prgress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        player!!.seekTo(prgress)
                    }

                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })
        }
    }

    private fun setVolumeOptions() {
        val maxVolume = manager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = manager?.getStreamVolume(AudioManager.STREAM_MUSIC)

        val apply = volumeControl?.apply {
            progress = currentVolume!!
            max = maxVolume!!
            setOnSeekBarChangeListener(
                object : OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        manager!!.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }
                },
            )
        }
    }

    private fun inisialization() {
        setContentView(R.layout.activity_main)
        playPauseBtn = findViewById(R.id.playPauseBtn)
        volumeControl = findViewById(R.id.volumeControl)
        musicProgress = findViewById(R.id.musicProgress)
        manager = getSystemService(AUDIO_SERVICE) as AudioManager?
        player = MediaPlayer.create(this ,R.raw.hislerim)
    }

    fun previus(view: View) {
            player!!.seekTo(0)
            player!!.start()
        playPauseBtn!!.setImageResource(R.drawable.ic_pause)
        }

        fun PauseBtn(view: View) {

            Handler().postDelayed(object:Runnable {
                override fun run() {
                    if (player!!.isPlaying){
                        player!!.pause()
                        playPauseBtn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                    else{
                        player!!.start()
                        playPauseBtn!!.setImageResource(R.drawable.ic_pause)
                    }
                }
            },200)
        }
        fun skipNextBtn(view: View) {
        player!!.seekTo(player!!.duration)
            playPauseBtn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }
        fun skipNazad(view: View) {

        }
    }
