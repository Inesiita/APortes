package sw.aportes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OfertasAceptadas extends AppCompatActivity {

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private ListView lstLista;
    private Relacion[] datosRelacion;

    private int codUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas_aceptadas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Referenciamos los Controles
        lstLista = (ListView)findViewById(R.id.listView2);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        registerForContextMenu(lstLista);
        //Recogemos el id del usuario pasado desde el Main
        Bundle extras = getIntent().getExtras();
        codUsu = extras.getInt("codigoU");

        obtenerofertas();
    }

    private void obtenerofertas() {
        //select pedido con sus clientes

        db = usdbh.getReadableDatabase();

        if (db != null) {

            //Cursor c = db.rawQuery("SELECT oferta,usuario,precio FROM Relacion WHERE usuario = '" + codUsu + "'", null);
            Cursor c = db.rawQuery("SELECT Usuarios.email, Usuarios.nombre, cantidad, precio FROM Relacion, Usuarios WHERE Relacion.usuario= '"+codUsu+"'",null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                datosRelacion = new Relacion[c.getCount()];
                int i = 0;
                do {

                    datosRelacion[i] = new Relacion();
                    datosRelacion[i].setEmail(c.getString(0));
                    datosRelacion[i].setNombre(c.getString(1));
                    datosRelacion[i].setCantidad(c.getInt(2));
                    datosRelacion[i].setPrecio(c.getInt(3));
                    Log.i("USuariosssss",datosRelacion[i].getNombre()+"");
                    i++;
                } while (c.moveToNext());
                AdaptadorUsuarios adaptador = new AdaptadorUsuarios(this);
                lstLista.setAdapter(adaptador);
            }//if

        }//if
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
                startActivity(new Intent(OfertasAceptadas.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(OfertasAceptadas.this, LoginActivity.class));
                finish(); finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unchecked")
    class AdaptadorUsuarios extends ArrayAdapter
    {
        Activity context;

        AdaptadorUsuarios(Activity context)
        {
            super(context, R.layout.listitem_ofertasaceptadas, datosRelacion);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;
            if (item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.listitem_ofertasaceptadas, null);

                holder = new ViewHolder();
                holder.email = (TextView)item.findViewById(R.id.OLblUsuario);
                holder.nombre = (TextView)item.findViewById(R.id.OLblOferta);
                holder.cantidad = (TextView)item.findViewById(R.id.OLblCantidad);
                holder.coste = (TextView)item.findViewById(R.id.OLblCoste);

                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }
            String datousuario = datosRelacion[position].getEmail();
            String datooferta = datosRelacion[position].getNombre();
            int datocantidad = datosRelacion[position].getCantidad();
            int datoprecio = datosRelacion[position].getPrecio();
            Log.i("Btenido bd --> ", datousuario+" " + datooferta+" " + datoprecio+" "+datocantidad+"");

             holder.email.setText(datooferta+"");
             holder.nombre.setText(datousuario+"");
             holder.cantidad.setText(datocantidad+"");
             holder.coste.setText(datoprecio+"");

            return(item);
        }
    }


        static class ViewHolder {

            //TextView usuarioO;
            //TextView oferta;
            TextView email;
            TextView nombre;
            TextView cantidad;
            TextView coste;



        }


}
