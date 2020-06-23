package com.example.testapp;

import android.graphics.Bitmap;
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
        setContentView(R.layout.activity_main);
    }
}