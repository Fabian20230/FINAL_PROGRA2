package gt.edu.umg.final_progra2.baseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class DbDatos extends DbHelper {

    public DbDatos(@Nullable Context context) {
        super(context);
    }

    // Método para insertar una nueva actividad al aire libre
    public long insertaDatos(String actividad, double inicioLatitud, double inicioLongitud, double finLatitud, double finLongitud, String horaInicio, String horaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("actividad", actividad);
        values.put("inicio_latitud", inicioLatitud);
        values.put("inicio_longitud", inicioLongitud);
        values.put("fin_latitud", finLatitud);
        values.put("fin_longitud", finLongitud);
        values.put("hora_inicio", horaInicio);
        values.put("hora_fin", horaFin);
        return db.insert(TABLE_ACTIVITIES, null, values);
    }

    // Método para actualizar el registro de fin de una actividad existente
    public boolean updateEndData(long id, double finLatitud, double finLongitud, String horaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fin_latitud", finLatitud);
        values.put("fin_longitud", finLongitud);
        values.put("hora_fin", horaFin);

        int rowsAffected = db.update(TABLE_ACTIVITIES, values, "id = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    // Método para obtener el último ID insertado (último registro de inicio)
    public long getLastInsertId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_ACTIVITIES + " ORDER BY id DESC LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            cursor.close();
            return id;
        }
        return -1; // Retorna -1 si no se encontró ningún registro
    }

    // Método para obtener todas las actividades registradas
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES + " ORDER BY hora_inicio ASC", null);
    }
}
