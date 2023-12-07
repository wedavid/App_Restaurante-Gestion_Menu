package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaReservasActivity extends AppCompatActivity {
    private ListView listViewReservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        listViewReservas = findViewById(R.id.listViewReservas);

        mostrarReservas();
    }

    private void mostrarReservas() {
        AdminSqlite admin = new AdminSqlite(this);
        SQLiteDatabase db = admin.getReadableDatabase();


        String query = "SELECT " +
                AdminSqlite.TABLE_USUARIOS + "." + AdminSqlite.COLUMN_NOMBRE + ", " +
                AdminSqlite.TABLE_USUARIOS + "." + AdminSqlite.COLUMN_CEDULA + ", " +
                AdminSqlite.TABLE_RESERVA + "." + AdminSqlite.COLUMN_CANTIDAD_USUARIOS + ", " +
                AdminSqlite.TABLE_RESERVA + "." + AdminSqlite.COLUMN_HORA + ", " +
                AdminSqlite.TABLE_RESERVA + "." + AdminSqlite.COLUMN_FECHA +
                " FROM " + AdminSqlite.TABLE_RESERVA +
                " INNER JOIN " + AdminSqlite.TABLE_USUARIOS +
                " ON " + AdminSqlite.TABLE_RESERVA + "." + AdminSqlite.COLUMN_USUARIO_ID +
                " = " + AdminSqlite.TABLE_USUARIOS + "." + AdminSqlite.COLUMN_ID;

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> listaReservas = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_NOMBRE));
                String cedula = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_CEDULA));
                int cantidadUsuarios = cursor.getInt(cursor.getColumnIndex(AdminSqlite.COLUMN_CANTIDAD_USUARIOS));
                String hora = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_HORA));
                String fecha = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_FECHA));

                String reservaInfo = "Nombre: " + nombre +
                        "\nCédula: " + cedula +
                        "\nCantidad de Usuarios: " + cantidadUsuarios +
                        "\nHora: " + hora +
                        "\nFecha: " + fecha + "\n";

                listaReservas.add(reservaInfo);
            } while (cursor.moveToNext());

            cursor.close();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReservas);
        listViewReservas.setAdapter(adapter);
    }
}
