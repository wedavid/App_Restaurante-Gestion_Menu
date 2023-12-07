package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_Admin extends AppCompatActivity {

    private EditText nombre, cedula, direccion, telefono, correo, password;
    private Button guardar;
    private AdminSqlite adminSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__admin);

        nombre = findViewById(R.id.et_admin_nomb);
        cedula = findViewById(R.id.et_admin_cedula);
        direccion = findViewById(R.id.et_admin_direccion);
        telefono = findViewById(R.id.et_admin_tel);
        correo = findViewById(R.id.et_admin_email);
        password = findViewById(R.id.et_admin_pass);
        guardar = findViewById(R.id.et_admin_save);

        adminSqlite = new AdminSqlite(this);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreStr = nombre.getText().toString();
                String cedulaStr = cedula.getText().toString();
                String direccionStr = direccion.getText().toString();
                String telefonoStr = telefono.getText().toString();
                String correoStr = correo.getText().toString();
                String passwordStr = password.getText().toString();

                if (nombreStr.isEmpty() || cedulaStr.isEmpty() || direccionStr.isEmpty() ||
                        telefonoStr.isEmpty() || correoStr.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {

                    insertarAdmin(nombreStr, cedulaStr, direccionStr, telefonoStr, correoStr, passwordStr);

                    Toast.makeText(getApplicationContext(), "Administrador registrado exitosamente", Toast.LENGTH_SHORT).show();

                    nombre.getText().clear();
                    cedula.getText().clear();
                    direccion.getText().clear();
                    telefono.getText().clear();
                    correo.getText().clear();
                    password.getText().clear();
                }
            }
        });
    }

    private void insertarAdmin(String nombre, String cedula, String direccion, String telefono, String correo, String password) {
        SQLiteDatabase db = adminSqlite.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AdminSqlite.COLUMN_ADMIN_NOMBRE, nombre);
        values.put(AdminSqlite.COLUMN_ADMIN_CEDULA, cedula);
        values.put(AdminSqlite.COLUMN_ADMIN_DIRECCION, direccion);
        values.put(AdminSqlite.COLUMN_ADMIN_TELEFONO, telefono);
        values.put(AdminSqlite.COLUMN_ADMIN_CORREO, correo);
        values.put(AdminSqlite.COLUMN_ADMIN_PASSWORD, password);

        db.insert(AdminSqlite.TABLE_ADMIN, null, values);
        db.close();
    }
}
