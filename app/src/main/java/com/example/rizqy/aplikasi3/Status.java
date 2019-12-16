package com.example.rizqy.aplikasi3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Model.Dosen;
import com.example.rizqy.Model.Mahasiswa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Status extends Activity implements View.OnClickListener {
    ImageButton Mahasiswa,Dosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Mahasiswa = (ImageButton)findViewById(R.id.mahasiswa);
        Dosen = (ImageButton)findViewById(R.id.dosen);

        Mahasiswa.setOnClickListener(this);
        Dosen.setOnClickListener(this);

        //Init Paper
        Paper.init(this);
        //Check Remember
        String mahasiswa = Paper.book().read(Common.MHS_KEY);
        String password = Paper.book().read(Common.PWD_KEY);
        if(mahasiswa != null & password !=null)
        {
            if(!mahasiswa.isEmpty() && !password.isEmpty())
                login(mahasiswa,password);
        }
        String dosen = Paper.book().read(Common.DSN_KEY);
        String pass = Paper.book().read(Common.PAS_KEY);
        if (dosen != null & pass != null)
        {
            if ((!dosen.isEmpty() && !pass.isEmpty()))
                logindosen(dosen,pass);
        }

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.mahasiswa:
                i = new Intent(this, Login.class);
                startActivity(i);
                break;
            case R.id.dosen:
                i = new Intent(this, DosenLogin.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
    private void login(final String npm, final String password) {

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_Mahasiswa = database.getReference("Mahasiswa");

        if (Common.isConnectedToInterner(getBaseContext())) {

            final ProgressDialog mDialog = new ProgressDialog(Status.this);
            mDialog.setMessage("Tolong tunggu...");
            mDialog.show();

            table_Mahasiswa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Chech if user not exist in database
                    if (dataSnapshot.child(npm).exists()) {
                        //get user information
                        mDialog.dismiss();
                        Mahasiswa mahasiswa = dataSnapshot.child(npm).getValue(Mahasiswa.class);
                        mahasiswa.setNpm(npm);//set NPM
                        if (mahasiswa.getPassword().equals(password)) {
                            {
                                Intent homeIntent = new Intent(Status.this, Main.class);
                                Common.currentMahasiswa = mahasiswa;
                                startActivity(homeIntent);
                                finish();

                                table_Mahasiswa.removeEventListener(this);
                            }
                        } else {
                            Toast.makeText(Status.this, "Password yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(Status.this, "NPM tidak terdaftar di Database ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }else {
            Toast.makeText(Status.this, "Cek Koneksi Internet!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void logindosen(final String kodedospem, final String pass) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_Dosen = database.getReference("Dosen");

        if (Common.isConnectedToInterner(getBaseContext())) {

            final ProgressDialog mDialog = new ProgressDialog(Status.this);
            mDialog.setMessage("Tolong tunggu...");
            mDialog.show();

            table_Dosen.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Chech if user not exist in database
                    if (dataSnapshot.child(kodedospem).exists()) {
                        //get user information
                        mDialog.dismiss();
                        com.example.rizqy.Model.Dosen dosen = dataSnapshot.child(kodedospem).getValue(Dosen.class);
                        dosen.setKodedospem(kodedospem);//set NPM
                        if (dosen.getPassword().equals(pass)) {
                            {
                                Intent homeIntent = new Intent(Status.this, DosenMain.class);
                                Common.currentDosen = dosen;
                                startActivity(homeIntent);
                                finish();

                                table_Dosen.removeEventListener(this);
                            }
                        } else {
                            Toast.makeText(Status.this, "Password yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(Status.this, "NIDN tidak terdaftar di Database ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }else {
            Toast.makeText(Status.this, "Cek Koneksi Internet!", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
