package com.example.exoplayer.interfaces;

public interface IPlayerListener {

    void onBufferingStart();
    void onBufferingComplete();
    void onSetDuration(long durationMs);
    void onUpdateProgress(float percentComplete);
}
