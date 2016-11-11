package jp.arc_net.hogeapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private SQLiteDatabase hogeDb;

    private LocationManager locationManager;

    double longitude;
    double latitude;
    double altitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        HogeSqlLiteOpenHelper hogeHelper = new HogeSqlLiteOpenHelper(getApplicationContext());
        this.hogeDb = hogeHelper.getWritableDatabase();

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View x) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String str = editText.getText().toString();
                        intent.putExtra("INPUT_TEXT", str);
                        ContentValues values = new ContentValues();
                        values.put("INPUT_TIME", System.currentTimeMillis());
                        values.put("INPUT_TEXT", str);
                        hogeDb.insert("HOGE_MESSAGE", "NULL", values);
                        startActivity(intent);
                    }
                }
        );

    }

    @Override
    public void onPause() {
        if (this.locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                super.onPause();
                return;
            }
            this.locationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (this.locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                super.onResume();
                return ;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        super.onResume();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.altitude = location.getAltitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
