package com.example.progettoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button buttonopenpage;
    private ToggleButton buttonlingua;
    private TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //apri GuidaActivity
        buttonopenpage = (Button) findViewById(R.id.button_inizia);
        buttonopenpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGuidaActivity();
            }
        });

        text1 = findViewById(R.id.textView2);

        //cambia lingua
        buttonlingua = (ToggleButton) findViewById(R.id.button_lingua);
        buttonlingua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp = getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if (isChecked) {
                    // The toggle is enabled
                    editor.putBoolean("Lingua", true); //cambia in inglese
                    editor.commit();
                    openText("welcome.txt");
                    buttonopenpage.setText("START");
                } else {
                    // The toggle is disabled
                    editor.putBoolean("Lingua", false); //cambia in italiano
                    editor.commit();
                    openText("benvenuto.txt");
                    buttonopenpage.setText("INIZIA");
                }
            }
        });



        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);

        if (sp1.getBoolean("Lingua", false)) {
            //visualizza in lingua inglese
            buttonopenpage.setText("START");
            openText("welcome.txt");
            buttonlingua.setChecked(true);
        } else {
            //visualizza in lingua italiana
            buttonopenpage.setText("INIZIA");
            openText("benvenuto.txt");
            buttonlingua.setChecked(false);
        }

    }

    //metodo per aprire GuidaActivity
    public void openGuidaActivity() {
        Intent intent = new Intent(this, GuidaActivity.class);
        startActivity(intent);
    }

    //metodo per aprire il testo di benvenuto
    public void openText(String txt) {
        String text = "";
        try {
            InputStream is = getAssets().open(txt);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        text1.setText(text);
    }

}