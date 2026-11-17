package com.grand.launcher.activity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.grand.game.R;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoaderActivity extends AppCompatActivity {
    private final String link = "http://89.185.84.18/game.zip";
    /* access modifiers changed from: private */
    public final String path_zip = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/game.zip");
    ProgressBar progressBar;
    ProgressBar progressBarInstall;
    TextView textView6;
    VideoView mVideoView;
    TextView textView7;
    TextView textview5;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_install);
        this.textview5 = (TextView) findViewById(R.id.textView5);
        this.textView7 = (TextView) findViewById(R.id.textView7);
        this.textView6 = (TextView) findViewById(R.id.textView6);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mVideoView = (VideoView) findViewById(R.id.videoView2);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.fire_bg2);

        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        File file = new File(Environment.getExternalStorageDirectory() + "/");
        if (file.exists()) {
            file.delete();
        }
        this.progressBarInstall = (ProgressBar) findViewById(R.id.progressBarInstall);
        try {
            getFileSize("http://89.185.84.18/game.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFileSize(final String string) throws IOException {
        new Thread(new Runnable() {
            FileOutputStream file = new FileOutputStream(LoaderActivity.this.path_zip);

            /* renamed from: in */
            InputStream f115in;
            URLConnection urlConnection = null;

            public void run() {
                try {
                    URLConnection openConnection = new URL(string).openConnection();
                    this.urlConnection = openConnection;
                    openConnection.connect();
                    int length = this.urlConnection.getContentLength();
                    this.f115in = this.urlConnection.getInputStream();
                    byte[] data = new byte[4096];
                    int chet = 0;
                    while (true) {
                        int read = this.f115in.read(data, 0, 4096);
                        int count = read;
                        if (read == -1) {
                            break;
                        }
                        chet += count;
                        this.file.write(data, 0, count);
                        int finalChet = chet;
                        LoaderActivity.this.runOnUiThread(new Runnable() {
                            public final void run() {
                                LoaderActivity.this.lambda$run$0$InstallActivity$1(finalChet, length);
                            }
                        });
                    }
                    FileOutputStream fileOutputStream = this.file;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    InputStream inputStream = this.f115in;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {
                    Log.e("Downn", "Ошибка " + e3);
                    FileOutputStream fileOutputStream2 = this.file;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    InputStream inputStream2 = this.f115in;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    FileOutputStream fileOutputStream3 = this.file;
                    if (fileOutputStream3 != null) {
                        try {
                            fileOutputStream3.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    InputStream inputStream3 = this.f115in;
                    if (inputStream3 != null) {
                        try {
                            inputStream3.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
                LoaderActivity.this.runOnUiThread(new Runnable() {
                    public final void run() {
                        LoaderActivity.this.lambda$run$1$InstallActivity$1();
                    }
                });
                LoaderActivity.this.UnZip();
            }
        }).start();
    }

    private void lambda$run$1$InstallActivity$1() {
        LoaderActivity.this.textview5.setText("Распаковка файлов игры...");
        LoaderActivity.this.textView6.setVisibility(View.INVISIBLE);
        LoaderActivity.this.textView7.setVisibility(View.INVISIBLE);
        LoaderActivity.this.progressBarInstall.setVisibility(View.INVISIBLE);
        LoaderActivity.this.progressBar.setVisibility(View.VISIBLE);
    }

    private void lambda$run$0$InstallActivity$1(int finalChet, int length) {
        TextView textView = LoaderActivity.this.textView7;
        textView.setText((finalChet / 1048576) + " MB из " + (length / 1048576) + " MB");
        TextView textView2 = LoaderActivity.this.textView6;
        StringBuilder sb = new StringBuilder();
        sb.append(((finalChet / 1048576) * 100) / (length / 1048576));
        sb.append("%");
        textView2.setText(sb.toString());
        LoaderActivity.this.progressBarInstall.setProgress(((finalChet / 1048576) * 100) / (length / 1048576));
    }

    public void UnZip() {
        try {
            new ZipFile(this.path_zip).extractAll(String.valueOf(Environment.getExternalStorageDirectory()));
            new File(this.path_zip).delete();
        } catch (ZipException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, com.grand.game.core.GTASA.class));
        overridePendingTransition(0, 0);
    }
}
