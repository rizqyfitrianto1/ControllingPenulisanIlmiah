package com.example.rizqy.aplikasi3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Model.Mahasiswa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    EditText editTextNPM, editTextPassword;
    Button buttonMasuk;
    CheckBox cbk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        getSupportActionBar().setTitle("Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNPM = findViewById(R.id.edittextnpm);
        editTextPassword = findViewById(R.id.edittextpassword);
        buttonMasuk = findViewById(R.id.masuk);
        cbk = findViewById(R.id.cbk);

        //Init Paper
        Paper.init(this);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_Mahasiswa = database.getReference("Mahasiswa");

        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInterner(getBaseContext())) {
                    //Save user & password
                    if (cbk.isChecked()) {
                        Paper.book().write(Common.MHS_KEY, editTextNPM.getText().toString());
                        Paper.book().write(Common.PWD_KEY, editTextPassword.getText().toString());
                    }


                        final ProgressDialog mDialog = new ProgressDialog(Login.this);
                        mDialog.setMessage("Tolong tunggu...");
                        mDialog.show();

                        table_Mahasiswa.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Chech if user not exist in database
                                if (dataSnapshot.child(editTextNPM.getText().toString()).exists()) {
                                    //get user information
                                    mDialog.dismiss();
                                    Mahasiswa mahasiswa = dataSnapshot.child(editTextNPM.getText().toString()).getValue(Mahasiswa.class);
                                    mahasiswa.setNpm(editTextNPM.getText().toString());//set NPM
                                    if (mahasiswa.getPassword().equals(editTextPassword.getText().toString())) {
                                        {
                                            Intent homeIntent = new Intent(Login.this, Main.class);
                                            Common.currentMahasiswa = mahasiswa;
                                            startActivity(homeIntent);
                                            finish();

                                            table_Mahasiswa.removeEventListener(this);
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "Password yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(Login.this, "NPM tidak terdaftar di Database ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                }else {
                    Toast.makeText(Login.this, "Cek Koneksi Internet!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
