package com.example.rizqy.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.aplikasi3.R;

public class DosenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Nama;
    public TextView Email;
    public TextView Notelepon;


    private ItemClickListener itemClickListener;


    public DosenViewHolder(View itemView) {
        super(itemView);

        Nama = (TextView)itemView.findViewById(R.id.nama);
        Notelepon = (TextView)itemView.findViewById(R.id.notelepon);
        Email = (TextView)itemView.findViewById(R.id.email);

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
