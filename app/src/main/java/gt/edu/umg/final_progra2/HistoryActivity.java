package gt.edu.umg.final_progra2;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import gt.edu.umg.final_progra2.baseDatos.DbDatos;

public class HistoryActivity extends AppCompatActivity {
    private ListView lvHistory;
    private DbDatos dbDatos;
    private ArrayList<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = findViewById(R.id.lvHistory);
        dbDatos = new DbDatos(this);
        historyList = new ArrayList<>();

        // Obtener datos de la base de datos
        Cursor cursor = dbDatos.getAllData();
        if (cursor != null) {
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    // Obtener índices de las columnas
                    int idIndex = cursor.getColumnIndex("id");
                    int actividadIndex = cursor.getColumnIndex("actividad");
                    int inicioLatIndex = cursor.getColumnIndex("inicio_latitud");
                    int inicioLongIndex = cursor.getColumnIndex("inicio_longitud");
                    int finLatIndex = cursor.getColumnIndex("fin_latitud");
                    int finLongIndex = cursor.getColumnIndex("fin_longitud");
                    int horaInicioIndex = cursor.getColumnIndex("hora_inicio");
                    int horaFinIndex = cursor.getColumnIndex("hora_fin");

                    // Obtener los valores usando los índices correctos
                    String id = cursor.getString(idIndex);
                    String actividad = cursor.getString(actividadIndex);
                    String inicioLat = cursor.getString(inicioLatIndex);
                    String inicioLong = cursor.getString(inicioLongIndex);
                    String finLat = cursor.getString(finLatIndex);
                    String finLong = cursor.getString(finLongIndex);
                    String horaInicio = cursor.getString(horaInicioIndex);
                    String horaFin = cursor.getString(horaFinIndex);

                    // Formatear la entrada para mostrar en la lista
                    String entry = String.format("Actividad: %s\n" +
                                    "Fin:\n" +
                                    "%s, %s\n" +
                                    "Tiempo:\n" +
                                    "%s - %s\n" +
                                    "Inicio:\n" +
                                    "%s, %s",
                            actividad,
                            finLat, finLong,
                            horaInicio, horaFin,
                            inicioLat, inicioLong);

                    // Agregar la entrada al inicio de la lista
                    historyList.add(0, entry);
                }
            }
            cursor.close(); // Cerrar el cursor
        } else {
            Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
        }

        // Crear y establecer el adaptador para la lista
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );
        lvHistory.setAdapter(historyAdapter);
    }
}
