package sw.aportes;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by narwhall on 27/05/2016.
 */
public class ValorUsu extends Activity implements RatingBar.OnRatingBarChangeListener {

    RatingBar ratingBar;
    TextView ratingText;
    private int rating;
    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;
    private Usuario[] datosLista;

    TextView ids;
    TextView Nom;
    TextView Email;
    TextView Ciudad;
    TextView Edad;
    TextView Valorate;
    String id, nm, City,Ed, ema,valor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vlor);
        //ids = (TextView) findViewById(R.id.txtId);

        recogerExtras();
        inicializar();
    }

    private void inicializar() {
        ratingText = (TextView) findViewById(R.id.rating);
        ((RatingBar) findViewById(R.id.ratingBar))
                .setOnRatingBarChangeListener(this);
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 8);

        Nom = (TextView) findViewById(R.id.lblNome);
        Email = (TextView) findViewById(R.id.lblEmail);
        Ciudad = (TextView) findViewById(R.id.lblCiudad);
        Edad = (TextView) findViewById(R.id.lblEdad);
        Valorate = (TextView) findViewById(R.id.rating);

    }
    //String id, nm, City,Ed, ema,valor;

    public void recogerExtras() {
//Aquí recogemos y tratamos los parámetros
        Bundle extras= getIntent().getExtras();
        //String s= extras.getString("texto");
         id = extras.getString("Codigo");
       // ids.setText(id);
         nm = extras.getString("Nombre");
        Nom.setText(nm);
         City = extras.getString("Ciudad");
        Edad.setText(City);
         Ed = extras.getString("Edad");
        Ciudad.setText(Ed);
         ema = extras.getString("Email");
        Email.setText(ema);
         valor = extras.getString("Valoracion");
        Valorate.setText(valor);


    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating,
                                boolean fromTouch) {
        final int numStars = ratingBar.getNumStars();
        ratingText.setText(rating + "/" + numStars);

        //Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();

        db = usdbh.getReadableDatabase();
        if (db != null) {
            //String nn = new String(filtrarOferta.getText().toString());
            Cursor c = db.rawQuery("SELECT valoracion FROM Usuarios WHERE codigo = " + "'" + id + "'", null);

            // rating = ratingbar1.getRating();
            int consul = 0;
            float valor = rating;

            if (c.moveToFirst()) {

                datosLista = new Usuario[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Usuario();
                    int  capa = c.getInt(0);
                    consul = datosLista[i].setValoracion(capa);
                } while (c.moveToNext());

            }

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Onclik" + consul, Toast.LENGTH_SHORT);

            toast1.show();

            Toast toast2 =
                    Toast.makeText(getApplicationContext(),
                            "Onclik---->" + valor, Toast.LENGTH_LONG);

            toast2.show();

            // int consul = 1000;
            int resul = (int) (consul + valor);

            Toast toast3 =
                    Toast.makeText(getApplicationContext(),
                            "Onclik" + resul, Toast.LENGTH_LONG);

            toast3.show();

            // int valor = c - editText.getText();
            //Log.i("cccccccccccccccccccccc", c.toString());


            if (true) {
                insertar(resul);
            }
            showDialog();
        }
    }


    private void insertar(float resul) {
        db = usdbh.getWritableDatabase();
        if (db != null) {
            db.execSQL("UPDATE Usuarios SET valoracion = '" + resul + "' WHERE codigo= " + "'" + id + "'");
            //Cursor c = db.rawQuery("SELECT AVG(media) AS media FROM Usuarios", null);
            db.close();

            //finish();

        }

    }

    private void showDialog() {
        final AlertDialog.Builder popdialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);
        //popdialog.setIcon(R.drawable.ic_launcher);
        popdialog.setTitle("Give us Rattings");
        popdialog.setView(rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
                // ratevale=arg1;
                //float rating;
                //Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            }
        });
    }

}
