package sw.aportes;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
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
 * Created by narwhall on 26/05/2016.
 */
//public class UsuariosFragment   extends DialogFragment {
    public class UsuariosFragment extends DialogFragment {

    public static String ARG_POSITION = "";
    private EditText txtMedia;

        private UsuariosSQLiteHelper usdbh;
        private SQLiteDatabase db;
    int pos =0;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        public static UsuariosFragment newInstance(Bundle argumentos) {
            UsuariosFragment frag = new UsuariosFragment();
            Bundle args = new Bundle();
            int title=0;
            args.putInt("dialog", title);
            if (argumentos != null) {
                frag.setArguments(argumentos);
            }
                frag.setArguments(args);
                return frag;
        }

            public UsuariosFragment (){

            }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.ayudafragment, container, false);
            //getDialog().setTitle("Usuarios");
            Log.i("MEdiaaaaaa", String.valueOf(txtMedia));
            Bundle bundle = this.getArguments();
            String myValue = bundle.getString("edttext");
            Log.i("codigo: ",myValue);
            // Do something else
            return rootView;

        }


        public void media() {

            //Toast.makeText(con, txtMedia.getText().toString(), Toast.LENGTH_LONG).show();
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

