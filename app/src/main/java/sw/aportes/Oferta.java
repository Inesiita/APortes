package sw.aportes;

/**
 * Created by narwhall on 12/05/2016.
 */

public class Oferta
{
    //variables principales
    private int codOferta;
    private int codUsuario;
    private String origen;
    private String destino;
    private int capacidad;
    private String fecha;
    private String hora;
    private int precio;

    // constructores
    public Oferta(){}
    public Oferta(int codOf, int codUs, String origen, String destino,int capacidad, String fecha, String hora, int precio){
        setCodOferta(codOf);
        setCodUsuario(codUs);
        setOrigen(origen);
        setDestino(destino);
        setCapacidad(capacidad);
        setFecha(fecha);
        setHora(hora);
        setPrecio(precio);


    }

    // creamos los manejadores para todas las variables
    public int getCodOferta() { return codOferta ; }
    public void setCodOferta(int codo) { this.codOferta = codo;}

    public int getCodUsuario() {
        return codUsuario ;
    }
    public void setCodUsuario(int codu) {
        this.codUsuario = codu;
    }


    public String getOrigen() {
        return this.origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }


    public String getDestino() {
        return this.destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getCapacidad() {
        return this.capacidad;
    }
    public int setCapacidad(int capa) {
        this.capacidad = capa;
        return capacidad;
    }


    public String getFecha()
    {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora)
    {
        this.hora = hora;
    }

    public int getPrecio() {
        return this.precio;
    }
    public int setPrecio(int pre) {
        this.precio = pre;
        return precio;
    }
}

