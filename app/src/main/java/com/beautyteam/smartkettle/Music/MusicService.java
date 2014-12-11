package com.beautyteam.smartkettle.Music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.beautyteam.smartkettle.R;

public class MusicService extends Service {
    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
            mp = MediaPlayer.create(this, R.raw.guylove);
            mp.setLooping(false);

    }

    public void onDestroy()
    {
        Log.d("music_service", "On destroy");
        mp.stop();
    }

    public void onStart(Intent intent,int startid){
        if(!mp.isPlaying()) {
            Log.d("music_service", "On start");
            mp.start();
        }
        else {
            mp.pause(); // при зыкрытии и повороте проблемы
        }
    }
}
