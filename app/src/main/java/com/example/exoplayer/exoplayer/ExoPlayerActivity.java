package com.example.exoplayer.exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.exoplayer.R;
import com.example.exoplayer.controls.ControlsContainer;
import com.example.exoplayer.controls.VideoControlsView;
import com.example.exoplayer.interfaces.IEndedListener;

public class ExoPlayerActivity extends Activity implements IEndedListener, AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = ExoPlayerActivity.class.getSimpleName();

    public static final String EXTRA_URL = "extra_url";

    private VideoPlayerView mPlayerView;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        mPlayerView = (VideoPlayerView) findViewById(R.id.player_view);

        VideoControlsView controlsView = new VideoControlsView(this);
        controlsView.setControlsListener(mPlayerView);

        ControlsContainer controlsContainer = (ControlsContainer) findViewById(R.id.controls_container);
        controlsContainer.addView(controlsView);

        mPlayerView.setPlayerListener(controlsView);
        mPlayerView.setEndedListener(this);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);


        mPlayerView.setUrl(getString(R.string.video_url));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.preparePlayer();
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.destroyPlayer();
    }

    private void showError() {
        Toast.makeText(this, R.string.video_player_error, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onEnded() {
        finish();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // dont do anything
                // should happen after onResume when we requestAudioFocus
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                mPlayerView.onPause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // just keep playing for short interruptions, ie notification noises
                break;
        }
    }
}
