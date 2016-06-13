package sw.aportes;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Publicar extends Activity {

    // creación de los objetos de la clase
    private EditText txtOrigen;
    private EditText txtDestino;
    private EditText txtCapacidad;
    private EditText txtFecha;
    private EditText txtHora;
    private EditText txtPrecio;


    private TextView lblMensaje;
    private Button btnAceptar;

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private boolean editando;
    private int usuarioSeleccionado;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        inicializar();

        // obtener el id del usuario pasado por parámetro
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("codigo")) {
            editando = true;
            cargarDatos(bundle.getInt("codigo"));
        }
    }

    // establecer los valores iniciales para todos los objetos
    private void inicializar(){
        //Obtenemos las referencias de los controles
        txtOrigen = (EditText)findViewById(R.id.PlblOrigen);
        txtDestino = (EditText)findViewById(R.id.PlblDestino);
        txtCapacidad = (EditText)findViewById(R.id.PlblCapacidad);
        txtFecha = (EditText)findViewById(R.id.PlblFecha);
        txtHora = (EditText)findViewById(R.id.PlblHora);
        txtPrecio = (EditText)findViewById(R.id.PlblPrecio);
        lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        btnAceptar = (Button)findViewById(R.id.BtnAceptar);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        //Asignar los eventos necesarios
        asignarEventos();
    }

    // establecemos los mensajes de error cuando los campos están vacíos
    private void asignarEventos(){
        btnAceptar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lblMensaje.setText("");
                if (txtOrigen.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El origen no puede estar vacio");
                } else if (txtDestino.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El destino no puede estar vacio");
                } else if (txtCapacidad.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La capacidad no puede estar vacia");
                } else if (txtFecha.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La capacidad no puede estar vacia");
                } else if (txtHora.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La hora no puede estar vacia");
                } else if (txtHora.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La hora no puede estar vacia");
                } else if (txtPrecio.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El precio no puede estar vacio");
                } else {
                    lblMensaje.setTextColor(Color.GREEN);
                    lblMensaje.setText("Guardando...");

                    if (editando)
                        actualizarRegistro();
                    else
                        guardarRegistro();
                }
            }
        });
    }

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
                startActivity(new Intent(Publicar.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(Publicar.this, LoginActivity.class));
                finish(); finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarRegistro(){
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null){
            db.execSQL("INSERT INTO Oferta (codigoUs, origen, destino, capacidad, fecha, hora, precio) VALUES ('"
                    + usuarioSeleccionado +"','"
                    + txtOrigen.getText().toString() + "','"
                    + txtDestino.getText().toString() + "','"
                    + txtCapacidad.getText().toString() +"','"
                    + txtFecha.getText().toString() + "','"
                    + txtHora.getText().toString() + "','"
                    + txtPrecio.getText().toString() + "')");
            Log.i("insertado la oferta", usuarioSeleccionado+" ??");

            //Cerramos la base de datos
            db.close();
            lblMensaje.setTextColor(Color.GREEN);
            lblMensaje.setText("oferta Guardada !");
            finish();
            //startActivity(new Intent(Publicar.this, MainActivity.class).putExtra("codigoU", usuarioSeleccionado));
        }
    }

    private void actualizarRegistro(){
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null){
            db.execSQL("UPDATE Oferta SET "
                    + "codigoUs = '" + usuarioSeleccionado + "', "
                    + "origen = '" + txtOrigen.getText().toString() + "', "
                    + "destino = '" + txtDestino.getText().toString() +"', "
                    + "capacidad = '" + txtCapacidad.getText().toString() +"', "
                    + "fecha = '" + txtFecha.getText().toString() +"', "
                    + "hora = '" + txtHora.getText().toString() +"', "
                    + "precio = '" + txtPrecio.getText().toString() + "' WHERE codigo = "+ usuarioSeleccionado);



            //Cerramos la base de datos
            db.close();
            lblMensaje.setTextColor(Color.GREEN);
            lblMensaje.setText("Oferta Actualizada !");

            finish();
        }
    }

    private void cargarDatos(int codigo){
        db = usdbh.getWritableDatabase();
        if (db != null){
            String[] args = new String[] {codigo+""};
            Cursor cursor = db.rawQuery("SELECT origen, destino, capacidad, fecha, hora, precio FROM Oferta WHERE codigo = ?",args);

            if (cursor.moveToFirst()){
                txtOrigen.setText(cursor.getString(0));
                txtDestino.setText(cursor.getString(1));
                txtCapacidad.setText(cursor.getString(2));
                txtFecha.setText(cursor.getString(3));
                txtHora.setText(cursor.getString(4));
                txtPrecio.setText(cursor.getString(5));
                usuarioSeleccionado = codigo;
            }else{
                lblMensaje.setTextColor(Color.RED);
                lblMensaje.setText("Error: No se encuentra el registro " + codigo);
            }

            db.close();
        }
    }
}