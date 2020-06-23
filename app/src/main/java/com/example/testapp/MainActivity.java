package com.example.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONException;
import org.json.JSONObject;

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

        initViews();

        setSupportActionBar(mToolbar); //toolbar menu

        btnSave.setEnabled(false); //disable btnSave
        btnFavourite.setEnabled(false); //disable btnFavourite
    }

    //init views
    private void initViews() {
        btnFox=findViewById(R.id.btn_fox);
        btnDog=findViewById(R.id.btn_dog);
        btnCat=findViewById(R.id.btn_cat);
        imageView=findViewById(R.id.imageView);
        txtChooseAnimal=findViewById(R.id.txt_choose_animal);
        progressBar=findViewById(R.id.progressBar);
        btnSave=findViewById(R.id.btn_save);
        mToolbar=findViewById(R.id.m_toolbar);
        btnFavourite =findViewById(R.id.btn_favourite);
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

    //function for get fox images
    public void getFoxImage() {
        //json request
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,
                "https://randomfox.ca/floof/?ref=public-apis", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response.toString());
                try {
                    image=response.getString("image"); //get json object
                    /*put json object in imageView with Glide library
                    glide library supports gif*/
                    Glide.with(imageView).load(image).into(imageView);
                    progressBar.setVisibility(View.INVISIBLE); //hide progress bar
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString());
                txtChooseAnimal.setText("لطفا از اتصال دستگاه خود به اینترنت مطمئن شوید.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
                btnFavourite.setEnabled(false);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //function for get dog images
    private void getDogImage() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,
                "https://random.dog/woof.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: "+response.toString());
                try {
                    image=response.getString("url");
                    Glide.with(imageView).load(image).into(imageView); //put json object in imageView with Glide library
                    progressBar.setVisibility(View.INVISIBLE); //hide progress bar
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString());
                txtChooseAnimal.setText("لطفا از اتصال دستگاه خود به اینترنت مطمئن شوید.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
                btnFavourite.setEnabled(false);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //function for get cat images
    private void getCatImage() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,
                "https://aws.random.cat/meow?ref=public-apis", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    image=response.getString("file");
                    Glide.with(imageView).load(image).into(imageView); //put json object in imageView with Glide library
                    progressBar.setVisibility(View.INVISIBLE); //hide progress bar
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onResponse: "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.toString());
                txtChooseAnimal.setText("لطفا از اتصال دستگاه خود به اینترنت مطمئن شوید.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
                btnFavourite.setEnabled(false);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}