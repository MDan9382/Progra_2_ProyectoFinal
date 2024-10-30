package umg.ProyectoFinal;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.OutputStream;
import java.util.Objects;

public class Activity_Camara extends AppCompatActivity {

    Button btnTomarFoto, btnGuardarFoto, btnCerrarActCamara;
    ImageView ivPrueba;
    private static final int PermisoCamara = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camara);

        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnGuardarFoto = findViewById(R.id.btnGuardarFoto);
        btnCerrarActCamara = findViewById(R.id.btnCerrarActCamara);
        ivPrueba = findViewById(R.id.ivFotoPreview);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnTomarFoto.setOnClickListener(view -> {
            Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camaraIntent,PermisoCamara);

        });

        btnGuardarFoto.setOnClickListener(view -> {
            if(ivPrueba.getDrawable() != null) {

                saveImage();

            }
            else {
                Toast.makeText(Activity_Camara.this,"No hay imagen cargada", Toast.LENGTH_SHORT).show();
            }
        });

        btnCerrarActCamara.setOnClickListener(view -> {
            Toast.makeText(Activity_Camara.this, "Cerrando Activity", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PermisoCamara && resultCode == RESULT_OK){

            Bitmap foto = (Bitmap) data.getExtras().get("data");
            ivPrueba.setImageBitmap(foto);

        }
        else {
            Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void saveImage() {

        //URI = Uniform Resource Identifier
        Uri imagen;

        //el resolver se comunica con el provider para poder acceder datos de forma segura
        ContentResolver contentResolver = getContentResolver();

        //VOLUME_EXTERNAL_PRIMARY = almacenamiento principal del dipositivo
        imagen = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        // es una clase que almacena datos que el resolver puede procesar (parametros para el URI de nuestra imagen)
        ContentValues contentValues = new ContentValues();

        //llenamos los datos del uri
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/*");
        Uri uri = contentResolver.insert(imagen,contentValues);

        try {
            //un bitmap para guardar nuestra foto
            BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPrueba.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            //OutputStream se refiere a un output en bytes
            //conseguimos es stream del uri, usamos not null para asegurarnos que obtengamos una repuesta valida
            OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));

            //lo comprimimos y guardamos
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(Activity_Camara.this,"Imagen guardada", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(Activity_Camara.this,"Imagen no guardada", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}
