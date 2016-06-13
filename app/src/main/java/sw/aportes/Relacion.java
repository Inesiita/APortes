package sw.aportes;

/**
 * Created by Iness on 12/06/2016.
 */
public class Relacion {

    private int codigo;
    private int usuarioO;
    private int oferta;
    private String email;
    private String nombre;
    private int cantidad;
    private int precio;

    public Relacion(){}
    public Relacion(int codigo, String emaiil, String noombre, int cantidad,  int precio){
        setCodigo(codigo);
        setEmail(emaiil);
        setNombre(noombre);
        //setUsuarioO(usuario);
        //setOferta(oferta);
        setCantidad(cantidad);
        setPrecio(precio);


    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {

        this.codigo = codigo;
    }

/*
    public int getUsuarioO() {
        return usuarioO;
    }
    public void setUsuarioO(int usuarioO) {

        this.usuarioO = usuarioO;
    }

    public int getOferta() {
        return oferta;
    }
    public void setOferta(int oferta) {

        this.oferta= oferta;
    }
*/
    public void setEmail(String ema){
        this.email = ema;
    }
    public String getEmail(){
        return this.email;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nom){
        this.nombre = nom;
    }
    public int getCantidad(){
        return this.cantidad;
    }
    public void setCantidad(int can){
        this.cantidad = can;
    }


    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {

        this.precio = precio;
    }
}
