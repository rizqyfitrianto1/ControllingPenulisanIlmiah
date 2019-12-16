package com.example.rizqy.aplikasi3;

import  android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Model.Dosen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class DosenLogin extends AppCompatActivity {

    EditText editTextNIDN, editTextPassword;
    Button buttonMasuk;
    CheckBox cbk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_login);

        getSupportActionBar().setTitle("Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNIDN = findViewById(R.id.edittextnidn);
        editTextPassword = findViewById(R.id.edittextpassword);
        buttonMasuk = findViewById(R.id.masuk);
        cbk = findViewById(R.id.cbk);

        //Init Paper
        Paper.init(this);

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_Dosen = database.getReference("Dosen");

        buttonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInterner(getBaseContext())) {
                    //Save user & password
                    if (cbk.isChecked()) {
                        Paper.book().write(Common.DSN_KEY, editTextNIDN.getText().toString());
                        Paper.book().write(Common.PAS_KEY, editTextPassword.getText().toString());
                    }


                    final ProgressDialog mDialog = new ProgressDialog(DosenLogin.this);
                    mDialog.setMessage("Tolong tunggu...");
                    mDialog.show();

                    table_Dosen.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Chech if user not exist in database
                            if (dataSnapshot.child(editTextNIDN.getText().toString()).exists()) {
                                //get user information
                                mDialog.dismiss();
                                Dosen dosen = dataSnapshot.child(editTextNIDN.getText().toString()).getValue(Dosen.class);
                                dosen.setKodedospem(editTextNIDN.getText().toString());//set NPM
                                if (dosen.getPassword().equals(editTextPassword.getText().toString())) {
                                    {
                                        Intent homeIntent = new Intent(DosenLogin.this, DosenMain.class);
                                        Common.currentDosen = dosen;
                                        startActivity(homeIntent);
                                        finish();

                                        table_Dosen.removeEventListener(this);
                                    }
                                } else {
                                    Toast.makeText(DosenLogin.this, "Password yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(DosenLogin.this, "NIDN tidak terdaftar di Database ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }else {
                    Toast.makeText(DosenLogin.this, "Cek Koneksi Internet!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
