package com.example.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zolad.zoominimageview.ZoomInImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnFox,btnDog,btnCat,btnSave,btnFavourite;
    private ZoomInImageView imageView;
    private TextView txtChooseAnimal;
    private ProgressBar progressBar;

    private Toolbar mToolbar;

    private String image;

    Bitmap bitmap;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StoragePermission(); //get read/write permission (call permission function)

        setContentView(R.layout.activity_main);
    }

    //function for get read/write permission
    private boolean StoragePermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return false;
        }
        else {
            return true;
        }
    }
}