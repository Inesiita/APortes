package sw.aportes;

/**
 * Created by narwhall on 12/05/2016.
 */

public class Oferta
{
    private int codigo;
    private String origen;
    private String destino;
    private int capacidad;
    private String fecha;
    private String hora;

    public Oferta(){}
    public Oferta(int codigo, String origen, String destino,int capacidad, String fecha,String hora){
        setCodigo(codigo);
        setOrigen(origen);
        setDestino(destino);
        setCapacidad(capacidad);
        setFecha(fecha);
        setHora(hora);


    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

}

