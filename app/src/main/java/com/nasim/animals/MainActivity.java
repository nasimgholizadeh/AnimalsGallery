package com.nasim.animals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnFox,btnDog,btnCat,btnSave;
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

        //save imageView image in gallery button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoragePermission(); //get read/write permission (call permission function)

                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
                File path = Environment.getExternalStorageDirectory();
                File dir = new File(path + "/DCIM/Animals");
                dir.mkdirs();
                String imageName = time + ".jpg";
                File file = new File(dir, imageName);
                OutputStream out;
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Toasty.success(MainActivity.this,"Saved.",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toasty.error(MainActivity.this,"An error occurred in storage!!!",Toast.LENGTH_LONG).show();
                }
                //show image in gallery
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                sendBroadcast(intent);
            }
        });

        //fox button on click listener
        btnFox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(1000).repeat(1).playOn(imageView); //animation on imageView
                getFoxImage(); //call function for get fox images when btnFox clicked
                txtChooseAnimal.setText("Fox"); //change textView text to selected animal
                txtChooseAnimal.setTextColor(Color.BLACK);
                progressBar.setVisibility(View.VISIBLE); //show progress bar
                btnSave.setEnabled(true); //enable btnSave
            }
        });
        //dog button on click listener
        btnDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(1000).repeat(1).playOn(imageView); //animation on imageView
                getDogImage(); //call function for get dog images when btnDog clicked
                txtChooseAnimal.setText("Dog"); //change textView text to selected animal
                txtChooseAnimal.setTextColor(Color.BLACK);
                progressBar.setVisibility(View.VISIBLE); //show progress bar
                btnSave.setEnabled(true); //enable btnSave
            }
        });
        //cat button on click listener
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(1000).repeat(1).playOn(imageView); //animation on imageView
                getCatImage(); //call function for get cat images when btnCat clicked
                txtChooseAnimal.setText("Cat"); //change textView text to selected animal
                txtChooseAnimal.setTextColor(Color.BLACK);
                progressBar.setVisibility(View.VISIBLE); //show progress bar
                btnSave.setEnabled(true); //enable btnSave
            }
        });
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
                    //put json object in imageView with Glide library
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
                txtChooseAnimal.setText("You're offline.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
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
                txtChooseAnimal.setText("You're offline.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
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
                    Glide.with(imageView).load(image).into(imageView);
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
                txtChooseAnimal.setText("You're offline.");
                progressBar.setVisibility(View.GONE);
                txtChooseAnimal.setTextColor(Color.RED);
                btnSave.setEnabled(false);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(8000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //press back again to exit
    @Override
    public void onBackPressed() {
        if (backPressedTime+2000>System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast=Toasty.info(getBaseContext(),"Press back again to exit.",Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}