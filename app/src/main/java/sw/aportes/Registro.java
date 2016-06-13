package sw.aportes;

/**
 * Created by narwhall on 10/05/2016.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Registro extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtEdad;
    private EditText txtTelefono;
    private EditText txtDni;
    private EditText txtCiudad;
    private EditText txtContrasena;
    private EditText txtConfirContrasena;
    private TextView lblMensaje;
    private Button btnAceptar;


    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private boolean editando;
    private int usuarioSeleccionado;
    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String name = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nuevo_registro);

        inicializar();
        // asignarEventos();
/*
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("codigo")) {
            editando = true;
            cargarDatos(bundle.getInt("codigo"));
        }
*/
    }

    private void inicializar() {
        //Obtenemos las referencias de los controles
        txtNombre = (EditText) findViewById(R.id.RLblNombre);
        txtEmail = (EditText) findViewById(R.id.RLblEmail);
        txtEdad = (EditText) findViewById(R.id.RLblEdad);
        txtTelefono = (EditText) findViewById(R.id.RLblTelefono);
        txtDni = (EditText) findViewById(R.id.RLblDni);
        txtCiudad = (EditText) findViewById(R.id.RLblCiudad);
        txtContrasena = (EditText) findViewById(R.id.RLblPass);
        txtConfirContrasena = (EditText) findViewById(R.id.RLblPass2);
        lblMensaje = (TextView) findViewById(R.id.RLblMensaje);
        btnAceptar = (Button) findViewById(R.id.BtnAceptar);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        //Asignar los eventos necesarios
        asignarEventos();
        name = Environment.getExternalStorageDirectory() + "/test.jpg";


        Button btnAction = (Button) findViewById(R.id.btnFoto);
        btnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Obtenemos los botones de imagen completa y de galer’a para revisar su estatus
                 * m‡s adelante
                 */


                /**
                 * Para todos los casos es necesario un intent, si accesamos la c‡mara con la acci—n
                 * ACTION_IMAGE_CAPTURE, si accesamos la galer’a con la acci—n ACTION_PICK.
                 * En el caso de la vista previa (thumbnail) no se necesita m‡s que el intent,
                 * el c—digo e iniciar la actividad. Por eso inicializamos las variables intent y
                 * code con los valores necesarios para el caso del thumbnail, as’ si ese es el
                 * bot—n seleccionado no validamos nada en un if.
                 */
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                int code = TAKE_PICTURE;


                /**
                 * Si la opci—n seleccionada es fotograf’a completa, necesitamos un archivo donde
                 * guardarla
                 */

                /**
                 * Si la opci—n seleccionada es ir a la galer’a, el intent es diferente y el c—digo
                 * tambiŽn, en la consecuencia de que estŽ chequeado el bot—n de la galer’a se hacen
                 * esas asignaciones
                 */
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                code = SELECT_PICTURE;

                /**
                 * Luego, con todo preparado iniciamos la actividad correspondiente.
                 */
                startActivityForResult(intent, code);
            }


        });
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

                return;
            }
        }
    }

    /**
     * Funci—n que se ejecuta cuando concluye el intent en el que se solicita una imagen
     * ya sea de la c‡mara o de la galer’a
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Se revisa si la imagen viene de la c‡mara (TAKE_PICTURE) o de la galer’a (SELECT_PICTURE)
         */
        if (requestCode == TAKE_PICTURE) {
            /**
             * Si se reciben datos en el intent tenemos una vista previa (thumbnail)
             */
            if (data != null) {
                /**
                 * En el caso de una vista previa, obtenemos el extra ÒdataÓ del intent y
                 * lo mostramos en el ImageView
                 */
                if (data.hasExtra("data")) {
                    ImageView iv = (ImageView) findViewById(R.id.imageView);
                    iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                }
                /**
                 * De lo contrario es una imagen completa
                 */
            } else {
                /**
                 * A partir del nombre del archivo ya definido lo buscamos y creamos el bitmap
                 * para el ImageView
                 */
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageBitmap(BitmapFactory.decodeFile(name));
                /**
                 * Para guardar la imagen en la galer’a, utilizamos una conexi—n a un MediaScanner
                 */
                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;

                    {
                        msc = new MediaScannerConnection(getApplicationContext(), this);
                        msc.connect();
                    }

                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
            }
            /**
             * Recibimos el URI de la imagen y construimos un Bitmap a partir de un stream de Bytes
             */
        } else if (requestCode == SELECT_PICTURE) {
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
            }
        }
    }

    private void asignarEventos() {
        btnAceptar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lblMensaje.setText("");

                if (txtNombre.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El Nombre no puede estar vacio");
                } else if (txtEmail.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El Email no puede estar vacio");
                } else if (txtContrasena.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La contraseña no puede estar vacio");
                } else if (txtConfirContrasena.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La contraseña no puede estar vacio");
                } else if (txtEdad.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La Edad no puede estar vacio");
                } else if (txtTelefono.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El Telefono no puede estar vacio");
                } else if (txtDni.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("El Dni no puede estar vacio");
                } else if (txtCiudad.getText().toString().equals("")) {
                    lblMensaje.setTextColor(Color.RED);
                    lblMensaje.setText("La Ciudad no puede estar vacio");
                } else {

                    lblMensaje.setTextColor(Color.GREEN);
                    lblMensaje.setText("Guardando...");
                }
                if (editando) {
                    actualizarRegistro();
                } else {
                    String pass = txtContrasena.getText().toString();
                    String pass2 = txtConfirContrasena.getText().toString();
                    String email = txtEmail.getText().toString();
                    if ((pass.equals(pass2)) && email.contains("@")) {
                        if (email.contains("@")) {
                            if (cargarDatos() != true)
                                guardarRegistro();
                        }
                    } else {
                        lblMensaje.setTextColor(Color.RED);
                        lblMensaje.setText("Las contraseñas son distintas o el correo es falso");
                    }

                }
            }
        });
    }

    private void guardarRegistro() {
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO Usuarios (nombre, email, edad, telefono, dni, ciudad, contrasena) VALUES ('"
                    + txtNombre.getText().toString() + "','"
                    + txtEmail.getText().toString() + "','"
                    + txtEdad.getText().toString() + "','"
                    + txtTelefono.getText().toString() + "','"
                    + txtDni.getText().toString() + "','"
                    + txtCiudad.getText().toString() + "','"
                    + txtContrasena.getText().toString() + "')");
            //Cerramos la base de datos
            db.close();
            lblMensaje.setTextColor(Color.GREEN);
            lblMensaje.setText("Usuario Guardado !");
            startActivity(new Intent(Registro.this, LoginActivity.class));
            finish();

        }
    }

    private void actualizarRegistro() {
        //Conectamos con la base de datos para escribir
        db = usdbh.getWritableDatabase();
        if (db != null) {
            db.execSQL("UPDATE Usuarios SET nombre = '"
                    + txtNombre.getText().toString() + "', "
                    + "email = '" + txtEmail.getText().toString()
                    + "edad = '" + txtEdad.getText().toString()
                    + "telefono = '" + txtTelefono.getText().toString()
                    + "dni = '" + txtDni.getText().toString()
                    + "ciudad = '" + txtCiudad.getText().toString()
                    + "contrasena = '" + txtContrasena.getText().toString()
                    + "' WHERE codigo = " + usuarioSeleccionado);
            //Cerramos la base de datos
            db.close();
            lblMensaje.setTextColor(Color.GREEN);
            lblMensaje.setText("Usuario Actualizado !");

            //finish();
        }
    }

    private boolean cargarDatos() {
        db = usdbh.getWritableDatabase();
        if (db != null) {
            String lg = txtEmail.getText().toString();
            String[] args = new String[]{lg};
            Cursor cursor = db.rawQuery("SELECT nombre, email, edad, telefono, dni, ciudad, contrasena FROM Usuarios WHERE codigo = ?", args);

            if (cursor.moveToFirst()) {
                txtNombre.setText(cursor.getString(0));
                txtEmail.setText(cursor.getString(1));
                txtEdad.setText(cursor.getString(2));
                txtTelefono.setText(cursor.getString(3));
                txtDni.setText(cursor.getString(4));
                txtCiudad.setText(cursor.getString(5));
                txtContrasena.setText(cursor.getString(6));
                //usuarioSeleccionado = codigo;
                Log.i("usuario sel: ", usuarioSeleccionado + "");
                Toast.makeText(getApplicationContext(), "Introduzca otro nombre de USUARIO \n El actual ya existe", Toast.LENGTH_LONG).show();
                db.close();
                return true;
            } else {
                lblMensaje.setTextColor(Color.RED);
                lblMensaje.setText("Correcto: No se han encontrado coincidencias " + lg);
                db.close();
                return false;
            }
        } // if db null


        return false;
    }

}
