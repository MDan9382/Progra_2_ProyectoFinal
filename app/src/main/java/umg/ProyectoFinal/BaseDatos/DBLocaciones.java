package umg.ProyectoFinal.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DBLocaciones extends DBHelper {

    Context context;


    public DBLocaciones(@Nullable Context context) {
        super(context);
    }


    public long insertaLocacion(String Latitud, String Longitud) {
        try {
            if (Latitud.isEmpty() || Longitud.isEmpty()) {
                return -1;
            }
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Latitud", Latitud);
            values.put("Longitud", Longitud);


            long id = db.insert(DBHelper.TABLE_LOCACIONES, null, values);
            return id;
        } catch (Exception e) {
            return -1;
        }


    }

}
