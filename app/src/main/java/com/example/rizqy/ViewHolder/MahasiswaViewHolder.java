package com.example.rizqy.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.aplikasi3.R;

public class MahasiswaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Nama;
    public TextView Kelas;
    public TextView Jurusan;

    private ItemClickListener itemClickListener;


    public MahasiswaViewHolder(View itemView) {
        super(itemView);

        Nama = (TextView)itemView.findViewById(R.id.nama);
        Kelas = (TextView)itemView.findViewById(R.id.kelas);
        Jurusan = (TextView)itemView.findViewById(R.id.jurusan);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
