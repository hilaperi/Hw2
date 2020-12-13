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
import hila.peri.hw2.utils.MyScreenUtils;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.R;
import hila.peri.hw2.objects.Card;
import hila.peri.hw2.objects.Deck;
import hila.peri.hw2.objects.Player;
import hila.peri.hw2.services.MainViewController;
import hila.peri.hw2.fragments.FragmentList;
import hila.peri.hw2.fragments.FragmentMap;
import hila.peri.hw2.interfaces.RecordCallBack;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.objects.TopTenRecords;
import hila.peri.hw2.services.RecordItemAdapter;
import hila.peri.hw2.utils.Constants;
import hila.peri.hw2.utils.MyScreenUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class WelcomePage extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private Location currentLocation;
    private EditText welcome_INPT_name;
    private ImageButton welcome_BTN_boy, welcome_BTN_girl;
    private Button welcome_BTN_records;
    private ImageView welcome_IMG_background;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViews();
        initViews();
    }

    private void findViews() {
        welcome_INPT_name = findViewById(R.id.welcome_INPT_name);
        welcome_BTN_boy = findViewById(R.id.welcome_BTN_boy);
        welcome_BTN_girl = findViewById(R.id.welcome_BTN_girl);
        welcome_BTN_records = findViewById(R.id.welcome_BTN_records);
        welcome_IMG_background = findViewById(R.id.welcome_IMG_background);

        MyScreenUtils.updateBackground(Constants.BACKGROUND_NAME, this, welcome_IMG_background);
    }

    private void initViews() {
        welcome_BTN_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(Constants.BOY_CARD);
            }
        });

        welcome_BTN_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(Constants.GIRL_CARD);
            }
        });

        welcome_BTN_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecords();
            }
        });
    }

    private void showRecords() {
        Intent intent = new Intent(this, RecordsPage.class);
        startActivity(intent);
    }

    private void startGame(String playerGander) {
        String name = welcome_INPT_name.getText().toString();
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
                                ActivityCompat.requestPermissions(WelcomePage.this,
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