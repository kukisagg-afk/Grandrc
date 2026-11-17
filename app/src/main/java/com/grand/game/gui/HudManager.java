package com.grand.game.gui;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.grand.game.R;
import com.grand.game.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;

public class HudManager {
    public Activity activity;

    public Animation animation;

    public ConstraintLayout hud_layout;

    public ProgressBar hud_health;
    public ProgressBar hud_armour;

    public TextView hud_money;
    public TextView mAmmo1;
    public TextView mAmmo2;

    public TextView hud_health_t;
    public TextView hud_armour_t;

    public ImageView hud_weapon;

    public ArrayList<ImageView> hud_wanted;

    public HudManager(Activity aactivity) {
        activity = aactivity;

        hud_layout = aactivity.findViewById(R.id.hud_main);
        hud_layout.setVisibility(View.GONE);

        hud_health = aactivity.findViewById(R.id.hud_health_pb);
        hud_armour = aactivity.findViewById(R.id.hud_armour_pb);

        hud_health_t = aactivity.findViewById(R.id.hud_health_text);
        hud_armour_t = aactivity.findViewById(R.id.hud_armour_text);

        mAmmo1 = aactivity.findViewById(R.id.hud_ammo_text);
        mAmmo2 = aactivity.findViewById(R.id.hud_max_ammo_text);

        hud_money = aactivity.findViewById(R.id.hud_balance_text);
        hud_weapon = aactivity.findViewById(R.id.hud_weapon);

        hud_wanted = new ArrayList<>();
        hud_wanted.add(activity.findViewById(R.id.hud_star_1));
        hud_wanted.add(activity.findViewById(R.id.hud_star_2));
        hud_wanted.add(activity.findViewById(R.id.hud_star_3));
        hud_wanted.add(activity.findViewById(R.id.hud_star_4));
        hud_wanted.add(activity.findViewById(R.id.hud_star_5));
    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponid, int ammo, int ammoinclip, int playerid, int money, int wanted)
    {
        hud_health.setProgress(health);
        hud_armour.setProgress(armour);

        hud_health_t.setText(Integer.toString(health));
        hud_armour_t.setText(Integer.toString(armour));
        mAmmo1.setText(Integer.toString(ammoinclip));
        mAmmo2.setText(Integer.toString(ammo));

        DecimalFormat formatter = new DecimalFormat();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        String s = formatter.format(money).toString();
        hud_money.setText(String.valueOf(s));

        int id = activity.getResources().getIdentifier(new Formatter().format("weapon_%d", Integer.valueOf(weaponid)).toString(), "drawable", activity.getPackageName());
        hud_weapon.setImageResource(id);

        hud_weapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NvEventQueueActivity.getInstance().onWeaponChanged();
            }
        });
        if(wanted > 5) wanted = 5;
        for (int i2 = 0; i2 < wanted; i2++) {
            hud_wanted.get(i2).setBackgroundResource(R.drawable.ic_y_star);
        }
    }
    public void ShowHud() {
        Utils.ShowLayout(hud_layout, false);
    }

    public void HideHud() {
        Utils.HideLayout(hud_layout, false);
    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponid, int ammo, int playerid, int money, int wanted) {
    }
}
