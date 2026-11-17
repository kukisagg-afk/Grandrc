package com.grand.launcher.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.grand.game.R;
import com.grand.game.core.Config;
import com.grand.launcher.other.Utils;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
public class SettingsFragment extends Fragment {

    Animation animation;
    public EditText nickname;
    public AppCompatImageView vk;

    public ImageView sorry;
    public AppCompatImageView menu_graphics_tab;
    public AppCompatImageView discord;
    public AppCompatImageView reinstallgame;
    String nickName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_settings, container, false);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);

        nickname = (EditText) inflate.findViewById(R.id.editText);
        vk = (AppCompatImageView) inflate.findViewById(R.id.vk);
        menu_graphics_tab = (AppCompatImageView) inflate.findViewById(R.id.menu_graphics_tab);
        discord = (AppCompatImageView) inflate.findViewById(R.id.discord);
        ImageView sorry = (ImageView) inflate.findViewById(R.id.sorry);

        menu_graphics_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sorry.getVisibility() == View.VISIBLE) {
                    sorry.setVisibility(View.INVISIBLE);
                } else {
                    sorry.setVisibility(View.VISIBLE);
                }
            }
        });

        InitLogic();

        ((AppCompatImageView) inflate.findViewById(R.id.reinstallGame))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                File gameDirectory = (new File(Environment.getExternalStorageDirectory() + "/Horizont"));
                                Utils.delete(gameDirectory);
                                startActivity(new Intent(getActivity(), com.grand.launcher.activity.LoaderActivity.class));
                            }
                        });
     //
        ((ImageView) inflate.findViewById(R.id.settings_back_button))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(new Intent(getActivity(), com.grand.launcher.activity.MainActivity.class));
                            }
                        });
        ((AppCompatImageView) inflate.findViewById(R.id.vk))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(
                                        new Intent("android.intent.action.VIEW", Uri.parse(Config.URL_VK)));
                            }
                        });
        ((AppCompatImageView) inflate.findViewById(R.id.discord))
                .setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                v.startAnimation(animation);
                                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Config.URL_DISCORD)));
                            }
                        });

        ((EditText) nickname)
                .setOnEditorActionListener(
                        new EditText.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(
                                    TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_SEARCH
                                        || actionId == EditorInfo.IME_ACTION_DONE
                                        || event.getAction() == KeyEvent.ACTION_DOWN
                                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                    try {
                                        File f =
                                                new File(
                                                        Environment.getExternalStorageDirectory()
                                                                + "/Horizont/SAMP/settings.ini");
                                        if (!f.exists()) {
                                            f.createNewFile();
                                            f.mkdirs();
                                        }
                                        Wini w =
                                                new Wini(
                                                        new File(
                                                                Environment.getExternalStorageDirectory()
                                                                        + "/Horizont/SAMP/settings.ini"));
                                        if(checkValidNick(inflate)){
                                            w.put("client", "name", nickname.getText().toString());
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Ваш новый никнейм успешно сохранен!",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            checkValidNick(inflate);
                                        }
                                        w.store();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), "Установите игру!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
                        });
        return inflate;
    }

    private void InitLogic() {
        try {
            Wini w = new Wini(new File(Environment.getExternalStorageDirectory() + "/Horizont/SAMP/settings.ini"));
            nickname.setText(w.get("client", "name"));
            w.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidNick(View inflate){
        EditText nick = (EditText) inflate.findViewById(R.id.editText);
        if(nick.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Введите ник", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(nick.getText().toString().contains("_"))){
            Toast.makeText(getActivity(), "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nick.getText().toString().length() < 4){
            Toast.makeText(getActivity(), "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}