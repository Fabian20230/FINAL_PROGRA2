package gt.edu.umg.final_progra2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import gt.edu.umg.final_progra2.R;
import gt.edu.umg.final_progra2.baseDatos.DbDatos;

public class MainActivity extends AppCompatActivity {

    private ImageView imgInicio, imgFin;
    private TextView txtDescripcion;
    private DbDatos dbDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIniciarActividad = findViewById(R.id.btnStartActivity);
        Button btnFinalizarActividad = findViewById(R.id.btnEndActivity);
        Button btnVerHistorial = findViewById(R.id.btnViewHistory);

        imgInicio = findViewById(R.id.imgStart);
        imgFin = findViewById(R.id.imgEnd);
        txtDescripcion = findViewById(R.id.txtDescription);

        dbDatos = new DbDatos(this);

        btnIniciarActividad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
        });

        btnFinalizarActividad.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EndActivity.class);
            startActivity(intent);
        });

        btnVerHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        // Carga las imágenes de inicio y fin de la última actividad
        cargarUltimaActividadImagenes();
    }

    private void cargarUltimaActividadImagenes() {
        long ultimoId = dbDatos.getLastInsertId();
        if (ultimoId != -1) {
            byte[] imagenInicio = dbDatos.getStartImage(ultimoId);
            byte[] imagenFin = dbDatos.getEndImage(ultimoId);
            String descripcion = dbDatos.getActivityDescription(ultimoId);

            if (imagenInicio != null) {
                imgInicio.setImageBitmap(BitmapFactory.decodeByteArray(imagenInicio, 0, imagenInicio.length));
            }
            if (imagenFin != null) {
                imgFin.setImageBitmap(BitmapFactory.decodeByteArray(imagenFin, 0, imagenFin.length));
            }
            txtDescripcion.setText(descripcion);
        }
    }
}