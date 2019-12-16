package com.example.rizqy.aplikasi3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.Model.Dosen;
import com.example.rizqy.ViewHolder.DosenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DospemCard extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dosen;

    RecyclerView recyclerdosen;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Dosen, DosenViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dospem_card);

        getSupportActionBar().setTitle("Dosen Pembimbing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        dosen = database.getReference("Dosen");

        //Load Mahasiswa
        recyclerdosen = (RecyclerView) findViewById(R.id.recyclerdosen);
        recyclerdosen.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerdosen.setLayoutManager(layoutManager);
        if (Common.isConnectedToInterner(getBaseContext()))
        {
            loadDosen(Common.currentMahasiswa.getKelas());
        }else {
            Toast.makeText(DospemCard.this,"Cek Koneksi Internet",Toast.LENGTH_SHORT).show();
        }
    }
    void loadDosen(String Kelas) {
        adapter = new FirebaseRecyclerAdapter<Dosen, DosenViewHolder>(
                Dosen.class,
                R.layout.dosen_item,
                DosenViewHolder.class,
                dosen.orderByChild("Kelas")
                     .equalTo(Kelas)
        ) {

            @Override
            protected void populateViewHolder(DosenViewHolder viewHolder, Dosen model, int position) {
                viewHolder.Nama.setText(model.getNama());
                viewHolder.Notelepon.setText(model.getNotelepon());
                viewHolder.Email.setText(model.getEmail());
                final Dosen clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get DosenId and send to new Activity
                        Intent listmahasiswa = new Intent(DospemCard.this, MahasiswaBimbingan.class);
                        //Karena DosenId adalah kunci, maka kita ambil kunci dari item adapter
                        listmahasiswa.putExtra("DosenId",adapter.getRef(position).getKey());
                        startActivity(listmahasiswa);
                    }
                });
            }
        };
        recyclerdosen.setAdapter(adapter);
    }
}
