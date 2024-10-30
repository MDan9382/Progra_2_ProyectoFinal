package umg.ProyectoFinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import umg.ProyectoFinal.BaseDatos.DBLocaciones;

public class ActivityLocacion extends AppCompatActivity {

    TextView txtLocacion;
    Button btnLocacion, btnGuardar;
    String latitud, longitud;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        txtLocacion = findViewById(R.id.txtLocacion);
        btnLocacion = findViewById(R.id.btnLocacion);
        btnGuardar = findViewById(R.id.btnGuardar);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        btnLocacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocacion();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void guardarDatos() {
        DBLocaciones dbLocaciones = new DBLocaciones(this);
        long id = dbLocaciones.insertaLocacion(latitud, longitud);
        if (id > 0) {
            txtLocacion.setText("");
            Toast.makeText(this, "Locacion guardada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }
    }


    private void getLocacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        Geocoder gcd = new Geocoder(ActivityLocacion.this, Locale.getDefault());
                        try {
                            List<Address> direcciones = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            latitud = "Latitud: " + direcciones.get(0).getLatitude();
                            longitud = "Longitud: " + direcciones.get(0).getLongitude();
                            ;

                            txtLocacion.setText(latitud + " " + longitud);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } else {
            pedirPermisos();

        }


    }

    private void pedirPermisos() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocacion();
            } else {
                Toast.makeText(this, "Se necesitan Permisos para Continuar", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}










