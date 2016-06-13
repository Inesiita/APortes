package sw.aportes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//import android.app.FragmentManager;

public class UsuarioS extends AppCompatActivity {


    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private ListView lstLista;
    private Usuario[] datosLista;
    Button media;
    //FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_s);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        media = (Button) findViewById(R.id.btMedia);

        //Referenciamos los Controles
        lstLista = (ListView)findViewById(R.id.LstUsuarios);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        //Asociamos el menu contextual a la lista
        registerForContextMenu(lstLista);

        // Evento para cuando doy click en algun elemento de la lista ( ListView )
        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                // creamos el intent y añadimos los extrar para mostrar la información del usuario
                Intent i = new Intent(UsuarioS.this, ValorUsu.class);

                i.putExtra("Codigo", datosLista[position].getCodigo());
                i.putExtra("Nombre",  datosLista[position].getNombre());
                i.putExtra("Ciudad", datosLista[position].getCiudad());
                i.putExtra("Edad", datosLista[position].getEdad());
                i.putExtra("Telefono", datosLista[position].getTelefono());
                i.putExtra("Dni", datosLista[position].getDni());
                i.putExtra("Email", datosLista[position].getEmail());
                i.putExtra("Valoracion", datosLista[position].getValoracion());

                startActivity(i);
                }
                /*
                public void recogerExtras() {
//Aquí recogemos y tratamos los parámetros
                    Bundle extras= getIntent().getExtras();
                    String s= extras.getString("texto");
                    texto.setText(s);
                }

            }*/
        });


        cargarLista();
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
                startActivity(new Intent(UsuarioS.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(UsuarioS.this, LoginActivity.class));
                finish(); finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarLista()
    {
        db = usdbh.getReadableDatabase();
        if (db != null)
        {
            Cursor c = db.rawQuery("SELECT codigo,nombre,email,edad,telefono,ciudad,contrasena,valoracion FROM Usuarios ORDER BY codigo ASC", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()){
                //Recorremos el cursor hasta que no haya más registros
                datosLista = new Usuario[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Usuario();
                    datosLista[i].setCodigo(c.getInt(0));
                    datosLista[i].setNombre(c.getString(1));
                    datosLista[i].setEmail(c.getString(2));
                    datosLista[i].setEdad(c.getString(3));
                    datosLista[i].setTelefono(c.getInt(4));
                    datosLista[i].setCiudad(c.getString(5));
                    datosLista[i].setContrasena(c.getString(6));
                    datosLista[i].setValoracion(c.getInt(7));
                    i++;
                } while (c.moveToNext());
                AdaptadorUsuarios adaptador = new AdaptadorUsuarios(this);
                lstLista.setAdapter(adaptador);
            }
        }
    }

    @SuppressWarnings("unchecked")
    class AdaptadorUsuarios extends ArrayAdapter
    {
        Activity context;
        AdaptadorUsuarios(Activity context)
        {
            super(context, R.layout.listitem_usuario, datosLista);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;
            if (item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.listitem_usuario, null);

                holder = new ViewHolder();
                holder.codigo = (TextView)item.findViewById(R.id.LblCodigo);
                holder.nombre = (TextView)item.findViewById(R.id.LblNombre);
                holder.email = (TextView)item.findViewById(R.id.LblEmail);
                holder.edad = (TextView)item.findViewById(R.id.LblEdad);
                holder.telefono = (TextView)item.findViewById(R.id.LblTelefono);
                holder.ciudad = (TextView)item.findViewById(R.id.LblCiudad);
                //holder.contrasena = (TextView)item.findViewById(R.id.LblContrasena);
                holder.valoracion = (TextView)item.findViewById(R.id.LblValoracion);
                //holder.contraseña = (TextView)item.findViewById(R.id.LblContraseña);

                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }
            holder.codigo.setText(datosLista[position].getCodigo() + "");
            holder.nombre.setText(datosLista[position].getNombre());
            holder.email.setText(datosLista[position].getEmail());
            holder.edad.setText(datosLista[position].getEdad());
            holder.telefono.setText(datosLista[position].getTelefono()+"");
            holder.ciudad.setText(datosLista[position].getCiudad());
            //holder.contrasena.setText(datosLista[position].getContrasena());
//            holder.contraseña.setText(datosLista[position].getContraseña());
            return(item);
        }
    }
    static class ViewHolder {
        TextView codigo;
        TextView nombre;
        TextView email;
        TextView edad;
        TextView telefono;
        TextView ciudad;

        TextView valoracion;

    }

}
