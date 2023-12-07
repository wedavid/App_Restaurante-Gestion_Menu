package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro_Login extends AppCompatActivity {

    private EditText nombre, cedula, direccion, telefono, correo, password;
    private Button inicio, guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro__login);

        nombre = findViewById(R.id.et_nomb);
        cedula = findViewById(R.id.et_cedula);
        direccion = findViewById(R.id.et_direccion);
        telefono = findViewById(R.id.et_tel);
        correo = findViewById(R.id.et_email);
        password = findViewById(R.id.et_pass);
        guardar = findViewById(R.id.et_save);
        inicio = findViewById(R.id.et_butini);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatosEnBaseDeDatos();
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Volver al menú de inicio
                Intent intent = new Intent(Registro_Login.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void guardarDatosEnBaseDeDatos() {
        String nom = nombre.getText().toString();
        String cedu = cedula.getText().toString();
        String dir = direccion.getText().toString();
        String tel = telefono.getText().toString();
        String mail = correo.getText().toString();
        String pass = password.getText().toString();


        if (nom.isEmpty() || cedu.isEmpty() ||dir.isEmpty() || tel.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Llamamos al método para guardar los datos
            guardarRegistro(nom, cedu, dir, tel, mail, pass);
        }
    }

    private void guardarRegistro(String nom, String cedu, String dir, String tel, String mail, String pass) {
        AdminSqlite admin = new AdminSqlite(Registro_Login.this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put(AdminSqlite.COLUMN_NOMBRE, nom);
        datos.put(AdminSqlite.COLUMN_CEDULA, cedu);
        datos.put(AdminSqlite.COLUMN_DIRECCION, dir);
        datos.put(AdminSqlite.COLUMN_TELEFONO, tel);
        datos.put(AdminSqlite.COLUMN_CORREO, mail);
        datos.put(AdminSqlite.COLUMN_PASSWORD, pass);
        db.insert(AdminSqlite.TABLE_USUARIOS, null, datos);

        Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
        nombre.setText("");
        cedula.setText("");
        direccion.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
    }
}
