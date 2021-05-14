package com.example.aula9app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private TextView latitude, longitude, provedor;
    private WebView mapa;
    private String urlBase = "http://maps.googleapis.com/maps/api" + "/staticmap?size=400x400&sensor=true" + "&markers=color:red|%s,%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        provedor = findViewById(R.id.provedor);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapa = findViewById(R.id.mapa);

        long tempoAtualizacao = 0;
        float distancia = 0;

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String latitudeStr = String.valueOf(location.getLatitude());
                String longitudeStr = String.valueOf(location.getLongitude());

                provedor.setText(location.getProvider());
                latitude.setText(latitudeStr);
                longitude.setText(longitudeStr);

                String url = String.format(urlBase, latitudeStr, longitudeStr);
                mapa.loadUrl(url);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, tempoAtualizacao, distancia, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tempoAtualizacao, distancia, listener);
    }
}