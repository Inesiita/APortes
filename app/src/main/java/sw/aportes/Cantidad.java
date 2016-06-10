package sw.aportes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Cantidad extends AppCompatActivity {


    private EditText editText;

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private Button btMedia;
    private Oferta[] datosLista;
    TextView ids;
    TextView Ori;
    TextView Dest;
    TextView Capa;
    TextView Fecha;
    TextView Hora;
    String orig;
    String des;
    int capaz;
    String fec;
    String hor;
    private int codUsu;
    private int codOfer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantidad);

        inicializar();
        getExtras();
        getExtras();



    }

    private void inicializar() {

        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 8);

        editText = (EditText)findViewById(R.id.editText);

        Ori = (TextView) findViewById(R.id.lblOrig);
        Dest = (TextView) findViewById(R.id.lblDest);
        Capa = (TextView) findViewById(R.id.lblCapac);
        Fecha = (TextView) findViewById(R.id.lblFec);
        Hora = (TextView) findViewById(R.id.lblHor);
    }

    public void getExtras() {
        Bundle extras= getIntent().getExtras();

        codUsu = extras.getInt("CodUsu");
        codOfer = extras.getInt("CodOfer");

        orig = extras.getString("Origen");
        Ori.setText(orig);
        des = extras.getString("Destino");
        Dest.setText(des);
        capaz = extras.getInt("Capacidad");
        Capa.setText(capaz+"");
        fec = extras.getString("Fecha");
        Fecha.setText(fec);
        hor = extras.getString("Hora");
        Hora.setText(hor);
    }

    public void onClick(View view) {

        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Onclik", Toast.LENGTH_SHORT);

        toast1.show();

        db = usdbh.getReadableDatabase();
        if (db != null) {
            //String nn = new String(filtrarOferta.getText().toString());
            Cursor c = db.rawQuery("SELECT capacidad FROM Oferta WHERE codigo = " + "'" + codOfer + "'", null);

            int consul = 0;
            int valor = Integer.parseInt(editText.getText().toString());

            if (c.moveToFirst()) {

                datosLista = new Oferta[c.getCount()];
                int i =0;
                do {
                    datosLista[i] = new Oferta();
                   consul = datosLista[i].setCapacidad(c.getInt(0));
                }while (c.moveToNext());

            }
            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            "Consul----->" + consul, Toast.LENGTH_SHORT);

            toast.show();

           // int consul = 1000;
            int resul = consul -valor;

            Toast toast2 =
                    Toast.makeText(getApplicationContext(),
                            "Valor----->" + valor, Toast.LENGTH_SHORT);

            toast2.show();

            Toast toast3 =
                    Toast.makeText(getApplicationContext(),
                            "Resultado----->" + resul, Toast.LENGTH_SHORT);

            toast3.show();

            // int valor = c - editText.getText();
            //Log.i("cccccccccccccccccccccc", c.toString());


            if(true) {
                insertar(resul);
            }


    }

    }

    private void insertar(int resul) {
        db = usdbh.getWritableDatabase();
        if (db != null) {
            //Insertar la cantidad de m3 sustraida de la oferta
            db.execSQL("UPDATE Oferta SET capacidad = '" + resul + "' WHERE codigo= " + "'" + codOfer + "'");
            db.close();
        }        //if not null

        db = usdbh.getWritableDatabase();
        if (db != null) {

            //Agregar el pedido a la tabla de relaciones
            db.execSQL("INSERT INTO Relacion (oferta, usuario) VALUES('"
                    + codOfer + "','"
                    + codUsu + "')");
            db.close();
        }// if not null
        finish();
    }// insertar


}
