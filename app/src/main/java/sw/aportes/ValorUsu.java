package sw.aportes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by narwhall on 27/05/2016.
 */
public class ValorUsu extends Activity{

    TextView ids;
    TextView Nom;
    TextView Email;
    TextView Ciudad;
    TextView Edad;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valorusu);
        ids = (TextView) findViewById(R.id.txtId);
        Nom = (TextView) findViewById(R.id.txtNome);
        Email = (TextView) findViewById(R.id.txtEmail);
        Ciudad = (TextView) findViewById(R.id.txtCiudad);
        Edad = (TextView) findViewById(R.id.txtEdad);
        recogerExtras();
    }

    public void recogerExtras() {
//Aquí recogemos y tratamos los parámetros
        Bundle extras= getIntent().getExtras();
        String s= extras.getString("texto");
        String id = extras.getString("id");
        ids.setText(id);
        String nm = extras.getString("Nombre");
        Nom.setText(nm);
        String City = extras.getString("Ciudad");
        Edad.setText(City);
        String Ed = extras.getString("Edad");
        Ciudad.setText(Ed);
        String ema = extras.getString("Email");
        Email.setText(ema);
    }


}
