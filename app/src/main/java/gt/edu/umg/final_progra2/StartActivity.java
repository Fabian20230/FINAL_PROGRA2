// StartActivity.java
package gt.edu.umg.final_progra2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import gt.edu.umg.final_progra2.baseDatos.DbDatos;

public class StartActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvLocation;
    private ImageView ivStartPhoto;
    private double startLatitude, startLongitude;
    private String startTime;
    private DbDatos dbDatos;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvLocation = findViewById(R.id.tvLocation);
        ivStartPhoto = findViewById(R.id.ivStartPhoto);
        Button btnStart = findViewById(R.id.btnStart);
        dbDatos = new DbDatos(this);
        preferences = getSharedPreferences("ActivityTrackerPrefs", MODE_PRIVATE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        checkPermissions();

        if (preferences.getBoolean("activityInProgress", false)) {
            Toast.makeText(this, "Debe finalizar la actividad actual antes de iniciar otra.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnStart.setOnClickListener(v -> {
            captureStartLocationAndPhoto();
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @SuppressLint("MissingPermission")
    private void captureStartLocationAndPhoto() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                startLatitude = location.getLatitude();
                startLongitude = location.getLongitude();
                startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                String locationText = "Ubicación de inicio: " + startLatitude + ", " + startLongitude;
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
                Bitmap photo = (Bitmap) extras.get("data");
                if (photo != null) {
                    ivStartPhoto.setImageBitmap(photo);
                    dbDatos.insertaDatos("Actividad", startLatitude, startLongitude, 0, 0, startTime, "");

                    preferences.edit().putBoolean("activityInProgress", true).apply();

                    // Navegar a MainActivity y finalizar StartActivity
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "No se obtuvo la foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
