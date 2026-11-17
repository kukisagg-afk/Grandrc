package com.grand.launcher.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.grand.game.R;
import com.grand.game.core.Config;

public class DonateFragment extends Fragment {

    Animation animation;


    public AppCompatImageView donate_help_image;
    public AppCompatImageButton donate_help_button;
    public VideoView videoView;
    public AppCompatImageView main;
    public AppCompatImageView deposit;
    private String packageName;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_donate, container, false);



        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);



        donate_help_button = (AppCompatImageButton) inflate.findViewById(R.id.donate_help_button);
        deposit = inflate.findViewById(R.id.deposit);

        AppCompatImageView donate_help_image = (AppCompatImageView) inflate.findViewById(R.id.donate_help_image);
        main = (AppCompatImageView) inflate.findViewById(R.id.button_play);
        AppCompatImageView deposit = (AppCompatImageView) inflate.findViewById(R.id.deposit);
        donate_help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (donate_help_image.getVisibility() == View.VISIBLE) {
                    donate_help_image.setVisibility(View.INVISIBLE);
                } else {
                    donate_help_image.setVisibility(View.VISIBLE);
                }
            }
        });
        ((AppCompatImageView) inflate.findViewById(R.id.deposit))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(
                                        new Intent("android.intent.action.VIEW", Uri.parse(Config.API_LINK)));
                            }
                        });
        ((ImageView) inflate.findViewById(R.id.donate_back_button))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(new Intent(getActivity(), com.grand.launcher.activity.MainActivity.class));
                            }
                        });

        return inflate;
    }






    public String getPackageName() {
        return packageName;
    }
}
