package com.example.rizqy.aplikasi3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class PanduanActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView fiktiCard,ftiCard,feCard,ftspCard,fpCards,fsCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        getSupportActionBar().setTitle("Panduan Penulisan Ilmiah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fiktiCard = (CardView)findViewById(R.id.fikti);
        ftiCard = (CardView)findViewById(R.id.fti);
        feCard = (CardView)findViewById(R.id.fe);
        ftspCard = (CardView)findViewById(R.id.ftsp);
        fpCards = (CardView)findViewById(R.id.fp);
        fsCard = (CardView)findViewById(R.id.fs);

        fiktiCard.setOnClickListener(this);
        ftiCard.setOnClickListener(this);
        feCard.setOnClickListener(this);
        ftspCard.setOnClickListener(this);
        fpCards.setOnClickListener(this);
        fsCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.fikti : i = new Intent(this,Fikti_book1Activity.class);startActivity(i);break;
            case R.id.fti : i = new Intent(this,Fti_book1Activity.class);startActivity(i);break;
            case R.id.fe : i = new Intent(this,Fe_book1Activity.class);startActivity(i);break;
            case R.id.ftsp : i = new Intent(this,Ftsp_book1Activity.class);startActivity(i);break;
            case R.id.fp : i = new Intent(this,Fp_book1Activity.class);startActivity(i);break;
            case R.id.fs : i = new Intent(this,Fs_book1Activity.class);startActivity(i);break;
            default:break;}
    }
}
