package com.example.rizqy.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rizqy.Interface.ItemClickListener;
import com.example.rizqy.aplikasi3.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Tanggal;
    public TextView Judul;
    public TextView Subjudul;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);
        Tanggal = (TextView)itemView.findViewById(R.id.tanggal);
        Judul = (TextView)itemView.findViewById(R.id.judul);
        Subjudul = (TextView)itemView.findViewById(R.id.subjudul);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onClick(view,getAdapterPosition(),false);
    }

}
