package com.example.progettoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GuidaActivity extends AppCompatActivity {

    private Button buttonopenpage;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private ImageButton mCaptureBtn;
    private ImageView mImageView;
    private Uri image_uri;
    private TextView resultTV, text15;
    private InputImage image;
    private InputStream inputStream, streamCountLines;
    private BufferedReader bufferedReader, readerCountLines;
    private String[] textData;
    private int intCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guida);

        //apri MainActivity2
        buttonopenpage = (Button) findViewById(R.id.button_guida);
        buttonopenpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity2();
            }
        });

        //scatta foto
        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        //button click
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if system os is >= marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        openCamera();
                    }
                } else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });

        //testo foto
        resultTV = findViewById(R.id.textView);
        streamCountLines = this.getResources().openRawResource(R.raw.testocamera);
        readerCountLines = new BufferedReader(new InputStreamReader(streamCountLines));
        //conta il numero di righe di testocamera.txt
        try {
            while (readerCountLines.readLine()!=null) {
                intCount++;
            }
        } catch (Exception e ) {
            e.printStackTrace();
        }

        text15 = findViewById(R.id.textView15);
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);

        if (sp1.getBoolean("Lingua", false)) {
            //visualizza in lingua inglese
            text15.setText("Take a picture!");
            buttonopenpage.setText("MY GUIDE");
        } else {
            //visualizza in lingua italiana
            text15.setText("Scatta una foto!");
            buttonopenpage.setText("LA MIA GUIDA");
        }


    }




    //metodo per aprire MainActivity2
    public void openMainActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    //metodo per aprire il testo (guida sbloccata oppure no)
    public void openTestoCamera(int N) {
        inputStream = this.getResources().openRawResource(R.raw.testocamera);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        textData = new String[intCount];

        try {
            for (int i=0; i<intCount; i++) {
                textData[i] = bufferedReader.readLine();
            }
        } catch (Exception f) {
            f.printStackTrace();
        }

        resultTV.setText(textData[N]);
    }


    //metodo per aprire la camera
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera Intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //this method is called when user presses Allow or Deny from Permission Request Popup
        switch (requestCode) {
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                } else {
                    //permission from popup was denied
                    Toast.makeText(this, "Allow the permissions for a correct use of the App", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //called when image was captured from camera
        if (resultCode == RESULT_OK) {
            //set the image captured to ImageView
            mImageView.setImageURI(image_uri);
            try {
                LocalModel localModel = new LocalModel.Builder()
                        .setAssetFilePath("model.tflite")
                        // or .setAbsoluteFilePath(absolute file path to model file)
                        // or .setUri(URI to model file)
                        .build();

                CustomImageLabelerOptions customImageLabelerOptions = new CustomImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.5f)
                        .setMaxResultCount(1)
                        .build();
                ImageLabeler labeler = ImageLabeling.getClient(customImageLabelerOptions);
                image = InputImage.fromFilePath(this, image_uri);
                // Process image with custom onSuccess() example
                // [START process_image]
                labeler.process(image)
                        .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                            @Override
                            public void onSuccess(List<ImageLabel> labels) {
                                // Task completed successfully
                                // ...
                                SharedPreferences sp = getSharedPreferences("MyUserGuides", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                SharedPreferences sp1 = getApplicationContext().getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);
                                //...
                                for (ImageLabel label : labels) {
                                    String text = label.getText();
                                    float confidence = label.getConfidence();
                                    int index = label.getIndex();
                                    if (confidence >= 0.95) {
                                        switch (index){
                                            case 0:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(1);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(0);
                                                }
                                                editor.putBoolean("Torre", true);
                                                editor.commit();
                                                break;
                                            case 1:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(4);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(3);
                                                }
                                                editor.putBoolean("Duomo", true);
                                                editor.commit();
                                                break;
                                            case 2:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(7);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(6);
                                                }
                                                editor.putBoolean("Battistero", true);
                                                editor.commit();
                                                break;
                                            case 3:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(10);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(9);
                                                }
                                                editor.putBoolean("Cavalieri", true);
                                                editor.commit();
                                                break;
                                            case 4:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(13);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(12);
                                                }
                                                editor.putBoolean("Spina", true);
                                                editor.commit();
                                                break;
                                            case 5:
                                                if (sp1.getBoolean("Lingua", false)) {
                                                    //visualizza in lingua inglese
                                                    openTestoCamera(16);
                                                } else {
                                                    //visualizza in lingua italiana
                                                    openTestoCamera(15);
                                                }
                                                editor.putBoolean("Blu", true);
                                                editor.commit();
                                                break;
                                        }
                                    } else {
                                        if (sp1.getBoolean("Lingua", false)) {
                                            //visualizza in lingua inglese
                                            openTestoCamera(19);
                                        } else {
                                            //visualizza in lingua italiana
                                            openTestoCamera(18);
                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                SharedPreferences sp1 = getApplicationContext().getSharedPreferences("MyUserLanguage", Context.MODE_PRIVATE);
                                // Task failed with an exception
                                if (sp1.getBoolean("Lingua", false)) {
                                    //visualizza in lingua inglese
                                    openTestoCamera(19);
                                } else {
                                    //visualizza in lingua italiana
                                    openTestoCamera(18);
                                }
                            }
                        });
                // [END process_image]
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}