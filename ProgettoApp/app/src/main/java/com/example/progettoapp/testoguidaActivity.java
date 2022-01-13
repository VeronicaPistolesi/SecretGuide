package com.example.progettoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

public class testoguidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testoguida);

        String testo = getIntent().getExtras().getString("testo");

        PDFView pdfView = findViewById(R.id.pdfview);
        pdfView.fromAsset(testo).load();
        pdfView.zoomTo(5);
        pdfView.setMinZoom(5);     //PDF

    }


}