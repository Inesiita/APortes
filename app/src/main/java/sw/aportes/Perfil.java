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
import android.widget.ListView;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    private UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    private ListView lstLista;
    private Oferta[] datosLista;

    private int codUsuPubli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Referenciamos los Controles
        lstLista = (ListView)findViewById(R.id.listView);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 9);

        registerForContextMenu(lstLista);

        inicializar();
        obtenerofertas(); // usuarios que han aceptado cada oferta

        //obtenerDatos();
        // Evento para cuando doy click en algun elemento de la lista ( ListView )
        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {


                Intent i = new Intent(Perfil.this, OfertasAceptadas.class);
                i.putExtra("codigoU",codUsuPubli);

                startActivity(i);
            }
        });

    }



    private void obtenerofertas() {
        //select pedido con sus clientes

        db = usdbh.getReadableDatabase();

        if (db != null ){

            Cursor c = db.rawQuery("SELECT origen,destino FROM Oferta WHERE codigoUs = '" + codUsuPubli +"'",null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()){
                //Recorremos el cursor hasta que no haya más registros
                datosLista = new Oferta[c.getCount()];
                int i = 0;
                do {
                    datosLista[i] = new Oferta();
                    datosLista[i].setOrigen(c.getString(0));
                    datosLista[i].setDestino(c.getString(1));
                    i++;
                } while (c.moveToNext());
                AdaptadorUsuarios adaptador = new AdaptadorUsuarios(this);
                lstLista.setAdapter(adaptador);
            }//if

        }//if



    }

    private void inicializar() {
            //Recogemos el id del usuario pasado desde el Main
            Bundle extras = getIntent().getExtras();
            codUsuPubli = extras.getInt("codigoU");


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
                startActivity(new Intent(Perfil.this, Ayuda.class));
                break;
            case R.id.salir:
                startActivity(new Intent(Perfil.this, LoginActivity.class));
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
            super(context, R.layout.listitem_perfil, datosLista);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder holder;
            if (item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.listitem_perfil, null);

                holder = new ViewHolder();
                holder.origen = (TextView)item.findViewById(R.id.LblOrigen);
                holder.destino = (TextView)item.findViewById(R.id.LblDestino);


                item.setTag(holder);
            }else{
                holder = (ViewHolder)item.getTag();
            }
            holder.origen.setText(datosLista[position].getOrigen());
            holder.destino.setText(datosLista[position].getDestino());


            return(item);
        }
    }


    static class ViewHolder {
        TextView origen;
        TextView destino;
        TextView usuario;



    }


}
