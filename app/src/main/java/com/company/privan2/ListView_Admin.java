package com.company.privan2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ListView_Admin extends AppCompatActivity {
    //Переменная Обновляемый список
    private RecyclerView recyclerView;
    private Button add_new;

    private DatabaseReference database;
    //Переменная держателя образа элемента
    private ItemViewHolder itemViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view__admin);

        //Привязываем обновляемый список к переменной
        recyclerView=findViewById(R.id.recyclerView_admin);
        add_new=findViewById(R.id.new_admin);

        //Кнопка для перехода на добавление товара
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListView_Admin.this,Add_Sweat.class);
                startActivity(intent);
            }
        });

        //Иницилизация обновляемого списка
        initRecyclerView();
    }

    private void initRecyclerView() {
        //Устанавливаем расположение списка
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Декорация для разделения элементов
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



        //Штука которая не понадобилась
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Получаем ссылку на таблицу с товаром
        database= FirebaseDatabase.getInstance().getReference().child("Sweat");
        //Прописываем настройки для обновляемого списка,заточенного для Firebase,он из отдельной библиотеки подключенной в градле,последняя имплементация
        FirebaseRecyclerOptions<Sweat> options=new FirebaseRecyclerOptions.Builder<Sweat>()//Грубо говоря,данные поступающие из базы конвертируются в тип объекта Sweat
                .setQuery(database,Sweat.class).build();
        //Далее мы прописываем адаптер,как и куда вставляются данные из объекта Sweat
        FirebaseRecyclerAdapter<Sweat, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Sweat, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Sweat model) {
                //Собественно сдесь и вставляются
                holder.name.setText("Название: "+model.getName());
                holder.price.setText("Цена: "+model.getPrice());
                holder.imageView.setImageURI(Uri.parse(model.getImage()));//тут мы конвертируем стринговую переменную в URI,это для картинки,так как мы не можем хранить в бд ничего кроме стрингов и интов,то мы конвертировали URI в стринг при добавление в бд,в активити  Add_Sweat
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //Прописываем обертку,как она будет выглядеть
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element,parent,false);//в данном случае прообраз это лист элемент
                itemViewHolder=new ItemViewHolder(view);
                //Пихаем в переменную обертки созданную нами обертку,чуть выше и возвращаем ее.
                return itemViewHolder;
            }
        };
        recyclerView.setAdapter(adapter);//Устанавливаем адаптер
        adapter.startListening();//на адаптер устанавливаем слушатель,когда данные будут меняться,это сразу отобразиться,воть
        //Все тоже самое и в списке для юзера

    }

}
