package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Dialo_mesas extends DialogFragment {
    private EditText etSillas;
    private EditText etMesas;
    private Button guardar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialo_mesas, container, false);

        etSillas = view.findViewById(R.id.et_sillas);
        etMesas = view.findViewById(R.id.et_mesas);
        guardar = view.findViewById(R.id.but2_guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatosEnBaseDeDatos();
            }
        });

        return view;
    }

    private void guardarDatosEnBaseDeDatos() {
        String sillas = etSillas.getText().toString();
        String mesas = etMesas.getText().toString();

        if (sillas.isEmpty() || mesas.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            guardarRegistro(sillas, mesas);
        }
    }

    private void guardarRegistro(String sillas, String mesas) {
        AdminSqlite admin = new AdminSqlite(requireContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put(AdminSqlite.COLUMN_NUMERO_SILLAS, sillas);
        datos.put(AdminSqlite.COLUMN_NUMERO_MESAS, mesas);

        // Insertamos en la tabla TABLE_MESAS
        db.insert(AdminSqlite.TABLE_MESAS, null, datos);

        Toast.makeText(requireContext(), "Sillas y Mesas Registradas", Toast.LENGTH_SHORT).show();
        etSillas.setText("");
        etMesas.setText("");
    }
}
