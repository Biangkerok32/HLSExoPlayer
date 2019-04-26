package com.example.exoplayer.interfaces;

public interface IControlsListener {

    void onPause();
    void onPlay();
    void onSeekTo(float percentComplete);
    void onControlsHidden();
    void onControlsShown();
}
