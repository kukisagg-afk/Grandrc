package com.grand.launcher.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.grand.game.R;

import com.grand.launcher.other.Lists;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity{




	DatabaseReference databaseServers;










	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Lists.slist = new ArrayList<>();
		Lists.nlist = new ArrayList<>();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://vbd.fdv.dd/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
















		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
					|| checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
			} else {
				startTimer();
			}
		} else startTimer();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			startLauncher();
		}
	}

	private void startLauncher()
	{
		startActivity(new Intent(this, AuthActivit.class));
		NotificationCompat.Builder builder =
				new NotificationCompat.Builder(SplashActivity.this)
						.setSmallIcon(R.drawable.logo_splash)
						.setContentTitle(" ")
						.setContentText(" ")
						.setPriority(NotificationCompat.PRIORITY_DEFAULT);

		NotificationManagerCompat notificationManager =
				NotificationManagerCompat.from(SplashActivity.this);

		finish();
	}

	public static boolean isOnline(Context context)
	{
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}

	private void startTimer()
	{
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				startLauncher();
			}
		}, 1000L);
	}
}