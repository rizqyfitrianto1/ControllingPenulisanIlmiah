package com.example.rizqy.aplikasi3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView ubahpasswordCard,aboutCard,keluarCard;

    TextView Nama,Npm,Kelas,Jurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setTitle("Acount");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ubahpasswordCard = (CardView)findViewById(R.id.ubahpassword);
        aboutCard = (CardView)findViewById(R.id.about);
        keluarCard = (CardView)findViewById(R.id.keluar);

        ubahpasswordCard.setOnClickListener(this);
        aboutCard.setOnClickListener(this);
        keluarCard.setOnClickListener(this);

        Nama = (TextView)findViewById(R.id.nama);
        Nama.setText(Common.currentMahasiswa.getNama());

        Npm = (TextView)findViewById(R.id.npm);
        Npm.setText(Common.currentMahasiswa.getNpm());

        Kelas = (TextView)findViewById(R.id.kelas);
        Kelas.setText(Common.currentMahasiswa.getKelas());

        Jurusan = (TextView)findViewById(R.id.jurusan);
        Jurusan.setText(Common.currentMahasiswa.getJurusan());

        Paper.init(this);
    }

    @Override
    public void onClick(View view) {
        Intent i ;
        switch (view.getId()) {
            case R.id.ubahpassword:
                showChangePasswordDialog();
                break;
            case R.id.about:
                AlertDialog.Builder info = new AlertDialog.Builder(AccountActivity.this);
                info.setMessage("Aplikasi ini ditujukan untuk Dosen dan Mahasiswa Strata 1 Universitas Gunadarma yang melaksanakan proses Bimbingan Penulisan Ilmiah. Tujuannya untuk membuat sebuah portal Bimbingan berbasis aplikasi android untuk memudahkan proses kontrol terhadap penulisan ilmiah tersebut.").setCancelable(false).setPositiveButton("Lanjut", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = info.create();
                dialog.setTitle("Tentang Aplikasi");
                dialog.show();
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setTextSize(15);
                break;
            case R.id.keluar:
                //Delete Remember User & Password
                Paper.book().destroy();
                //Logout
                Intent login2 = new Intent(AccountActivity.this,Status.class);
                login2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login2);
                break;
            default:
                break;
        }
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccountActivity.this);
        alertDialog.setTitle("Ubah Password");
        /*alertDialog.setMessage("Please fill all information");*/

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_pwd = inflater.inflate(R.layout.activity_ubah_password, null);

        final MaterialEditText passwordlama = (MaterialEditText)layout_pwd.findViewById(R.id.passwordlama);
        final MaterialEditText passwordbaru = (MaterialEditText)layout_pwd.findViewById(R.id.passwordbaru);
        final MaterialEditText konfirmasi = (MaterialEditText)layout_pwd.findViewById(R.id.konfirmasi);

        alertDialog.setView(layout_pwd);
        alertDialog.setIcon(R.drawable.ic_secure);

        //Button
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ubah password disini

                final android.app.AlertDialog waitingDialog = new SpotsDialog(AccountActivity.this);
                waitingDialog.show();

                //Check password lama
                if(passwordlama.getText().toString().equals(Common.currentMahasiswa.getPassword())){
                    //Check New Password and Konfirm
                    if(passwordbaru.getText().toString().equals(konfirmasi.getText().toString())){
                        Map<String,Object> passwordUpdate = new HashMap<>();
                        passwordUpdate.put("Password",passwordbaru.getText().toString());

                        //Make Update
                        DatabaseReference mahasiswa = FirebaseDatabase.getInstance().getReference("Mahasiswa");
                        mahasiswa.child(Common.currentMahasiswa.getNpm())
                                .updateChildren(passwordUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        waitingDialog.dismiss();
                                        Toast.makeText(AccountActivity.this, "Password telah dirubah",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(AccountActivity.this, "",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        waitingDialog.dismiss();
                        Toast.makeText(AccountActivity.this,"Password baru tidak cocok", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    waitingDialog.dismiss();
                    Toast.makeText(AccountActivity.this,"Password lama salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}
