package com.dotcom.maptestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListofOnlineViewHolder extends RecyclerView.ViewHolder {
    public TextView text_mail;

    public ListofOnlineViewHolder(@NonNull View itemView) {
        super(itemView);
        text_mail=(TextView)itemView.findViewById(R.id.text_mail);
    }
}
