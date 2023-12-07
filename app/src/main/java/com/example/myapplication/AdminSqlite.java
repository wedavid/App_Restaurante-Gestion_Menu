package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSqlite extends SQLiteOpenHelper {
    private static final String DB_NAME = "DB";
    private static final int DB_VERSION = 1;

    // Tabla "usuarios"
    static final String TABLE_USUARIOS = "usuarios";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NOMBRE = "nombre";
    static final String COLUMN_CEDULA = "cedula";
    static final String COLUMN_DIRECCION = "direccion";
    static final String COLUMN_TELEFONO = "telefono";
    static final String COLUMN_CORREO = "correo";
    static final String COLUMN_PASSWORD = "password";

    // Tabla "administrador"
    static final String TABLE_ADMIN = "admin_usuarios";
    static final String COLUMN_ADMIN_ID = "admin_id";
    static final String COLUMN_ADMIN_NOMBRE = "admin_nombre";
    static final String COLUMN_ADMIN_CEDULA = "admin_cedula";
    static final String COLUMN_ADMIN_DIRECCION = "admin_direccion";
    static final String COLUMN_ADMIN_TELEFONO = "admin_telefono";
    static final String COLUMN_ADMIN_CORREO = "admin_correo";
    static final String COLUMN_ADMIN_PASSWORD = "admin_password";

    // Tabla "mesas"
    static final String TABLE_MESAS = "mesas";
    static final String COLUMN_MESA_ID = "mesa_id";
    static final String COLUMN_NUMERO_SILLAS = "numero_sillas";
    static final String COLUMN_NUMERO_MESAS = "numero_mesas";

    // Tabla "reserva"
    static final String TABLE_RESERVA = "reserva";
    static final String COLUMN_RESERVA_ID = "reserva_id";
    static final String COLUMN_USUARIO_ID = "usuario_id";
    static final String COLUMN_CANTIDAD_USUARIOS = "cantidad_user";
    static final String COLUMN_HORA = "hora";
    static final String COLUMN_FECHA = "fecha";

    // Tabla "platos"
    static final String TABLE_PLATOS = "platos";
    static final String COLUMN_PLATO_ID = "plato_id";
    static final String COLUMN_NOMBRE_PLATO = "nombre_plato";
    static final String COLUMN_DETALLE_PLATO = "detalle_plato";
    static final String COLUMN_PRECIO_PLATO = "precio_plato";

    public AdminSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla "usuarios"
        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " VARCHAR(100) NOT NULL, " +
                COLUMN_CEDULA + " VARCHAR(100) NOT NULL, " +
                COLUMN_DIRECCION + " VARCHAR(100) NOT NULL, " +
                COLUMN_TELEFONO + " VARCHAR(100) NOT NULL, " +
                COLUMN_CORREO + " VARCHAR(100) NOT NULL, " +
                COLUMN_PASSWORD + " VARCHAR(100) NOT NULL);");

        // Tabla "administrador"
        db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" +
                COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADMIN_NOMBRE + " VARCHAR(100) NOT NULL, " +
                COLUMN_ADMIN_CEDULA + " VARCHAR(100) NOT NULL, " +
                COLUMN_ADMIN_DIRECCION + " VARCHAR(100) NOT NULL, " +
                COLUMN_ADMIN_TELEFONO + " VARCHAR(100) NOT NULL, " +
                COLUMN_ADMIN_CORREO + " VARCHAR(100) NOT NULL, " +
                COLUMN_ADMIN_PASSWORD + " VARCHAR(100) NOT NULL);");

        // Tabla "mesas"
        db.execSQL("CREATE TABLE " + TABLE_MESAS + " (" +
                COLUMN_MESA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NUMERO_SILLAS + " VARCHAR(100) NOT NULL, " +
                COLUMN_NUMERO_MESAS + " VARCHAR(100) NOT NULL);");

        // Tabla "reserva"
        db.execSQL("CREATE TABLE " + TABLE_RESERVA + " (" +
                COLUMN_RESERVA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_ID + " INTEGER, " +
                COLUMN_CANTIDAD_USUARIOS + " INTEGER, " +
                COLUMN_HORA + " TIME, " +
                COLUMN_FECHA + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + " (" + COLUMN_ID + "));");

        // Tabla "platos"
        db.execSQL("CREATE TABLE " + TABLE_PLATOS + " (" +
                COLUMN_PLATO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE_PLATO + " VARCHAR(100) NOT NULL, " +
                COLUMN_DETALLE_PLATO + " TEXT NOT NULL, " +
                COLUMN_PRECIO_PLATO + " REAL NOT NULL);");

        // Insertar usuario administrador de ejemplo
        insertarAdminEjemplo(db);
    }

    private void insertarAdminEjemplo(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_NOMBRE, "Administrador");
        values.put(COLUMN_ADMIN_CEDULA, "111111");
        values.put(COLUMN_ADMIN_DIRECCION, "123456789");
        values.put(COLUMN_ADMIN_TELEFONO, "11111");
        values.put(COLUMN_ADMIN_CORREO, "superadmin");
        values.put(COLUMN_ADMIN_PASSWORD, "2023");

        db.insert(TABLE_ADMIN, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
