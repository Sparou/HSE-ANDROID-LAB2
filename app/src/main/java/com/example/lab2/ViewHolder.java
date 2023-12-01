package com.example.lab2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView itemTitle;

    public ViewHolder(View view) {
        super(view);
        itemTitle = view.findViewById(R.id.itemTitle);
    }

    public void bind(String title) {
        itemTitle.setText(title);
    }

}
