package com.company.privan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button singIn;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Привязываем кнопки к переменным
        singIn=findViewById(R.id.sing);
        reg=findViewById(R.id.reg);

        //Устанавливаем слушателя на кнопку
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Создаем намерение,в параметрах задаем куда нужно нас переправить
                Intent intent=new Intent(MainActivity.this,sing_in.class);
                //Запускаем намерение
                startActivity(intent);
            }
        });
        //Устанавливаем слушателя на кнопку
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Создаем намерение,в параметрах задаем куда нужно нас переправить
                Intent intent=new Intent(MainActivity.this,register.class);
                //Запускаем намерение
                startActivity(intent);
            }
        });
    }
}