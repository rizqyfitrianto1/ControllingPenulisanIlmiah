package com.example.rizqy.aplikasi3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Main extends Activity implements View.OnClickListener{
    private CardView progressCard,infoCard,accountCard,panduanCard,formulirCard,dospemCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressCard = (CardView)findViewById(R.id.progress);
        infoCard = (CardView)findViewById(R.id.info);
        accountCard = (CardView)findViewById(R.id.account);
        panduanCard = (CardView)findViewById(R.id.panduan);
        formulirCard = (CardView)findViewById(R.id.formulir);
        dospemCard = (CardView)findViewById(R.id.dospemcard);

        progressCard.setOnClickListener(this);
        infoCard.setOnClickListener(this);
        accountCard.setOnClickListener(this);
        panduanCard.setOnClickListener(this);
        formulirCard.setOnClickListener(this);
        dospemCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.progress : i = new Intent(this,ProgressActivity.class);startActivity(i);break;
            case R.id.info : i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://baak.gunadarma.ac.id/berita/338"));startActivity(i);break;
            case R.id.account : i = new Intent(this,AccountActivity.class);startActivity(i);break;
            case R.id.panduan : i = new Intent(this,PanduanActivity.class);startActivity(i);break;
            case R.id.formulir : i = new Intent(this,FormulirActivity.class);startActivity(i);break;
            case R.id.dospemcard : i = new Intent(this,DospemCard.class);startActivity(i);break;
            default:break;
        }
    }

    public void onBackPressed() {
        final AlertDialog.Builder konfirmasi = new AlertDialog.Builder(Main.this);
        konfirmasi.setMessage("Apakah Anda ingin keluar dari Aplikasi?");
        konfirmasi.setCancelable(true);
        konfirmasi.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        konfirmasi.setPositiveButton("KELUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exit);
                finish();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = konfirmasi.create();
        alertDialog.setTitle("Konfirmasi");
        alertDialog.show();
    }
}
