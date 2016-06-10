package sw.aportes;

/**
 * Created by Iness on 14/04/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreaUsu = "CREATE TABLE Usuarios (codigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "email TEXT, " +
            "edad TEXT, " +
            "ciudad TEXT, " +
            "contrasena TEXT," +
            "valoracion INT)";
    String sqlCreaOfer = "CREATE TABLE Oferta (codigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "origen TEXT, " +
            "destino TEXT, " +
            "capacidad INT, " +
            "fecha TEXT, " +
            "hora TEXT)";
    String sqlRelaccion = "CREATE TABLE Relacion (codigo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "oferta INT, " +
            "usuario INT)";

    public UsuariosSQLiteHelper(Context context, String name,
                                CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreaUsu);
        db.execSQL(sqlCreaOfer);
        db.execSQL(sqlRelaccion);

        // Conseguimos la fecha actual
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
        int monthDay = calendarNow.get(Calendar.DAY_OF_MONTH);
        int month = calendarNow.get(Calendar.MONTH);

        // Le sumamos uno al dia
        int dia = monthDay+1;

        //Eliminar oferta si llega a la fecha
      //  db.execSQL("DELETE FROM Oferta IF destino=aaa");
       // db.delete("Oferta", "capacidad=3", null);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Oferta");
        db.execSQL("DROP TABLE IF EXISTS Relacion");

        //Se crea la nueva vrsión de la tabla
        db.execSQL(sqlCreaUsu);
        db.execSQL(sqlCreaOfer);
        db.execSQL(sqlRelaccion);


    }



}