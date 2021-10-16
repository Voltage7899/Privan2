package com.company.privan2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView price;
    ImageView imageView;

    public ItemViewHolder(View view) {
        super(view);
        name = view.findViewById(R.id.name_element);
        price = view.findViewById(R.id.price_element);
        imageView = view.findViewById(R.id.image_element);
    }
}
