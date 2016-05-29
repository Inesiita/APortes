package sw.aportes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by narwhall on 12/05/2016.
 */
public class ayudaFragment  extends DialogFragment {

    private EditText txtMedia;

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;


    Button CrearCuenta;
    Button Login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/*
    public static ayudaFragment newInstance(int title) {
        ayudaFragment frag = new ayudaFragment();
        Bundle args = new Bundle();
        args.putInt("dialog", title);

        frag.setArguments(args);
        return frag;
    }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ayudafragment, container, false);
        getDialog().setTitle("Usuarios");
        Log.i("MEdiaaaaaa", String.valueOf(txtMedia));
        // Do something else
        return rootView;

    }

    public void media() {

        Toast.makeText(getContext(), txtMedia.getText().toString(), Toast.LENGTH_LONG).show();
        txtMedia.getText().toString();
        db = usdbh.getWritableDatabase();
        if (db != null){
            db.execSQL("INSERT INTO Usuarios (media) VALUES ('"
                    + txtMedia.getText().toString() + "')");
            //Cerramos la base de datos
            db.close();

            Log.i("MEdiaaaaaa", String.valueOf(txtMedia));
           // startActivity(new Intent(ayudaFragment.this, MainActivity.class));

            Cursor c =  db.rawQuery("SELECT * FROM Usuarios", null);
            //Cursor d =  db.rawQuery("SELECT AVG(media) AS media FROM Usuarios", null);

            Bundle bundle = new Bundle();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            // intent.putExtra("dato_bundle", bundle);
            intent.putExtra("email", c.toString());


           // Context context = getApplicationContext();
            //Toast toast = Toast.makeText(context, c , Toast.LENGTH_LONG).show();

        }

        //cargarLista();

    }
}
