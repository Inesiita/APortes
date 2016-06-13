package sw.aportes;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    // inicializamos los objetos y variables que utilizaremos
    private EditText mEmail;
    private EditText mPassword;
    private Button mEmailSignInButton;
    private Button mRegistrButton;
    private String login;
    private String paswd;

    private int codigo; //from bd
    private String nombre;  //from bd
    private String lg = "vacio";  //from bd
    private String ps = "vacio";  //from bd

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicio();
    }

    // establecemos los objetos de nuestro layout y los manejadores de los botones
    private void inicio(){
        // Set up the login form.
        mEmail = (EditText) findViewById(R.id.editLogEmail);
        mPassword = (EditText) findViewById(R.id.editLogPass);
        mEmailSignInButton = (Button) findViewById(R.id.btnSingIn);
        mRegistrButton = (Button) findViewById(R.id.btnLogAReg);
        //final DataBaseMAnager db = new DataBaseMAnager(this);
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        OnClickListener reg = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rg = new Intent(getApplication(), Registro.class);
                startActivity(rg);
            }
        };

        OnClickListener sing = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // obrtenemos los datos
               Obtener();
                if(comprobar()){
                    Toast.makeText( getApplicationContext(),  "Login Correcto, "+ nombre, Toast.LENGTH_SHORT).show();
                    Intent lg = new Intent(getApplication(), MainActivity.class);
                    lg.putExtra("codigoU", codigo);
                    startActivity(lg);
                    finish();
                } else {
                    Toast.makeText( getApplicationContext(),  "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mEmailSignInButton.setOnClickListener(sing);
        mRegistrButton.setOnClickListener(reg);

    }

    // funcion que recoge los datos introducidos por el usuario, y los datos de la bd
    private void  Obtener() {

        login = getEmail();
        paswd = getPassword();
        db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT codigo,nombre,email,contrasena FROM Usuarios WHERE email = '" + login.toString() + "'",null);

        //Recorremos el cursor hasta que no haya más registros
        if(c.moveToFirst())  {
            codigo = c.getInt(0);
            nombre = c.getString(1);
            lg = c.getString(2);
            ps = c.getString(3);
        }else {
            Toast.makeText( getApplicationContext(),  " el usuario no existe", Toast.LENGTH_SHORT).show();

        }
    }
    // función que comprueba si usuario y contraseña son iguales
    private boolean comprobar() {
        if (!lg.equals("vacio")) {
            if ((lg.equals(login)) && (ps.equals(paswd))) {
                return true;
            } else {
                Log.i("Login -->","Usuario y contraseña no coinciden");
                return false;
            }
        } else{
            Toast.makeText( getApplicationContext(),  " El campo login está vacio", Toast.LENGTH_SHORT).show();
            Log.i("Login --> "," vacio");
            return false;
        }

    }

    // funcion que obtiene la contraseña introducida
    public String getPassword() {
        return mPassword.getText().toString().trim(); // mEditPassword - EditText
    }
    // función que obtiene el email introducido
    public String getEmail() {
        return mEmail.getText().toString(); // mEditEmail - EditText
    }
}