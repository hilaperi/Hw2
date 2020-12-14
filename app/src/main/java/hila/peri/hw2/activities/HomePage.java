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
import hila.peri.hw2.services.MyScreenUtils;
import hila.peri.hw2.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomePage extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private Location currentLocation;
    private EditText HomePage_FLD_name;
    private ImageButton HomePage_IMG_Left, HomePage_IMG_Right;
    private Button HomePage_BTN_TopTen;
    private ImageView HomePage_IMG_background;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
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
        //thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Request Location permission")
                        .setMessage("You have to give this permission to access this feature ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(HomePage.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
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
                // request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
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