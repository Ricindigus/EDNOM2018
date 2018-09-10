package pe.com.ricindigus.appednom2018.modelo;

public class UsuarioActual {
    private int _id;
    private String clave;


    public UsuarioActual(int _id, String clave) {
        this._id = _id;
        this.clave = clave;
    }

    public UsuarioActual() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
