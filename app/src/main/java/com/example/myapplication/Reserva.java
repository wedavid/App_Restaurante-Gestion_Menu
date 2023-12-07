package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Reserva extends AppCompatActivity {
    private EditText can_user, fecha, hora, id_cedula2;
    private Button btn_fecha1, btn_hora1, guardar;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);

        can_user = findViewById(R.id.et_user);
        fecha = findViewById(R.id.et_fecha);
        hora = findViewById(R.id.et_hora);
        id_cedula2 = findViewById(R.id.cedula2);
        btn_fecha1 = findViewById(R.id.btn_fecha);
        btn_hora1 = findViewById(R.id.btn_hora);
        guardar = findViewById(R.id.btn_guardar);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        btn_fecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btn_hora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatosEnBaseDeDatos();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        fecha.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = hourOfDay + ":" + minute;
                        hora.setText(selectedTime);
                    }
                },
                hour, minute, true
        );
        timePickerDialog.show();
    }

    private void guardarDatosEnBaseDeDatos() {
        String cedula = id_cedula2.getText().toString();
        String user = can_user.getText().toString();
        String dat_fecha = fecha.getText().toString();
        String dat_hora = hora.getText().toString();

        if (user.isEmpty() || dat_fecha.isEmpty() || dat_hora.isEmpty() || cedula.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            int usuarioId = obtenerIdUsuario(cedula);
            if (usuarioId != -1) {
                guardarRegistro(usuarioId, user, dat_fecha, dat_hora);
            } else {
                Toast.makeText(this, "El usuario con la cédula proporcionada no existe", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int obtenerIdUsuario(String cedula) {
        AdminSqlite admin = new AdminSqlite(Reserva.this);
        SQLiteDatabase db = admin.getReadableDatabase();

        String[] columns = {AdminSqlite.COLUMN_ID};
        String selection = AdminSqlite.COLUMN_CEDULA + " = ?";
        String[] selectionArgs = {cedula};

        Cursor cursor = db.query(AdminSqlite.TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(AdminSqlite.COLUMN_ID));
            cursor.close();
            return userId;
        }

        return -1;
    }

    private void guardarRegistro(int userId, String user, String dat_fecha, String dat_hora) {
        AdminSqlite admin = new AdminSqlite(Reserva.this);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put(AdminSqlite.COLUMN_USUARIO_ID, userId);
        datos.put(AdminSqlite.COLUMN_CANTIDAD_USUARIOS, user);
        datos.put(AdminSqlite.COLUMN_HORA, dat_hora);
        datos.put(AdminSqlite.COLUMN_FECHA, dat_fecha);

        db.insert(AdminSqlite.TABLE_RESERVA, null, datos);

        Toast.makeText(getApplicationContext(), "Reserva Registrada", Toast.LENGTH_SHORT).show();
        can_user.setText("");
        fecha.setText("");
        hora.setText("");
        id_cedula2.setText("");
    }
}

