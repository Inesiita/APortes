package sw.aportes;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private ListView lstLista;
    private Oferta[] datosLista;
    FragmentManager fm;

    int monthDay;
    int month;
    int mes;

    private EditText filtrarOferta;
    //private OnArticuloSelectedListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Referenciamos los Controles
        lstLista = (ListView)findViewById(R.id.LstOferta);
        filtrarOferta = (EditText)findViewById(R.id.filtrarOferta);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 7);

        //Asociamos el menu contextual a la lista
        registerForContextMenu(lstLista);

        // Evento para cuando doy click en algun elemento de la lista ( ListView )
        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // Mensaje Toast del elemento seleccionado.
                //sacar dialog fragment de ayuda
                    //UsuariosFragment dFragment = new UsuariosFragment();
                // Show DialogFragment
                     //dFragment.show(fm, "Ayuda");

                //mCallback.onArticleSelected(datosLista[position].getCodigo());
                //UsuariosFragment fragment = (UsuariosFragment) getFragmentManager().findFragmentById(R.id.frag_UNO);
                //listener.onArticuloSelected(datosLista[position].getCodigo());
                Bundle bun = new Bundle();
                bun.putString("edttext", "From Activity");
// set Fragmentclass Arguments
                //Fragment fragobj = new UsuariosFragment().newInstance(bun);
                //fragobj.setArguments(bun);

                //FragmentTransaction
                //FragmentManager fem = getSupportFragmentManager();
                //fm.beginTransaction().replace(R.id.btMedia, fragobj);

                //transaction.replace((int)id, fragobj);
                //transaction.commit();

                //FragmentManager fun = getFragmentManager();
                //FragmentTransaction fragmentTransaction = fun.beginTransaction();


                //UsuariosFragment fragment = new UsuariosFragment();
                //fragment.setArguments(bun);
                //fragmentTransaction.add(R.id.btMedia, fragment);



            }
        });



        /***
         *
         *
         * fECHA ACTUAL--------------------------------------------------------------------------------------------
         *
         *
         */

        // Conseguimos la fecha actual
        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
        monthDay = calendarNow.get(Calendar.DAY_OF_MONTH);
        month = calendarNow.get(Calendar.MONTH);

        // Le sumamos uno al dia
        mes = month+1;

        String fechaC = monthDay + "/0" + mes + "/2016";

        Log.i("holaaaaaaaaaaaaaaaaaaaa", fechaC);


        cargarLista();
    } // oncreate

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnArticuloSelectedListener) activity;
        } catch (ClassCastException e) {}
    }

*/

    private void cargarLista()
    {
        db = usdbh.getReadableDatabase();
        if (db != null)
        {
            Cursor c = db.rawQuery("SELECT codigo,origen,destino,capacidad,fecha,hora FROM Oferta ORDER BY codigo ASC", null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()){
                //Recorremos el cursor hasta que no haya más registros
                datosLista = new Oferta[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Oferta();
                    datosLista[i].setCodigo(c.getInt(0));
                    datosLista[i].setOrigen(c.getString(1));
                    datosLista[i].setDestino(c.getString(2));
                    datosLista[i].setCapacidad(c.getString(3));
                    datosLista[i].setFecha(c.getString(4));
                    datosLista[i].setHora(c.getString(5));
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
                    datosLista[i].setCodigo(c.getInt(0));
                    datosLista[i].setOrigen(c.getString(1));
                    datosLista[i].setDestino(c.getString(2));
                    datosLista[i].setCapacidad(c.getString(3));
                    datosLista[i].setFecha(c.getString(4));
                    datosLista[i].setHora(c.getString(5));
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
            //sacar dialog fragment de ayuda
                //ayudaFragment dFragment = new ayudaFragment();
                // Show DialogFragment
                //dFragment.show(fm, "Ayuda");
                break;
            case R.id.salir:
               finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Publicar) {
           startActivity(new Intent(MainActivity.this,  Publicar.class));

        } else if (id == R.id.Login) {
            startActivity(new Intent(MainActivity.this,  LoginActivity.class));

        } else if (id == R.id.Registro) {
           startActivity(new Intent(MainActivity.this,  Registro.class));

        } else if (id == R.id.Usuarios) {
            startActivity(new Intent(MainActivity.this,  UsuarioS.class));


        } else if (id == R.id.Perfil) {
        //    startActivity(new Intent(MainActivity.this,  Perfil.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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


                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }
            holder.codigo.setText(datosLista[position].getCodigo() + "");
            holder.origen.setText(datosLista[position].getOrigen());
            holder.destino.setText(datosLista[position].getDestino());
            holder.capacidad.setText(datosLista[position].getCapacidad());
            holder.fecha.setText(datosLista[position].getFecha());
            holder.hora.setText(datosLista[position].getHora());


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
    }

}
