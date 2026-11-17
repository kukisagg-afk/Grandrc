package com.grand.launcher.fragment;



import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.grand.game.R;
import com.grand.game.core.Config;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class EditFragment extends Fragment {
    public EditText nickname;

    String nickName;
    private AppCompatEditText editTextname;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.edit_fragment, container, false);
        editTextname = inflate.findViewById(R.id.editText);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);

        nickname = (EditText) inflate.findViewById(R.id.editText);
        InitLogic();


        editTextname.setOnEditorActionListener(
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
                                                new File(Config.GAME_PATH + "/SAMP/settings.ini");
                                        if (!f.exists()) {
                                            f.createNewFile();
                                            f.mkdirs();
                                        }
                                        Wini w =
                                                new Wini(
                                                        new File(Config.GAME_PATH + "/SAMP/settings.ini"));
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


    public void setedittextmain(AppCompatEditText edittextmain) {
        ViewGroup.LayoutParams layoutParams = edittextmain.getLayoutParams();
        ViewGroup parent = (ViewGroup) edittextmain.getParent();
        int index = parent.indexOfChild(edittextmain);

        parent.removeView(edittextmain);
        parent.addView(editTextname, index, layoutParams);

        editTextname.requestFocus();
    }

    private void InitLogic() {
        try {
            Wini w = new Wini(new File(Config.GAME_PATH + "/SAMP/settings.ini"));
            nickname.setText(w.get("client", "name"));
            w.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidNick(View inflate){
        EditText nick = (AppCompatEditText) inflate.findViewById(R.id.editText);
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

    public AppCompatEditText getEditText() {
        return editTextname;
    }

    public void setEditText(AppCompatEditText AppCompatEditText) {
        this.editTextname = AppCompatEditText;
    }
}
