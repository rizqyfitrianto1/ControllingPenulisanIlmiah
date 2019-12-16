package com.example.rizqy.aplikasi3;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.rizqy.Common.Common;
import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.Model.Request;
import com.example.rizqy.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class DosenMahasiswa1 extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference requests;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String mahasiswaId = "";
    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_mahasiswa1);

        getSupportActionBar().setTitle("Progress Report Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
        if(getIntent() != null)
            mahasiswaId = getIntent().getStringExtra("MahasiswaId");
        if(!mahasiswaId.isEmpty() && mahasiswaId != null)
        {
            if (Common.isConnectedToInterner(getBaseContext())) {
                loadMahasiswa(mahasiswaId);
            }else {
                Toast.makeText(DosenMahasiswa1.this,"Cek Koneksi Internet",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadMahasiswa(String mahasiswaId){
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.cart_layout,
                OrderViewHolder.class,
                requests.orderByChild("npm")
                        .equalTo(mahasiswaId)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.Tanggal.setText(model.getTanggal());
                viewHolder.Judul.setText(model.getJudul());
                viewHolder.Subjudul.setText(model.getSubjudul());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }


    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DosenMahasiswa1.this);
        alertDialog.setTitle("Report Progress");
        alertDialog.setMessage("Please fill all information");

        LayoutInflater inflater = LayoutInflater.from(this);
        View form = inflater.inflate(R.layout.new_layout, null);

        final MaterialEditText Tanggal = (MaterialEditText)form.findViewById(R.id.tanggal);
        final MaterialEditText Judul = (MaterialEditText)form.findViewById(R.id.judul);
        final MaterialEditText Subjudul = (MaterialEditText)form.findViewById(R.id.subjudul);

        alertDialog.setView(form);
        alertDialog.setIcon(R.drawable.ic_report);

        //Button
        alertDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Tanggal.getText().toString(),
                        Judul.getText().toString(),
                        Subjudul.getText().toString(),
                        Common.currentMahasiswa.getNpm()
                );
                //Submit to firebase
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
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
