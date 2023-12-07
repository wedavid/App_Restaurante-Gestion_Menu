package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPlatos = findViewById(R.id.listViewProductos);
        mostrarProductos();
    }

    private void mostrarProductos() {
        AdminSqlite admin = new AdminSqlite(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        // Consulta para obtener información de los platos
        String query = "SELECT " +
                AdminSqlite.COLUMN_NOMBRE_PLATO + ", " +
                AdminSqlite.COLUMN_DETALLE_PLATO + ", " +
                AdminSqlite.COLUMN_PRECIO_PLATO +
                " FROM " + AdminSqlite.TABLE_PLATOS;

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> listaPlatos = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombre_plato = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_NOMBRE_PLATO));
                String detalle_plato = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_DETALLE_PLATO));
                String precio_plato = cursor.getString(cursor.getColumnIndex(AdminSqlite.COLUMN_PRECIO_PLATO));


                String platoInfo =
                         nombre_plato + "\n" +
                          detalle_plato + "\n" +
                           "$" + precio_plato + "\n";

                listaPlatos.add(platoInfo);
            } while (cursor.moveToNext());

            cursor.close();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lista_platos_item, R.id.textViewNombrePlato, listaPlatos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textViewNombrePlato = view.findViewById(R.id.textViewNombrePlato);
                TextView textViewDetallePlato = view.findViewById(R.id.textViewDetallePlato);
                TextView textViewPrecioPlato = view.findViewById(R.id.textViewPrecioPlato);

                String[] partesPlato = listaPlatos.get(position).split("\n");

                textViewNombrePlato.setText(partesPlato[0]);
                textViewDetallePlato.setText(partesPlato[1]);


                textViewPrecioPlato.setText(partesPlato[2]);

                return view;
            }
        };
        listViewPlatos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.init) {

            Toast.makeText(this, "Ya estás en la pantalla principal", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.reser) {
            Intent intent = new Intent(this, Reserva.class);
            startActivity(intent);
        } else if (id == R.id.consul) {
            Intent intent = new Intent(this, Consulta.class);
            startActivity(intent);
        } else if (id == R.id.admin) {
            Intent intent = new Intent(this, Admin.class);
            startActivity(intent);
        } else if (id == R.id.close) {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
