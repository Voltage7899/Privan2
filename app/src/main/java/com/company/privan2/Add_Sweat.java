package com.company.privan2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Add_Sweat extends AppCompatActivity {

    //Переменые для записи данных из полей и путь для картинки(URI)
    private String id, name, price, image;
    private Uri imageUri;
    //Переменные полей
    private EditText name_field, price_field;
    private ImageView img_field;
    private Button add_db;
    //ссылку ты уже знаешь,умничка моя
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__sweat);

        name_field = findViewById(R.id.name_add);
        price_field = findViewById(R.id.price_add);
        img_field = findViewById(R.id.image_add);

        add_db = findViewById(R.id.add_db);

        add_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Как только прошли проверку на заполнение полей и картинки,то добавляем данные в бд
                name = name_field.getText().toString();
                price = price_field.getText().toString();

                if (imageUri == null) {
                    Toast.makeText(Add_Sweat.this, "Добавьте картинку", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(Add_Sweat.this, "Добавьте название", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(price)) {
                    Toast.makeText(Add_Sweat.this, "Добавьте цену", Toast.LENGTH_SHORT).show();
                } else {
                    //Функция для добавления
                    AddInDataBase();
                }


            }

        });


        img_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Короче,прописываем намерение и в качестве параметра указываем что нам нужно получить
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //Устанавливаем тип данных получаемых
                intent.setType("image/*");
                //Запускаем намерение с возвращаемым результатом и вернется нам все в онАктивитиРезалт
                startActivityForResult(Intent.createChooser(intent, "pickImage"), 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //А вот и оно,ты его нашел
        if (resultCode == RESULT_OK && requestCode == 1) {//Проверка на удачное получение данных,все прошло гладко
            //Вытаскиваем ссылку на картинку из даты
            imageUri = data.getData();
            img_field.setImageURI(imageUri);//Устанавливаем картинку
            image = imageUri.toString();//Переводим Uri в стринг,чтобы запихнуть в базу данных,ты поймешь о чем я
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);//А тут мы даем постоянный допуск к этой картинке,иначе картинка не отобразится через несколько минут,ну или когда ты сменишь активити или перезапустишь приложение,вот попа будет,да

        }

    }
    private void AddInDataBase()
    {
        //Сдесь мы устанавливаем нужный айдишник и приводим его в инту
        id=Integer.toString((int) (1+Math.random()*1000));

        Log.d(TAG,"Айдишник при добавлении "+id);

        database= FirebaseDatabase.getInstance().getReference("Sweat");

        HashMap<String,Object> SweatMap = new HashMap<>();

        SweatMap.put("id",id);
        SweatMap.put("name",name);
        SweatMap.put("price",price);
        SweatMap.put("image",image);
//Это мы уже проходили,но на всякий случай,здесь мы пихаем данные в бд через Хэшмэп и делаем проверку на результат
        database.child(id).updateChildren(SweatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Add_Sweat.this, "Товар добавлен", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {
                    Toast.makeText(Add_Sweat.this, "Нигга,мы в дерьме", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}