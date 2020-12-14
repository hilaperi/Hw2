package hila.peri.hw2.activities;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import hila.peri.hw2.views.MyScreenUtils;
import hila.peri.hw2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomePage extends AppCompatActivity {
    private final int PERMISSIONS_ACCESS_LOCATION = 1;
    private Location currentLocation;
    private ImageButton HomePage_IMG_Left, HomePage_IMG_Right;
    private Button HomePage_BTN_TopTen;
    private ImageView HomePage_IMG_background;
    private EditText HomePage_FLD_name;
    private FusedLocationProviderClient mfusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.mfusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViews();
        initViews();
    }

    private void findViews() {
        HomePage_FLD_name = findViewById(R.id.HomePage_FLD_name);
        HomePage_IMG_Left = findViewById(R.id.HomePage_IMG_Left);
        HomePage_IMG_Right = findViewById(R.id.HomePage_IMG_Right);
        HomePage_BTN_TopTen = findViewById(R.id.HomePage_BTN_TopTen);
        HomePage_IMG_background = findViewById(R.id.HomePage_IMG_background);

        MyScreenUtils.updateBackground(MyScreenUtils.Const.BACKGROUND_NAME, this, HomePage_IMG_background);
    }

    private void initViews() {
        HomePage_IMG_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(MyScreenUtils.Const.BOY_CARD);
            }
        });

        HomePage_IMG_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(MyScreenUtils.Const.GIRL_CARD);
            }
        });

        HomePage_BTN_TopTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecords();
            }
        });
    }

    private void showRecords() {
        Intent intent = new Intent(this, TopTenPage.class);
        startActivity(intent);
    }

    private void startGame(String playerGander) {
        String name = HomePage_FLD_name.getText().toString();
        if (name.matches("")) {
            Toast.makeText(this, "Enter you name please", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PLAYER_GENDER, playerGander);
        intent.putExtra(MainActivity.PLAYER_NAME, name);
        if (currentLocation != null) {
            intent.putExtra(MainActivity.LATITUDE, currentLocation.getLatitude());
            intent.putExtra(MainActivity.LONGITUDE, currentLocation.getLongitude());
        }
        startActivity(intent);
        finish();
    }

    private void mapFunc() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Request Location permission")
                        .setMessage("If you want to access feature please give this permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(HomePage.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        1);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_ACCESS_LOCATION);
            }
        } else {
            mfusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
                            }
                        }
                    });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFunc();
    }
}