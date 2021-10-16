package com.company.privan2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class sing_in extends AppCompatActivity {

    private EditText phone_sign,pass_sign;
    private Button user_sign,admin_sign;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        database= FirebaseDatabase.getInstance().getReference();

        phone_sign=findViewById(R.id.phone_sign);
        pass_sign=findViewById(R.id.pass_sign);
        user_sign=findViewById(R.id.sign_sign);
        admin_sign=findViewById(R.id.admin_sign);

        //Поместил код в отдельные функции,чтобы проще читалось
        user_sing_in();
        admin_sing_in();


    }


    private void user_sing_in(){
        user_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone_field=phone_sign.getText().toString();
                String pass_field = pass_sign.getText().toString();

                if(TextUtils.isEmpty(phone_field)&&TextUtils.isEmpty(pass_field)){
                    Toast.makeText(sing_in.this, "Введите все данные", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("User").child(phone_field).exists()){//Проверяем,есть ли пользователь с таким номером,введенным из поля телефона
                                //если есть,то устанавливаем полученные данные в объект типа юзер
                                User userCurrentData=snapshot.child("User").child(phone_field).getValue(User.class);
                                //Потом устанавливаем данные в глобальную переменную пользователя
                                CurrentUser.currentUser=userCurrentData;
                                //Логи для просмотра,какие данные приходят
                                Log.d(TAG,"Переменная имени  "+CurrentUser.currentUser.name);
                                Log.d(TAG,"Переменная телефона "+CurrentUser.currentUser.phone);
                                Log.d(TAG,"Переменная пароля "+CurrentUser.currentUser.pass);
                                //Проверка на то,правильно ли введены поля
                                if(userCurrentData.phone.equals(phone_field) && userCurrentData.pass.equals(pass_field)){
                                    //если все окей,то переносит на страницу для юзеров со списком товаров
                                    Toast.makeText(sing_in.this, "Вы вошли как Юзер", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(sing_in.this,ListView.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(sing_in.this, "Wrong Data", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void admin_sing_in(){
        //Для АДМИНА все то же самое,что и для Юзера
        admin_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone_field=phone_sign.getText().toString();
                String pass_field = pass_sign.getText().toString();

                if(TextUtils.isEmpty(phone_field)&&TextUtils.isEmpty(pass_field)){
                    Toast.makeText(sing_in.this, "Введите все данные", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Admin").child(phone_field).exists()){

                                User userCurrentData=snapshot.child("Admin").child(phone_field).getValue(User.class);
                                CurrentUser.currentUser=userCurrentData;

                                Log.d(TAG,"Переменная имени  "+CurrentUser.currentUser.name);
                                Log.d(TAG,"Переменная телефона "+CurrentUser.currentUser.phone);
                                Log.d(TAG,"Переменная пароля "+CurrentUser.currentUser.pass);

                                if(userCurrentData.phone.equals(phone_field) && userCurrentData.pass.equals(pass_field)){
                                    Toast.makeText(sing_in.this, "Вы вошли как админ", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(sing_in.this,ListView_Admin.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(sing_in.this, "Wrong Data", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}