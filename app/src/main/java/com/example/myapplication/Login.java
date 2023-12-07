package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText usuario, clave;
    private Button login, registro;
    private RadioButton radioButtonUser, radioButtonAdmin;
    private RadioGroup radioGroup;
    private AdminSqlite adminSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuario = findViewById(R.id.log_user);
        clave = findViewById(R.id.log_pass);
        login = findViewById(R.id.btn_login);
        registro = findViewById(R.id.log_crear);
        radioButtonUser = findViewById(R.id.radioButton1);
        radioButtonAdmin = findViewById(R.id.radioButton2);
        radioGroup = findViewById(R.id.radioGroup);

        adminSqlite = new AdminSqlite(this);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad de registro
                Intent intent = new Intent(Login.this, Registro_Login.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usuario.getText().toString();
                String pass = clave.getText().toString();

                if (user.isEmpty()) {
                    usuario.setError("Usuario Vacío");
                    usuario.requestFocus();
                } else if (pass.isEmpty()) {
                    clave.setError("Contraseña Vacía");
                    clave.requestFocus();
                } else {
                    // Lógica de validación
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        // Ningún RadioButton seleccionado
                        Toast.makeText(getApplicationContext(), "Selecciona un tipo de usuario", Toast.LENGTH_SHORT).show();
                    } else {
                        if (radioButtonUser.isChecked()) {
                            // Lógica de validación para usuario
                            if (user.equals("usuario_estandar") && pass.equals("contraseña_estandar")) {
                                Toast.makeText(getApplicationContext(), "Bienvenido Usuario Estándar", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (verificarUsuario(user, pass, AdminSqlite.TABLE_USUARIOS)) {
                                Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        } else if (radioButtonAdmin.isChecked()) {
                            // Lógica de validación para administrador
                            if (user.equals("superadmin") && pass.equals("2023")) {
                                Toast.makeText(getApplicationContext(), "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                int adminId = verificarUsuarioAdmin(user, pass, AdminSqlite.TABLE_ADMIN);
                                if (adminId != -1) {
                                    Toast.makeText(getApplicationContext(), "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("adminId", adminId); // Pasa el ID del administrador a la actividad MainActivity
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean verificarUsuario(String usuario, String contraseña, String tabla) {
        SQLiteDatabase db = adminSqlite.getReadableDatabase();
        String[] projection = {AdminSqlite.COLUMN_PASSWORD};
        String selection = AdminSqlite.COLUMN_CORREO + " = ? AND " + AdminSqlite.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {usuario, contraseña};
        Cursor cursor = db.query(
                tabla,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    private int verificarUsuarioAdmin(String usuario, String contraseña, String tabla) {
        SQLiteDatabase db = adminSqlite.getReadableDatabase();
        String[] projection = {AdminSqlite.COLUMN_ADMIN_ID};
        String selection = AdminSqlite.COLUMN_ADMIN_CORREO + " = ? AND " + AdminSqlite.COLUMN_ADMIN_PASSWORD + " = ?";
        String[] selectionArgs = {usuario, contraseña};
        Cursor cursor = db.query(
                tabla,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int adminId = -1;

        if (cursor.moveToFirst()) {
            adminId = cursor.getInt(cursor.getColumnIndex(AdminSqlite.COLUMN_ADMIN_ID));
        }

        cursor.close();
        return adminId;
    }
}
