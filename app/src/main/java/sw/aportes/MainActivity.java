package sw.aportes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

//import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private ListView lstLista;
    private Oferta[] datosLista;
    //FragmentManager fm;

    int monthDay;
    int month;
    int mes;
    int codUs;


    private EditText filtrarOferta;

    //private OnArticuloSelectedListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        fecha();
        cargarLista();

        // Evento para cuando doy click en algun elemento de la lista ( ListView )
        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Intent i = new Intent(MainActivity.this, Cantidad.class);
                i.putExtra("CodUsu", codUs);
                i.putExtra("CodOfer", datosLista[position].getCodOferta());
                i.putExtra("Origen", datosLista[position].getOrigen());
                i.putExtra("Destino", datosLista[position].getDestino());
                i.putExtra("Capacidad", datosLista[position].getCapacidad());
                i.putExtra("Fecha", datosLista[position].getFecha());
                i.putExtra("Hora", datosLista[position].getHora());
                i.putExtra("Precio", datosLista[position].getPrecio());

                startActivity(i);
            }
        });

    } // oncreate

    private void inicializar() {

        lstLista = (ListView)findViewById(R.id.LstOferta);
        filtrarOferta = (EditText)findViewById(R.id.filtrarOferta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        //Asociamos el menu contextual a la lista
        registerForContextMenu(lstLista);

        //Recogemos el id del usuario pasado desde el login
        if(getIntent().getExtras().containsKey("codigoU")) {
            Bundle extras = getIntent().getExtras();
            codUs = extras.getInt("codigoU");
        }
    }

    private void fecha() {
        // Conseguimos la fecha actual
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
        monthDay = calendarNow.get(Calendar.DAY_OF_MONTH);
        month = calendarNow.get(Calendar.MONTH);
        // Le sumamos uno al dia
        mes = month+1;

        String fechaC = monthDay + "/0" + mes + "/2016";

        //Log.i("holaaaaaaaaaaaaaaaaaaaa", fechaC);
    }


    private void cargarLista()
    {
        db = usdbh.getReadableDatabase();
        if (db != null)
        {
            Cursor c = db.rawQuery("SELECT codigo,origen,destino,capacidad,fecha,hora,precio FROM Oferta ORDER BY codigo ASC", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()){
                //Recorremos el cursor hasta que no haya más registros
                datosLista = new Oferta[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Oferta();
                    datosLista[i].setCodOferta(c.getInt(0));
                    datosLista[i].setOrigen(c.getString(1));
                    datosLista[i].setDestino(c.getString(2));
                    datosLista[i].setCapacidad(c.getInt(3));
                    datosLista[i].setFecha(c.getString(4));
                    datosLista[i].setHora(c.getString(5));
                    datosLista[i].setPrecio(c.getInt(6));
                    Log.i("codigo of",datosLista[i].getCodOferta()+" ??");
                    i++;
                } while (c.moveToNext());
                AdaptadorOfertas adaptador = new AdaptadorOfertas(this);
                lstLista.setAdapter(adaptador);


            }
        }
        //baja();

    }


        public void buscar (View view) {
        db = usdbh.getReadableDatabase();
        if (db != null) {
            String nn = new String (filtrarOferta.getText().toString());
            Cursor c = db.rawQuery("SELECT * FROM Oferta WHERE origen = " + "'" + nn + "'", null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                datosLista = new Oferta[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Oferta();
                    datosLista[i].setCodOferta(c.getInt(0));
                    datosLista[i].setOrigen(c.getString(1));
                    datosLista[i].setDestino(c.getString(2));
                    datosLista[i].setCapacidad(c.getInt(3));
                    datosLista[i].setFecha(c.getString(4));
                    datosLista[i].setHora(c.getString(5));
                    datosLista[i].setPrecio(c.getInt(6));
                    i++;
                } while (c.moveToNext());
                AdaptadorOfertas adaptador = new AdaptadorOfertas(this);
                lstLista.setAdapter(adaptador);


            }else if (nn.equals("")){
                cargarLista();
            }
        }
       // baja();
    }
    /***
     *
     *
     * PARA BORRAR UN REGISTRO--------------------------------------------------------------------------------------------
     *
     *
     */
    public void baja(View view) {
        db = usdbh.getReadableDatabase();
        String fechaC = monthDay + "/0" + mes + "/2016";
        Log.i("FEchaa", fechaC);
        if(db != null){
            db.execSQL("DELETE FROM Oferta WHERE fecha = " + "'" + fechaC + "'");
            Log.i("FEchaa", fechaC);
            db.close();
        }
        cargarLista();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                startActivity(new Intent(MainActivity.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish(); finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // método heredado para configurar los elementos del menú principal
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Publicar) {
            Intent publicar = new Intent(MainActivity.this,  Publicar.class);
            publicar.putExtra("codigoU", codUs);
            startActivity(publicar);

        } else if (id == R.id.Ofertas) {
            //startActivity(new Intent(MainActivity.this,  LoginActivity.class));
        } else if (id == R.id.Registro) {
           startActivity(new Intent(MainActivity.this,  Registro.class));
            finish();

        } else if (id == R.id.Usuarios) {
            Intent usuarios = new Intent(MainActivity.this,  UsuarioS.class);
            usuarios.putExtra("codigoU", codUs);
            startActivity(usuarios);

        } else if (id == R.id.Perfil) {

            startActivity(new Intent(MainActivity.this,  Perfil.class).putExtra("coodigoU", codUs));

        } else if (id == R.id.Ayuda) {

            startActivity(new Intent(MainActivity.this,  Ayuda.class));

        } else if (id == R.id.Salir) {

            startActivity(new Intent(MainActivity.this,  LoginActivity.class));
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Adaptador para rellenar las ofertas del listview
    @SuppressWarnings("unchecked")
    class AdaptadorOfertas extends ArrayAdapter
    {
        Activity context;
        AdaptadorOfertas(Activity context)
        {
            super(context, R.layout.listitem_oferta, datosLista);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            ViewHolder holder;
            if (item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.listitem_oferta, null);

                holder = new ViewHolder();
                holder.codigo = (TextView)item.findViewById(R.id.LblCodigo);
                holder.origen = (TextView)item.findViewById(R.id.LblOrigen);
                holder.destino = (TextView)item.findViewById(R.id.LblDestino);
                holder.capacidad = (TextView)item.findViewById(R.id.LblCapacidad);
                holder.fecha = (TextView)item.findViewById(R.id.LblFecha);
                holder.hora = (TextView)item.findViewById(R.id.LblHora);
                holder.precio = (TextView)item.findViewById(R.id.LblPrecio);


                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }
            holder.codigo.setText(datosLista[position].getCodOferta()+"");
            holder.origen.setText(datosLista[position].getOrigen());
            holder.destino.setText(datosLista[position].getDestino());
            holder.capacidad.setText(datosLista[position].getCapacidad()+"");
            holder.fecha.setText(datosLista[position].getFecha());
            holder.hora.setText(datosLista[position].getHora());
            holder.precio.setText(datosLista[position].getPrecio()+"");


            return(item);
        }
    }

    static class ViewHolder {
        TextView codigo;
        TextView origen;
        TextView destino;
        TextView capacidad;
        TextView fecha;
        TextView hora;
        TextView precio;
    }

}
