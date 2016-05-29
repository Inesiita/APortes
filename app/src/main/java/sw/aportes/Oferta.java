package sw.aportes;

/**
 * Created by narwhall on 12/05/2016.
 */

public class Oferta
{
    private int codigo;
    private String origen;
    private String destino;
    private String capacidad;
    private String fecha;
    private String hora;

    public Oferta(){}
    public Oferta(int codigo, String origen, String destino,String capacidad, String fecha,String hora){
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
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }


    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
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

