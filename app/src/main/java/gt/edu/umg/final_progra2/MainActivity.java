// MainActivity.java
package gt.edu.umg.final_progra2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartActivity = findViewById(R.id.btnStartActivity);
        Button btnEndActivity = findViewById(R.id.btnEndActivity);
        Button btnViewHistory = findViewById(R.id.btnViewHistory);

        btnStartActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
        });

        btnEndActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EndActivity.class);
            startActivity(intent);
        });

        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }
}
