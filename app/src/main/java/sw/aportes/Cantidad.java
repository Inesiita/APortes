package sw.aportes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    // campos a rellenar
    TextView Origen;
    TextView Destino;
    TextView Capa;
    TextView Fecha;
    TextView Hora;
    TextView Precio;
    // from intent
    String orig;
    String des;
    int capaz;
    String fec;
    String hor;
    int pre;
    private int codUsu;
    private int codOfer;
    private int capaPedida;
    private int capaFinal;
    private int precioFinal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantidad);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        inicializar();
        getExtras();
    }

    private void inicializar() {
        // Conectamos la bd
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        //creamos los objetos del layout que luego utilizaremos
        editText = (EditText)findViewById(R.id.editText);
        Origen = (TextView) findViewById(R.id.ClblOrig);
        Destino = (TextView) findViewById(R.id.ClblDest);
        Capa = (TextView) findViewById(R.id.ClblCapac);
        Fecha = (TextView) findViewById(R.id.ClblFec);
        Hora = (TextView) findViewById(R.id.ClblHor);
        Precio = (TextView) findViewById(R.id.ClblPre);
    }

    public void getExtras() {

        // obtenemos todos los datos pasados por parámetro desde la clase ofertas
        Bundle extras= getIntent().getExtras();

        codUsu = extras.getInt("CodUsu");
        codOfer = extras.getInt("CodOfer");
        orig = extras.getString("Origen");
        Origen.setText(orig);
        des = extras.getString("Destino");
        Destino.setText(des);
        capaz = extras.getInt("Capacidad");
        Capa.setText(capaz + "");
        fec = extras.getString("Fecha");
        Fecha.setText(fec);
        hor = extras.getString("Hora");
        Hora.setText(hor);
        pre = extras.getInt("Precio");
        Precio.setText(pre + "");
    }

    // accion cuando presionamos el botón
    public void onClick(View view) {

        // conectamos con la base de datos para obtener la capacidad del pedido, y su precio
        db = usdbh.getReadableDatabase();
        if (db != null) {
            //String nn = new String(filtrarOferta.getText().toString());
              Cursor c = db.rawQuery("SELECT capacidad,precio FROM Oferta WHERE codigo = " + "'" + codOfer + "'", null);

            // capacidad que tiene el pedido en el momento de la consulta
            int capaInicial = 0;

            // cantidad (m3) que reserva el usuario
            capaPedida = Integer.parseInt(editText.getText().toString());

            // recogemos los datos
            if (c.moveToFirst()) {

                datosLista = new Oferta[c.getCount()];
                int i =0;
                do {
                    datosLista[i] = new Oferta();
                    capaInicial = datosLista[i].setCapacidad(c.getInt(0));

                    i++;
                }while (c.moveToNext());

            } else {
                Log.i("errror capacidad", "no se ha podido acceder a la capacidad");
            }

        // calculamos la capacidad restando la reservada por el usuario
        capaFinal = capaInicial - capaPedida;
        precioFinal = capaPedida * pre;
        Toast.makeText(getApplicationContext(), "precio a pagar: "+precioFinal, Toast.LENGTH_SHORT).show();
            Log.i("coste -->", "coste calculado: "+ precioFinal);

            // int valor = c - editText.getText();
            //Log.i("cccccccccccccccccccccc", c.toString());

            // insertamos los valores calculados en la base de datos
            if(true) {
                insertarCapa();
                insertarRel();
            }
        } // database
    } // onclick

    // insertamos la capacidad
    private void insertarCapa() {
        db = usdbh.getWritableDatabase();
        if (db != null) {
            //Insertar la cantidad de m3 sustraida de la oferta
            db.execSQL("UPDATE Oferta SET capacidad = '" + capaFinal + "' WHERE codigo= " + "'" + codOfer + "'");
            db.close();
            Log.i("insertar -->", "capacidad guardada");
        }        //if not null
    }
    // insertamos la relacion oferta - usuario con su precio
    private void insertarRel(){

        db = usdbh.getWritableDatabase();
        if (db != null) {

            //Agregar el pedido a la tabla de relaciones
            db.execSQL("INSERT INTO Relacion (oferta, usuario, cantidad, precio) VALUES('"
                    + codOfer + "','"
                    + codUsu + "','"
                    + capaPedida+ "','"
                    //+ codUsu + "',");
                    + precioFinal + "')");
            db.close();
        }// if not null
        Log.i("insertar -->", "pedido guardado");
        finish();
    }// insertar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.ayuda:
                startActivity(new Intent(Cantidad.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(Cantidad.this, LoginActivity.class));
                finish(); finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}