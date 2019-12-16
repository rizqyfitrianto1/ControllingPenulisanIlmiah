package com.example.rizqy.aplikasi3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splashscreen extends Activity {
    private ImageView iv;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        iv = (ImageView)findViewById(R.id.logogundar);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        iv.startAnimation(myanim);
        final Intent i = new Intent(Splashscreen.this, Status.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

        //Slogan Universitas Gunadarma
        textView = (TextView)findViewById(R.id.slogan);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Carrington.ttf");
        textView.setTypeface(typeface);

    }
}
