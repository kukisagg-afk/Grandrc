package com.grand.launcher.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.grand.game.R;
import com.grand.game.core.Config;

import java.io.File;

public class ServerActivity extends AppCompatActivity {

    public AppCompatImageView playButton;
    private VideoView mVideoView;

    public AppCompatImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);
        mVideoView = (VideoView) findViewById(R.id.background_video);
        playButton = (AppCompatImageView) findViewById(R.id.playbtn);
        back = (AppCompatImageView) findViewById(R.id.select_server_back_button);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.fire_bg2);

        mVideoView.setVideoURI(uri);
        mVideoView.start();


        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        back.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        v.startAnimation(animation);
                        onClickBack();
                    }
                });
        playButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        View view= LayoutInflater.from(ServerActivity.this).inflate(R.layout.custom_dialog,
                                (ViewGroup) findViewById(R.id.vhod));
                        Toast toast=new Toast(ServerActivity.this);
                                toast.setView(view);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM,0,0);
                                toast.show();
                        v.startAnimation(animation);
                        onClickPlay();
                    }
                });
    }




        public void onClickPlay() {
            if(IsGameInstalled()) {
                startActivity(new Intent(getApplicationContext(), com.grand.game.core.GTASA.class));
            } else {
                startActivity(new Intent(getApplicationContext(), LoaderActivity.class));
            }
        }
    public void onClickBack() {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    private boolean IsGameInstalled()
    {
        String CheckFile = Config.GAME_PATH + "/texdb/gta3.img";

        File file = new File(CheckFile);
        return file.exists();
    }
}
