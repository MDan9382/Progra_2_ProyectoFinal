package umg.ProyectoFinal;

import static umg.ProyectoFinal.R.id.btnAbrirActCamara;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //ActivityMainBinding mainBinding;
//    Button btnTomarFoto;
//    ImageView ivPrueba;
//    private static final int PermisoCamara = 1;
    //ActivityResultLauncher<Uri> tomarFotoLauncher;
    //Uri imagenUri;
    //private Camera nuevacamara;

    Button btnAbrirActCamara, btnAbrirActLocacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        btnTomarFoto = findViewById(R.id.btnAbrirActCamara);
//        ivPrueba = findViewById(R.id.ivPrueba);
        //imagenUri = crearUri();
        //regstrarFotoLauncher();

        btnAbrirActCamara = findViewById(R.id.btnAbrirActCamara);
        btnAbrirActLocacion = findViewById(R.id.btnAbrirActLocacion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAbrirActCamara.setOnClickListener(view -> {

            Toast.makeText(this, "Abriendo Activity de Camara", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Activity_Camara.class);
            startActivity(intent);

        });

        btnAbrirActLocacion.setOnClickListener(view ->  {

            Intent inten = new Intent(this, ActivityLocacion.class);
            startActivity(inten);
            Toast.makeText(this, "Abriendo Activity de Locacion",Toast.LENGTH_SHORT).show();
        });

    }

}