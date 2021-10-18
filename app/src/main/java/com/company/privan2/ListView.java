package com.company.privan2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListView extends AppCompatActivity {
    private RecyclerView recyclerView;


    private DatabaseReference database;
    private ItemViewHolder itemViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        recyclerView=findViewById(R.id.recyclerView_User);

        initRecyclerView();
    }
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void onStart() {
        super.onStart();

        database= FirebaseDatabase.getInstance().getReference().child("Sweat");
        FirebaseRecyclerOptions<Sweat> options=new FirebaseRecyclerOptions.Builder<Sweat>()
                .setQuery(database,Sweat.class).build();
        FirebaseRecyclerAdapter<Sweat, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Sweat, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Sweat model) {
                holder.name.setText("Название: "+model.getName());
                holder.price.setText("Цена: "+model.getPrice());
                holder.imageView.setImageURI(Uri.parse(model.getImage()));
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element,parent,false);
                itemViewHolder=new ItemViewHolder(view);
                return itemViewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}