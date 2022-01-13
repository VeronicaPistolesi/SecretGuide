package com.example.progettoapp;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.Camera;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ExperimentalGetImage;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.ImageProxy;
//import androidx.camera.core.Preview;
//import androidx.camera.view.PreviewView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private ImageButton torre, duomo, battistero, cavalieri, spina, blu;
    private ImageButton cur1, cur2, cur3, cur4, cur5, cur6, curfin;
    private TextView cinema, medici, mura, mare, lungarni, musei, torri;
    private TextView visitare, scoprire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        torre = findViewById(R.id.imageView5);
        duomo = findViewById(R.id.imageView6);
        battistero = findViewById(R.id.imageView7);
        cavalieri = findViewById(R.id.imageView8);
        spina = findViewById(R.id.imageView9);
        blu = findViewById(R.id.imageView10);

        cur1 = findViewById(R.id.imageView17);
        cur2 = findViewById(R.id.imageView18);
        cur3 = findViewById(R.id.imageView19);
        cur4 = findViewById(R.id.imageView20);
        cur5 = findViewById(R.id.imageView21);
        cur6 = findViewById(R.id.imageView22);
        curfin = findViewById(R.id.imageView);

        cinema = findViewById(R.id.textView22);
        medici = findViewById(R.id.textView23);
        mura = findViewById(R.id.textView24);
        mare = findViewById(R.id.textView25);
        lungarni = findViewById(R.id.textView26);
        musei = findViewById(R.id.textView27);
        torri = findViewById(R.id.textView16);

        visitare = findViewById(R.id.textView6);
        scoprire = findViewById(R.id.textView7);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserGuides", Context.MODE_PRIVATE);

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);


        if (sp.getBoolean("Torre", false)) {
            //color image
            torre.setImageResource(R.drawable.torre2);
            torre.setEnabled(true);
            cur1.setImageResource(R.drawable.cinema);
            cur1.setEnabled(true);
            cinema.setText("Cinema");
        } else {
            //black and white image
            torre.setImageResource(R.drawable.torre1);
            torre.setEnabled(false);
            cur1.setImageResource(R.drawable.lente);
            cur1.setEnabled(false);
            cinema.setText("");
        }

        if (sp.getBoolean("Duomo", false)) {
            //color image
            duomo.setImageResource(R.drawable.duomo2);
            duomo.setEnabled(true);
            cur2.setImageResource(R.drawable.medici);
            cur2.setEnabled(true);
            medici.setText("Medici");
        } else {
            //black and white image
            duomo.setImageResource(R.drawable.duomo1);
            duomo.setEnabled(false);
            cur2.setImageResource(R.drawable.lente);
            cur2.setEnabled(false);
            medici.setText("");
        }

        if (sp.getBoolean("Battistero", false)) {
            //color image
            battistero.setImageResource(R.drawable.battistero2);
            battistero.setEnabled(true);
            cur3.setImageResource(R.drawable.mura);
            cur3.setEnabled(true);
            mura.setText("Mura Medievali");
        } else {
            //black and white image
            battistero.setImageResource(R.drawable.battistero1);
            battistero.setEnabled(false);
            cur3.setImageResource(R.drawable.lente);
            cur3.setEnabled(false);
            mura.setText("");
        }

        if (sp.getBoolean("Cavalieri", false)) {
            //color image
            cavalieri.setImageResource(R.drawable.piazzadeicavalieri2);
            cavalieri.setEnabled(true);
            cur4.setImageResource(R.drawable.mare);
            cur4.setEnabled(true);
            mare.setText("La Battaglia");
        } else {
            //black and white image
            cavalieri.setImageResource(R.drawable.piazzadeicavalieri1);
            cavalieri.setEnabled(false);
            cur4.setImageResource(R.drawable.lente);
            cur4.setEnabled(false);
            mare.setText("");
        }

        if (sp.getBoolean("Spina", false)) {
            //color image
            spina.setImageResource(R.drawable.chiesadellaspina2);
            spina.setEnabled(true);
            cur5.setImageResource(R.drawable.lungarni);
            cur5.setEnabled(true);
            lungarni.setText("Lungarni");
        } else {
            //black and white image
            spina.setImageResource(R.drawable.chiesadellaspina1);
            spina.setEnabled(false);
            cur5.setImageResource(R.drawable.lente);
            cur5.setEnabled(false);
            lungarni.setText("");
        }
        if (sp.getBoolean("Blu", false)) {
            //color image
            blu.setImageResource(R.drawable.palazzoblu2);
            blu.setEnabled(true);
            cur6.setImageResource(R.drawable.musei);
            cur6.setEnabled(true);
            musei.setText("Musei");
        } else {
            //black and white image
            blu.setImageResource(R.drawable.palazzoblu1);
            blu.setEnabled(false);
            cur6.setImageResource(R.drawable.lente);
            cur6.setEnabled(false);
            musei.setText("");
        }

        //curiosità finale
        if (sp.getBoolean("Torre", false) && sp.getBoolean("Duomo", false) && sp.getBoolean("Battistero", false) && sp.getBoolean("Cavalieri", false) && sp.getBoolean("Spina", false) && sp.getBoolean("Blu", false)) {
            curfin.setImageResource(R.drawable.pendenza);
            curfin.setEnabled(true);
            torri.setText("Pisa in Pendenza");
        } else {
            curfin.setEnabled(false);
            curfin.setImageResource(R.drawable.lente);
            torri.setText("");
        }



        if (sp1.getBoolean("Lingua", false)) {
            //visualizza in lingua inglese
            visitare.setText("To visit:");
            scoprire.setText("To discover:");

            //apri guidaTorre
            torre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "torreEN.pdf");
                }
            });
            //apri guidaCinema
            cur1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "cinemaEN.pdf");
                }
            });
            //apri guidaDuomo
            duomo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "duomoEN.pdf");
                }
            });
            //apri guidaMedici
            cur2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "mediciEN.pdf");
                }
            });
            //apri guidaBattistero
            battistero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "battisteroEN.pdf");
                }
            });
            //apri guidaMura
            cur3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "muraEN.pdf");
                }
            });
            //apri guidaCavalieri
            cavalieri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "cavalieriEN.pdf");
                }
            });
            //apri guidaMeloria
            cur4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "meloriaEN.pdf");
                }
            });
            //apri guidaChiesadellaSpina
            spina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "spinaEN.pdf");
                }
            });
            //apri guidaLungarni
            cur5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "lungarniEN.pdf");
                }
            });
            //apri guidaPalazzoBlu
            blu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "bluEN.pdf");
                }
            });
            //apri guidaMusei
            cur6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "museiEN.pdf");
                }
            });
            //apri guidaPendenza
            curfin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "pendenzaEN.pdf");
                }
            });

        } else {
            //visualizza in lingua italiana
            visitare.setText("Da visitare:");
            scoprire.setText("Da scoprire:");

            //apri guidaTorre
            torre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "torre.pdf");
                }
            });
            //apri guidaCinema
            cur1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "cinema.pdf");
                }
            });
            //apri guidaDuomo
            duomo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "duomo.pdf");
                }
            });
            //apri guidaMedici
            cur2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "medici.pdf");
                }
            });
            //apri guidaBattistero
            battistero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "battistero.pdf");
                }
            });
            //apri guidaMura
            cur3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "mura.pdf");
                }
            });
            //apri guidaCavalieri
            cavalieri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "cavalieri.pdf");
                }
            });
            //apri guidaMeloria
            cur4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "meloria.pdf");
                }
            });
            //apri guidaChiesadellaSpina
            spina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "spina.pdf");
                }
            });
            //apri guidaLungarni
            cur5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "lungarni.pdf");
                }
            });
            //apri guidaPalazzoBlu
            blu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "blu.pdf");
                }
            });
            //apri guidaMusei
            cur6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "musei.pdf");
                }
            });
            //apri guidaPendenza
            curfin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openguida(testoguidaActivity.class, "pendenza.pdf");
                }
            });

        }


    }


    //metodo per aprire testoguidaActivity
    public void openguida(Class c, String file) {
        Intent intent = new Intent(this, c);

        //passaggio dati con putExtra, poi getExtra per fare una sola Activity
        intent.putExtra("testo", file);
        startActivity(intent);
    }

}
