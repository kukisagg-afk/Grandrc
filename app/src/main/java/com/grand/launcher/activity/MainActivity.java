package com.grand.launcher.activity;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grand.game.R;
import com.grand.game.core.Config;
import com.grand.launcher.fragment.EditFragment;
import com.grand.launcher.fragment.SettingsFragment;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public AppCompatButton playButton;
    public AppCompatButton settingsButton;

    public AppCompatImageView donate_button;

    public AppCompatImageView donate_buttons;

    public ImageButton aboutnickname;

    public EditText nickname;

   public ImageButton ib_info;

    public TextView about_nick;
    private VideoView mVideoView;
    private Fragment SettingsFragment;

    private EditFragment editFragment;
    private Fragment DonateFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        editFragment = new EditFragment();
        fragmentTransaction.add(R.id.edit_container, editFragment);

        fragmentTransaction.commit();

        EditText edittextmain = findViewById(R.id.edittextname);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        mVideoView = (VideoView) findViewById(R.id.videoView);
        playButton = (AppCompatButton) findViewById(R.id.button_play);
        settingsButton = (AppCompatButton) findViewById(R.id.button_settings);

        donate_button = (AppCompatImageView) findViewById(R.id.donate_button);

        donate_buttons = (AppCompatImageView) findViewById(R.id.left_menu_button2);

        aboutnickname = (ImageButton) findViewById(R.id.ib_info);


        TextView about_nick = (TextView) findViewById(R.id.text_view_info_about_nickname);


        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.moscow_cycled);

        mVideoView.setVideoURI(uri);
        mVideoView.start();


        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        SettingsFragment = new SettingsFragment();

        DonateFragment = new DonateFragment();


        edittextmain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment.setedittextmain((AppCompatEditText) v);
            }
        });




        aboutnickname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Соблюдайте все пункты! //Very STUDIO", Toast.LENGTH_SHORT).show();
                if (about_nick.getVisibility() == View.VISIBLE) {
                    about_nick.setVisibility(View.INVISIBLE);
                } else {
                    about_nick.setVisibility(View.VISIBLE);
                }
            }
        });


        settingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Настройки", Toast.LENGTH_SHORT).show();
                v.startAnimation(animation);
                replaceFragment(SettingsFragment);
            }

        });
        donate_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "DONAT", Toast.LENGTH_SHORT).show();
                v.startAnimation(animation);
                replaceFragments(DonateFragment);
            }

        });
        donate_buttons.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"DONAT", Toast.LENGTH_SHORT).show();
                v.startAnimation(animation);
                replaceFragments(DonateFragment);
            }

        });

        playButton.setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Запуск", Toast.LENGTH_SHORT).show();
                        v.startAnimation(animation);
                        onClickPlay();
                    }
                });
    }


	public void onClickPlay() {

		    startActivity(new Intent(getApplicationContext(), ServerActivity.class));
		}





    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, SettingsFragment)
                .addToBackStack(null)

                .commit();
    }
    public void replaceFragments(Fragment fragment) {

        getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, DonateFragment )
                .addToBackStack(null)

                .commit();
    }
	
	public boolean isRecordAudioPermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECORD_AUDIO") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, 2);
        return false;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        return false;
    }
	
	private boolean IsGameInstalled()
    {
        String CheckFile = Config.GAME_PATH + "/texdb/gta3.img";

        File file = new File(CheckFile);
        return file.exists();
    }

	private void startTimer()
    {
        Timer t = new Timer();
        t.schedule(new TimerTask(){

            @Override
            public void run() {
                onClickPlay();
            }
        }, 200L);
    }
	
	public void onDestroy() {
        super.onDestroy();
        
    }

    public void onRestart() {
        super.onRestart();
        
    }





	
	public boolean checkValidNick(){
		EditText nick = (EditText) findViewById(R.id.editText);
		if(nick.getText().toString().isEmpty()) {
			Toast.makeText(this, "Введите ник", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!(nick.getText().toString().contains("_"))){
			Toast.makeText(this, "Ник должен содержать символ \"_\"", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(nick.getText().toString().length() < 4){
			Toast.makeText(this, "Длина ника должна быть не менее 4 символов", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}