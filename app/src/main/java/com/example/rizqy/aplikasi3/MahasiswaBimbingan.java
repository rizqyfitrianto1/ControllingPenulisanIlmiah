package com.example.rizqy.aplikasi3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.Model.Mahasiswa;
import com.example.rizqy.ViewHolder.MahasiswaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MahasiswaBimbingan extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mahasiswa;

    RecyclerView recyclermahasiswa;
    RecyclerView.LayoutManager layoutManager;

    String dosenId = "";
    FirebaseRecyclerAdapter<Mahasiswa,MahasiswaViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_bimbingan);

        getSupportActionBar().setTitle("Daftar Mahasiswa Bimbingan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        mahasiswa = database.getReference("Mahasiswa");

        //Load Mahasiswa
        recyclermahasiswa = (RecyclerView)findViewById(R.id.recyclermahasiswa);
        recyclermahasiswa.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclermahasiswa.setLayoutManager(layoutManager);

        //Get Intent here
        if(getIntent() != null)
            dosenId = getIntent().getStringExtra("DosenId");
        if(!dosenId.isEmpty() && dosenId != null)
        {
            if (Common.isConnectedToInterner(getBaseContext())) {
                loadMahasiswa(dosenId);
            }else {
            Toast.makeText(MahasiswaBimbingan.this,"Cek Koneksi Internet",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loadMahasiswa(String dosenId) {
        adapter = new FirebaseRecyclerAdapter<Mahasiswa, MahasiswaViewHolder>(Mahasiswa.class,
                R.layout.mahasiswa_item,
                MahasiswaViewHolder.class,
                mahasiswa.orderByChild("Kodedospem").equalTo(dosenId)//seperti : Select * from Mahasiswa where Kodedosen =
                ) {
            @Override
            protected void populateViewHolder(MahasiswaViewHolder viewHolder, Mahasiswa model, int position) {

                viewHolder.Nama.setText(model.getNama());
                viewHolder.Kelas.setText(model.getKelas());
                viewHolder.Jurusan.setText(model.getJurusan());

                final Mahasiswa local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MahasiswaBimbingan.this,""+local.getNama(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclermahasiswa.setAdapter(adapter);
    }
}
