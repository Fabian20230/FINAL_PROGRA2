package gt.edu.umg.final_progra2.baseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2; // Aumenta la versión de la base de datos
    private static final String DB_NOMBRE = "activity_tracker.db";
    public static final String TABLE_ACTIVITIES = "tb_activities";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ACTIVITIES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "actividad TEXT, " +
                "inicio_latitud REAL, " +
                "inicio_longitud REAL, " +
                "fin_latitud REAL, " +
                "fin_longitud REAL, " +
                "hora_inicio TEXT, " +
                "hora_fin TEXT, " +
                "inicio_imagen BLOB, " +
                "fin_imagen BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        onCreate(db);
    }
}
