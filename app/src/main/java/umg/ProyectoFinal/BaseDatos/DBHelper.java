package umg.ProyectoFinal.BaseDatos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NOMBRRE = "db_examenProgra2";
    public static final String TABLE_LOCACIONES = "t_locaciones";

    //constructor
    public  DBHelper(@Nullable Context context) {
        super(context,DB_NOMBRRE,null, DB_VERSION);
    }

    @Override
    //se crea la tabla la primera vez que se ejecuta la aplicacion
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LOCACIONES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, Latitud TEXT NOT NULL, Longitud TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCACIONES);
        onCreate(db);
    }


}

