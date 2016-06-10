package sw.aportes;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.NoSuchElementException;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;



    // UI references.
    private EditText mEmail;
    private EditText mPassword;
    private Button mEmailSignInButton;
    private  Button mRegistrButton;
    private String login;
    private String lg = "vacio";  //from bd
    private String paswd;
    private String ps;  //from bd

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicio();
    }

    private void inicio(){
        // Set up the login form.
        mEmail = (EditText) findViewById(R.id.editLogEmail);
        mPassword = (EditText) findViewById(R.id.editLogPass);
        mEmailSignInButton = (Button) findViewById(R.id.btnSingIn);
        mRegistrButton = (Button) findViewById(R.id.btnLogAReg);
        //final DataBaseMAnager db = new DataBaseMAnager(this);
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 8);

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
                String name = Obtener();
                if(comprobar()){
                    Toast.makeText( getApplicationContext(),  "Login Correcto, "+ name, Toast.LENGTH_SHORT).show();
                    Intent lg = new Intent(getApplication(), MainActivity.class);
                    startActivity(lg);
                } else {
                    Toast.makeText( getApplicationContext(),  "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mEmailSignInButton.setOnClickListener(sing);
        mRegistrButton.setOnClickListener(reg);

    }

    private String Obtener() {

        login = getEmail();
        paswd = getPassword();
        db = usdbh.getWritableDatabase();


        //String[] args = new String[] {login};
        //String[] campos = new String[] {"codigo", "nombre","email", "contrasena" };
        //Toast.makeText( getApplicationContext(),  args +"\n"+ campos, Toast.LENGTH_SHORT).show();

        //Cursor c = db.query("Usuarios", campos, "email=?", args, null, null, null);
        //Campos .query  -->NombreTabla + campos + Where + Campos + GroupBy + Having + OrderBy
        //Toast.makeText( getApplicationContext(), "--"+ login +"--", Toast.LENGTH_SHORT).show();

        Cursor c = null;
        c = db.rawQuery("SELECT nombre,email,contrasena FROM Usuarios WHERE email = '" + login.toString() + "'",null);

        if(c.moveToFirst())  {
            //Recorremos el cursor hasta que no haya más registros
            // c.moveToFirst();
            //String codigo= c.getString(0);
            String nombre = c.getString(0);
            lg = c.getString(1);
            ps = c.getString(2);
            return nombre;
        }else {
            Toast.makeText( getApplicationContext(),  "no existe", Toast.LENGTH_SHORT).show();

        }

        return null;
    }

    private boolean comprobar() {
        if (!lg.equals("vacio")) {
            if ((lg.equals(login)) && (ps.equals(paswd))) {
                return true;
            } else {
                return false;
            }
        } else return false;

    }

   /* private void onLoginClicked() {
        if(!isDataValid()) {
            Toast.makeText( getApplicationContext(),  "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }*/

    public String getPassword() {
        return mPassword.getText().toString().trim(); // mEditPassword - EditText
    }

    public String getEmail() {
        return mEmail.getText().toString(); // mEditEmail - EditText
    }
}