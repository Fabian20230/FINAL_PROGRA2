package gt.edu.umg.final_progra2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import gt.edu.umg.final_progra2.baseDatos.DbDatos;

public class EndActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvLocation;
    private ImageView ivEndPhoto;
    private double endLatitude, endLongitude;
    private String endTime;
    private DbDatos dbDatos;
    private SharedPreferences preferences;
    private Bitmap endPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        tvLocation = findViewById(R.id.tvLocation);
        ivEndPhoto = findViewById(R.id.ivEndPhoto);
        Button btnEnd = findViewById(R.id.btnEnd);

        dbDatos = new DbDatos(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        preferences = getSharedPreferences("ActivityTrackerPrefs", MODE_PRIVATE);

        btnEnd.setOnClickListener(v -> captureEndLocationAndPhoto());
    }

    @SuppressLint("MissingPermission")
    private void captureEndLocationAndPhoto() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                endLatitude = location.getLatitude();
                endLongitude = location.getLongitude();
                endTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                String locationText = "Ubicación de fin: " + endLatitude + ", " + endLongitude;
                tvLocation.setText(locationText);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                endPhoto = (Bitmap) extras.get("data");
                if (endPhoto != null) {
                    ivEndPhoto.setImageBitmap(endPhoto);

                    long lastInsertId = dbDatos.getLastInsertId();
                    if (lastInsertId != -1) {
                        dbDatos.updateEndData(lastInsertId, endLatitude, endLongitude, dbDatos.getBitmapAsByteArray(endPhoto), endTime);
                        Toast.makeText(this, "Actividad finalizada", Toast.LENGTH_SHORT).show();

                        preferences.edit().putBoolean("activityInProgress", false).apply();

                        Intent intent = new Intent(EndActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Error al enlazar el fin con el inicio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No se obtuvo la foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
