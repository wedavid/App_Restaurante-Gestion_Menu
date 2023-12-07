package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsultaProductos extends AppCompatActivity {
    private AutoCompleteTextView nombrePlatoAutoComplete;
    private EditText precio, detalle;
    private Button eliminarPlatos, guardar, consultarPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_productos);

        nombrePlatoAutoComplete = findViewById(R.id.autoCompleteTextView);
        detalle = findViewById(R.id.editTextDetalle);
        precio = findViewById(R.id.editTextPrecio);
        eliminarPlatos = findViewById(R.id.eliminar_productos);
        consultarPlatos = findViewById(R.id.consu_productos);
        guardar = findViewById(R.id.btnGuardarplatos);

        // Configurar el adaptador para el AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, obtenerNombresPlatos());
        nombrePlatoAutoComplete.setAdapter(adapter);

        consultarPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrePlatoConsulta = nombrePlatoAutoComplete.getText().toString().trim();
                consultarPlatos(nombrePlatoConsulta);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatosPlatos();
            }
        });

        eliminarPlatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPlato();
            }
        });
    }

    private void consultarPlatos(String nombrePlatoConsulta) {
        if (nombrePlatoConsulta.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del plato para consultar", Toast.LENGTH_SHORT).show();
            return;
        }

        AdminSqlite admin = new AdminSqlite(ConsultaProductos.this);
        SQLiteDatabase db = admin.getReadableDatabase();

        String[] columns = {AdminSqlite.COLUMN_NOMBRE_PLATO, AdminSqlite.COLUMN_DETALLE_PLATO, AdminSqlite.COLUMN_PRECIO_PLATO};
        String selection = AdminSqlite.COLUMN_NOMBRE_PLATO + " = ?";
        String[] selectionArgs = {nombrePlatoConsulta};

        Cursor cursor = db.query(AdminSqlite.TABLE_PLATOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            nombrePlatoAutoComplete.setText(cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_NOMBRE_PLATO)));

            detalle.setText(cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_DETALLE_PLATO)));
            precio.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(AdminSqlite.COLUMN_PRECIO_PLATO))));

            cursor.close();
        } else {
            Toast.makeText(this, "El plato no existe", Toast.LENGTH_SHORT).show();
            detalle.setText("");
            precio.setText("");
        }
    }

    private void guardarDatosPlatos() {
        String nombrePlato = nombrePlatoAutoComplete.getText().toString().trim();
        String detallePlato = detalle.getText().toString().trim();
        String precioPlatoStr = precio.getText().toString().trim();

        if (nombrePlato.isEmpty() || detallePlato.isEmpty() || precioPlatoStr.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precioPlato = Double.parseDouble(precioPlatoStr);

        AdminSqlite admin = new AdminSqlite(ConsultaProductos.this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put(AdminSqlite.COLUMN_NOMBRE_PLATO, nombrePlato);
        datos.put(AdminSqlite.COLUMN_DETALLE_PLATO, detallePlato);
        datos.put(AdminSqlite.COLUMN_PRECIO_PLATO, precioPlato);

        String whereClause = AdminSqlite.COLUMN_NOMBRE_PLATO + " = ?";
        String[] whereArgs = {nombrePlato};
        db.delete(AdminSqlite.TABLE_PLATOS, whereClause, whereArgs);

        db.insert(AdminSqlite.TABLE_PLATOS, null, datos);

        Toast.makeText(getApplicationContext(), "Plato Guardado", Toast.LENGTH_SHORT).show();

        nombrePlatoAutoComplete.setText("");
        detalle.setText("");
        precio.setText("");
    }

    private void eliminarPlato() {
        String nombrePlatoEliminar = nombrePlatoAutoComplete.getText().toString().trim();

        if (nombrePlatoEliminar.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del plato para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        AdminSqlite admin = new AdminSqlite(ConsultaProductos.this);
        SQLiteDatabase db = admin.getWritableDatabase();

        String whereClause = AdminSqlite.COLUMN_NOMBRE_PLATO + " = ?";
        String[] whereArgs = {nombrePlatoEliminar};

        int rowsDeleted = db.delete(AdminSqlite.TABLE_PLATOS, whereClause, whereArgs);

        if (rowsDeleted > 0) {
            Toast.makeText(getApplicationContext(), "Plato Eliminado", Toast.LENGTH_SHORT).show();
            nombrePlatoAutoComplete.setText("");
            detalle.setText("");
            precio.setText("");
        } else {
            Toast.makeText(this, "El plato no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] obtenerNombresPlatos() {
        AdminSqlite admin = new AdminSqlite(ConsultaProductos.this);
        SQLiteDatabase db = admin.getReadableDatabase();

        String[] columns = {AdminSqlite.COLUMN_NOMBRE_PLATO};
        Cursor cursor = db.query(AdminSqlite.TABLE_PLATOS, columns, null, null, null, null, null);

        List<String> nombresPlatos = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                nombresPlatos.add(cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_NOMBRE_PLATO)));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return nombresPlatos.toArray(new String[0]);
    }
}
