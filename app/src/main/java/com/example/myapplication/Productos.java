package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Productos extends AppCompatActivity {

    private EditText editTextNombre, editTextDetalle, editTextPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDetalle = findViewById(R.id.editTextDetalle);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnMenu = findViewById(R.id.btnmenu);
        Button btnConsultaProductos = findViewById(R.id.menucon_productos);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarPlato();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Productos.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnConsultaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí inicia la nueva actividad al hacer clic en el botón
                Intent intent = new Intent(Productos.this, ConsultaProductos.class);
                startActivity(intent);
            }
        });
    }

    private void guardarPlato() {
        String nombre = editTextNombre.getText().toString().trim();
        String detalle = editTextDetalle.getText().toString().trim();
        String precioStr = editTextPrecio.getText().toString().trim();

        if (nombre.isEmpty() || detalle.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);

        guardarEnBaseDeDatos(nombre, detalle, precio);

        editTextNombre.setText("");
        editTextDetalle.setText("");
        editTextPrecio.setText("");
    }

    private void guardarEnBaseDeDatos(String nombre, String detalle, double precio) {
        AdminSqlite adminSqlite = new AdminSqlite(this);
        SQLiteDatabase db = adminSqlite.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AdminSqlite.COLUMN_NOMBRE_PLATO, nombre);
        values.put(AdminSqlite.COLUMN_DETALLE_PLATO, detalle);
        values.put(AdminSqlite.COLUMN_PRECIO_PLATO, precio);

        long result = db.insert(AdminSqlite.TABLE_PLATOS, null, values);

        if (result != -1) {
            Toast.makeText(this, "Plato guardado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar el plato", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
