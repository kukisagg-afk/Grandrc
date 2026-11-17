package com.grand.launcher.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.grand.game.R;


public class AuthActivit extends AppCompatActivity {


    private VideoView mVideoView;
    private ImageView vk;


    private ImageView discord;

    private ImageView guest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);
        vk = (ImageView) findViewById(R.id.button_vk);
        guest = (ImageView) findViewById(R.id.button_guest);

        discord = (ImageView) findViewById(R.id.button_discord);
        mVideoView = (VideoView) findViewById(R.id.background_video);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.moscow_cycled);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        discord.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(AuthActivit.this, "В Разработке! используйте GUEST", Toast.LENGTH_SHORT).show();
                        v.startAnimation(animation);
                    }
                });
        vk.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(AuthActivit.this, "В Разработке! используйте Гость!", Toast.LENGTH_SHORT).show();
                        v.startAnimation(animation);
                    }
                });
        guest.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(AuthActivit.this, "Вы успешно авторизовались как ГОСТЬ!", Toast.LENGTH_SHORT).show();
                        v.startAnimation(animation);
                        onClickPlay();
                    }
                });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }
    public void onClickPlay() {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}