package com.example.rizqy.aplikasi3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class FormulirActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView sertifikatCard,persetujuanCard,nilaiCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulir);

        getSupportActionBar().setTitle("Formulir Sidang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sertifikatCard = (CardView)findViewById(R.id.sertifikat);
        persetujuanCard = (CardView)findViewById(R.id.persetujuan);
        nilaiCard = (CardView)findViewById(R.id.nilai);

        nilaiCard.setOnClickListener(this);
        persetujuanCard.setOnClickListener(this);
        sertifikatCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.sertifikat : i = new Intent(this,FormSertifikatActivity.class);startActivity(i);break;
            case R.id.persetujuan : i = new Intent(this,FormPersetujuanActivity.class);startActivity(i);break;
            case R.id.nilai : i = new Intent(this,FormNilaiActivity.class);startActivity(i);break;
            default:break;}
    }
}
