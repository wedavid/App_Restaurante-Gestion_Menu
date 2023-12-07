package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    private Button btnopendialo, listuser,user_admin, productos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        listuser =findViewById(R.id.bt_con_usuarios);
        user_admin =findViewById(R.id.btn_crear_admin);
        productos =findViewById(R.id.btn_productos);
        btnopendialo=findViewById(R.id.btn_mesas);
        btnopendialo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialo_mesas Dialo_mesas=new Dialo_mesas();
                Dialo_mesas.show(getSupportFragmentManager(),"dialogo");

            }
        });
        user_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, User_Admin.class);
                startActivity(intent);

            }
        });

        listuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, ListaReservasActivity.class);
                startActivity(intent);

            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, Productos.class);
                startActivity(intent);

            }
        });



    }
}