package sw.aportes;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Publicar extends Activity {

    private EditText txtOrigen;
    private EditText txtDestino;
    private EditText txtCapacidad;
    private EditText txtFecha;
    private EditText txtHora;

    ;
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("codigo")) {
            editando = true;
            cargarDatos(bundle.getInt("codigo"));
        }
    }

    private void inicializar(){
        //Obtenemos las referencias de los controles
        txtOrigen = (EditText)findViewById(R.id.TxtOrigen);
        txtDestino = (EditText)findViewById(R.id.TxtDestino);
        txtCapacidad = (EditText)findViewById(R.id.TxtCapacidad);
        txtFecha = (EditText)findViewById(R.id.TxtFecha);
        txtHora = (EditText)findViewById(R.id.TxtHora);
        lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        btnAceptar = (Button)findViewById(R.id.BtnAceptar);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 8);

        //Asignar los eventos necesarios
        asignarEventos();
    }

    private void asignarEventos(){
        btnAceptar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lblMensaje.setText("");
                if (txtOrigen.getText().toString().equals("")){
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El origen no puede estar vacio");
                }else if (txtDestino.getText().toString().equals("")){
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El destino no puede estar vacio");
                }else if (txtCapacidad.getText().toString().equals("")){
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La capacidad no puede estar vacia");
                }else if (txtFecha.getText().toString().equals("")){
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La capacidad no puede estar vacia");
                }else if (txtHora.getText().toString().equals("")){
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La hora no puede estar vacia");
                }else {
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

    private void guardarRegistro(){
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null){
            db.execSQL("INSERT INTO Oferta (origen, destino, capacidad, fecha, hora) VALUES ('"
                    + txtOrigen.getText().toString() + "','"
                    + txtDestino.getText().toString() + "','"
                    + txtCapacidad.getText().toString() +"','"
                    + txtFecha.getText().toString() + "','"
                    + txtHora.getText().toString() + "')");





            //Cerramos la base de datos
            db.close();
            lblMensaje.setTextColor(Color.GREEN);
            lblMensaje.setText("oferta Guardada !");
            startActivity(new Intent(Publicar.this, MainActivity.class));
        }
    }

    private void actualizarRegistro(){
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null){
            db.execSQL("UPDATE Oferta SET origen = '" + txtOrigen.getText().toString() + "', "
                    + "destino = '" + txtDestino.getText().toString() +"', "
                    + "capacidad = '" + txtCapacidad.getText().toString() +"', "
                    + "fecha = '" + txtFecha.getText().toString() +"', "
                    + "hora = '" + txtHora.getText().toString() + "' WHERE codigo = "+ usuarioSeleccionado);



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
            Cursor cursor = db.rawQuery("SELECT origen, destino, capacidad, fecha, hora FROM Oferta WHERE codigo = ?",args);

            if (cursor.moveToFirst()){
                txtOrigen.setText(cursor.getString(0));
                txtDestino.setText(cursor.getString(1));
                txtCapacidad.setText(cursor.getString(2));
                txtFecha.setText(cursor.getString(3));
                txtHora.setText(cursor.getString(4));
                usuarioSeleccionado = codigo;
            }else{
                lblMensaje.setTextColor(Color.RED);
                lblMensaje.setText("Error: No se encuentra el registro " + codigo);
            }

            db.close();
        }
    }
}