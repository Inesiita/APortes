package sw.aportes;

/**
 * Created by narwhall on 10/05/2016.
 */

public class Usuario
{
    private int codigo;
    private String nombre;
    private String email;
    private String edad;
    private String ciudad;
    private String contrasena;

    public Usuario(){}
    public Usuario(int codigo, String nombre, String email, String edad, String ciudad,  String contrasena){
        setCodigo(codigo);
        setNombre(nombre);
        setEmail(email);
        setEdad(edad);
        setCiudad(ciudad);
        setContrasena(contrasena);
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String eml) {
        this.email = eml;
    }
    public String getEdad() {
        return edad;
    }
    public void setEdad(String eda) {
        this.edad = eda;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String city) {
        this.ciudad = city;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String pass) {
        this.contrasena = pass;
    }
}
